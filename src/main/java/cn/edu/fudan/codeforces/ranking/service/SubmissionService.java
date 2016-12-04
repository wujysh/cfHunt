package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Submission;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class SubmissionService {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionService.class.getName());
    private static Configuration conf;
    private static String tablenameSubmission = "codeforces:submission";
    private static HTable tableSubmission;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "10.141.208.43,10.141.208.44,10.141.208.45");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.master.port", "60000");
        conf.setLong("hbase.rpc.timeout", Long.parseLong("60000"));
        conf = HBaseConfiguration.create(conf);
    }

    public List<Submission> getSubmissions(String contestIdStr, String rtime) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        tableSubmission = (HTable) conn.getTable(TableName.valueOf(tablenameSubmission));

        int contestId = Integer.valueOf(contestIdStr);
        long time = 9999999998L;

        if (!rtime.isEmpty()) {
            time = Long.valueOf(rtime);
        }

        String startKey = String.format("%06d-0000000000", contestId);
        String stopkey = String.format("%06d-%010d", contestId, time + 1);

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startKey));
        scan.setStopRow(Bytes.toBytes(stopkey));

        ArrayList<Submission> ans = new ArrayList<>();
        ResultScanner scanner = tableSubmission.getScanner(scan);
        for (Result result : scanner) {

            Submission submission = buildSubmission(result);
            ans.add(submission);

        }

        tableSubmission.close();
        conn.close();

        return ans;
    }

    private Submission buildSubmission(Result result) {
        Submission res = new Submission();

        String[] token = Bytes.toString(result.getRow()).split("-");

        res.setContestId(Integer.valueOf(token[0]));
        res.setRelativeTimeSeconds(Integer.valueOf(token[1]));
        res.getProblem().setIndex(token[2]);

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            Integer valueInt = Bytes.readAsInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            if (family.equals("verdict")) {

                res.setVerdict(valueStr);

            } else if (family.equals("info")) {

                if (qualifier.equals("creationTimeSeconds")) {
                    res.setCreationTimeSeconds(valueInt);
                } else if (qualifier.equals("participantType")) {
                    res.setPassedTestCount(valueInt);
                } else if (qualifier.equals("teamId")) {
                    res.getAuthor().setTeamId(valueInt);
                } else if (qualifier.equals("teamName")) {
                    res.getAuthor().setTeamName(valueStr);
                } else if (qualifier.equals("ghost")) {
                    res.getAuthor().setGhost(Boolean.valueOf(valueStr));
                } else if (qualifier.equals("room")) {
                    res.getAuthor().setRoom(valueInt);
                } else if (qualifier.equals("startTimeSeconds")) {
                    res.getAuthor().setStartTimeSeconds(valueInt);
                } else if (qualifier.equals("programmingLanguage")) {
                    res.setProgrammingLanguage(valueStr);
                } else if (qualifier.equals("testset")) {
                    res.setTestset(valueStr);
                } else if (qualifier.equals("passedTestCount")) {
                    res.setPassedTestCount(valueInt);
                } else if (qualifier.equals("timeConsumedMillis")) {
                    res.setTimeConsumedMillis(valueInt);
                } else if (qualifier.equals("memoryConsumedBytes")) {
                    res.setMemoryConsumedBytes(valueInt);
                } else {
                    System.out.println("Bad Cell. Family " + family + " Qualifier " + qualifier + " Value " + valueStr);
                }

            } else {
                System.out.println("Bad Cell. Family " + family + " Qualifier " + qualifier + " Value " + valueStr);
            }

        }

        if (!token[3].equals(res.getAuthor().getTeamName())) {
            String[] tmp = token[3].split("_");
            ArrayList<String> mem = new ArrayList<>();
            for (String ftmp : tmp) {
                mem.add(ftmp);
            }
            res.getAuthor().setMembers(mem);
        }

//        res.setId(Integer.valueOf(token[4]));

        return res;
    }

}
