package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.Submission;
import cn.edu.fudan.codeforces.ranking.util.ByteUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class ProblemService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemService.class.getName());
    private static String tablenameProblem = "codeforces:problem";
    private static HTable tableProblem;

    public List<Problem> listProblems(Integer page, Integer max) throws IOException {
        --page;
        ArrayList<Problem> ans = new ArrayList<>();
        int cnt = 0;

        Connection conn = ConnectionFactory.createConnection(conf);
        tableProblem = (HTable) conn.getTable(TableName.valueOf(tablenameProblem));

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("points"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tags"));
        ResultScanner scanner = tableProblem.getScanner(scan);
        for (Result result : scanner) {
            if (cnt >= page * max && cnt < (page + 1) * max)
                ans.add(buildProblem(result));
            ++cnt;
        }

        tableProblem.close();
        conn.close();

        return ans;
    }

    public List<Problem> getProblemForContest(String contestIdStr) throws IOException {
        ArrayList<Problem> ans = new ArrayList<>();

        Connection conn = ConnectionFactory.createConnection(conf);
        tableProblem = (HTable) conn.getTable(TableName.valueOf(tablenameProblem));

        int contestId = Integer.valueOf(contestIdStr);
        String rowkey = String.format("%06d-", contestId);

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(rowkey));
        scan.setStopRow(Bytes.toBytes(rowkey + "-zz"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        ResultScanner scanner = tableProblem.getScanner(scan);
        for (Result result : scanner) {
            ans.add(buildProblem(result));
        }

        tableProblem.close();
        conn.close();

        return ans;
    }


    public Problem getProblem(String contestIdStr, String problemIdx) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        tableProblem = (HTable) conn.getTable(TableName.valueOf(tablenameProblem));

        int contestId = Integer.valueOf(contestIdStr);

        String rowkey = String.format("%06d-%s", contestId, problemIdx);

        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = tableProblem.get(get);
        Problem ans = buildProblem(result);

        tableProblem.close();
        conn.close();

        return ans;
    }

    private Problem buildProblem(Result result) throws IOException {
        Problem res = new Problem();

        String[] token = Bytes.toString(result.getRow()).split("-");

        res.setContestId(Integer.valueOf(token[0]));
        res.setIndex(token[1]);

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            if (family.equals("info")) {

                if (qualifier.equals("name")) {
                    res.setName(valueStr);
                } else if (qualifier.equals("type")) {
                    res.setType(valueStr);
                } else if (qualifier.equals("points")) {
                    res.setPoints(Bytes.toFloat(cell.getValueArray(), cell.getValueOffset()));
                } else if (qualifier.equals("tags")) {
                    res.setTags(ByteUtil.toStringList(Bytes.copy(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength())));
                }

            }

        }

        return res;
    }

    public double getProblemAvgACTryCnt(String contestIdStr, String problemIdx) throws IOException {
        HashMap<String, Integer> cnt = new HashMap<>();
        HashSet<String> acers = new HashSet<>();

        SubmissionService ss = new SubmissionService();
        List<Submission> submissions = ss.getSubmissions(contestIdStr, "");

        for (Submission sub : submissions) {
            if (sub.getProblem().getIndex().equals(problemIdx)) {

                for (String auth : sub.getAuthor().getMembers()) {
                    if (!acers.contains(auth)) {
                        int val = 0;
                        if (cnt.containsKey(auth)) {
                            val = cnt.get(auth);
                        }
                        ++val;
                        cnt.put(auth, val);
                    }
                }

                if (sub.isVerdictOK()) {
                    for (String auth : sub.getAuthor().getMembers()) {
                        acers.add(auth);
                    }
                }

            }
        }

        long actry = 0;
        long acman = acers.size();
        for (String acer : acers) {
            actry += cnt.get(acer);
        }

        return (double) (actry) / acman;
    }

}
