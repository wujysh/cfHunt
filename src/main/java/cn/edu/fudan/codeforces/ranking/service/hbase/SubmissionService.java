package cn.edu.fudan.codeforces.ranking.service.hbase;

import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.Submission;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class SubmissionService extends BaseHBaseService {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionService.class.getName());

    private static Table tableSubmission;

    static {
        String tablename = "codeforces:submission";
        try {
            tableSubmission = conn.getTable(TableName.valueOf(tablename));
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public List<Submission> listSubmissions(Integer page, Integer max) throws IOException {
        return new ArrayList<>();  // TODO:
    }

    public List<Submission> getSubmissions(String contestIdStr, String rtime) throws IOException {
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

        return ans;
    }

    private Submission buildSubmission(Result result) {
        Submission res = new Submission();

        String[] token = Bytes.toString(result.getRow()).split("-");

        res.setContestId(Integer.valueOf(token[0]));
        res.setRelativeTimeSeconds(Integer.valueOf(token[1]));

        Problem prob = new Problem();
        prob.setIndex(token[2]);
        res.setProblem(prob);

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            Integer valueInt = Bytes.readAsInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            switch (family) {
                case "verdict":
                    res.setVerdict(valueStr);
                    break;
                case "info":
                    switch (qualifier) {
                        case "creationTimeSeconds":
                            res.setCreationTimeSeconds(valueInt);
                            break;
                        case "participantType":
                            res.setPassedTestCount(valueInt);
                            break;
                        case "teamId":
                            res.getAuthor().setTeamId(valueInt);
                            break;
                        case "teamName":
                            res.getAuthor().setTeamName(valueStr);
                            break;
                        case "ghost":
                            res.getAuthor().setGhost(Boolean.valueOf(valueStr));
                            break;
                        case "room":
                            res.getAuthor().setRoom(valueInt);
                            break;
                        case "startTimeSeconds":
                            res.getAuthor().setStartTimeSeconds(valueInt);
                            break;
                        case "programmingLanguage":
                            res.setProgrammingLanguage(valueStr);
                            break;
                        case "testset":
                            res.setTestset(valueStr);
                            break;
                        case "passedTestCount":
                            res.setPassedTestCount(valueInt);
                            break;
                        case "timeConsumedMillis":
                            res.setTimeConsumedMillis(valueInt);
                            break;
                        case "memoryConsumedBytes":
                            res.setMemoryConsumedBytes(valueInt);
                            break;
                        default:
                            logger.error("Bad Cell. Family {} Qualifier {} Value {}", family, qualifier, valueStr);
                            break;
                    }
                    break;
                default:
                    logger.error("Bad Cell. Family {} Qualifier {} Value {}", family, qualifier, valueStr);
                    break;
            }
        }

        if (!token[3].equals(res.getAuthor().getTeamName())) {
            String[] tmp = token[3].split("_");
            ArrayList<String> mem = new ArrayList<>();
            Collections.addAll(mem, tmp);
            res.getAuthor().setMembers(mem);
        }

//        res.setId(Integer.valueOf(token[4]));
        return res;
    }

}
