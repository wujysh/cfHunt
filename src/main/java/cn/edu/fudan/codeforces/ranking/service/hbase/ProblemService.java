package cn.edu.fudan.codeforces.ranking.service.hbase;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.Submission;
import cn.edu.fudan.codeforces.ranking.util.ByteUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProblemService extends BaseHBaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseHBaseService.class.getName());
    private static Table tableProblem;

    static {
        try {
            String tablename = "codeforces:problem";
            tableProblem = conn.getTable(TableName.valueOf(tablename));
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    private final SubmissionService submissionService;

    @Autowired
    public ProblemService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    public List<Problem> listProblems(Integer page, Integer max) throws IOException {
        --page;
        ArrayList<Problem> ans = new ArrayList<>();
        int cnt = 0;

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

        return ans;
    }

    public List<Problem> getProblemForContest(String contestIdStr) throws IOException {
        ArrayList<Problem> ans = new ArrayList<>();

        int contestId = Integer.valueOf(contestIdStr);
        String rowkey = String.format("%06d-", contestId);

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(rowkey));
        scan.setStopRow(Bytes.toBytes(rowkey + "zz"));
        ResultScanner scanner = tableProblem.getScanner(scan);
        for (Result result : scanner) {
            ans.add(buildProblem(result));
        }

        return ans;
    }


    public Problem getProblem(String contestIdStr, String problemIdx) throws IOException {
        int contestId = Integer.valueOf(contestIdStr);

        String rowkey = String.format("%06d-%s", contestId, problemIdx);

        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = tableProblem.get(get);

        return buildProblem(result);
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
                switch (qualifier) {
                    case "name":
                        res.setName(valueStr);
                        break;
                    case "type":
                        res.setType(valueStr);
                        break;
                    case "points":
                        res.setPoints(Bytes.toFloat(cell.getValueArray(), cell.getValueOffset()));
                        break;
                    case "tags":
                        res.setTags(ByteUtil.toStringList(Bytes.copy(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength())));
                        break;
                }
            }
        }
        return res;
    }

    public List<Double> getProblemAvgACTryCnt(Contest contest, List<Problem> problems) throws IOException {
        ArrayList<Double> ans = new ArrayList<>();

        int problemSize = problems.size();
        HashMap<String, Integer> problemIdx = new HashMap<>();
        ArrayList<HashMap<String, Integer>> cnt = new ArrayList<>();
        ArrayList<HashSet<String>> acers = new ArrayList<>();
        for (int i = 0; i < problemSize; ++i) {
            problemIdx.put(problems.get(i).getIndex(), i);
            cnt.add(new HashMap<>());
            acers.add(new HashSet<>());
        }

        int ctime = contest.getDurationSeconds() + 1;
        List<Submission> submissions = submissionService.getSubmissions(String.valueOf(contest.getId()), String.valueOf(ctime));

        for (Submission sub : submissions) {
            if (problemIdx.containsKey(sub.getProblem().getIndex())) {

                int problemIndex = problemIdx.get(sub.getProblem().getIndex());
                String partyName = "";

                for (String auth : sub.getAuthor().getMembers()) {
                    partyName += auth + "_";
                }

                if (!acers.get(problemIndex).contains(partyName)) {
                    int val = 0;
                    if (cnt.get(problemIndex).containsKey(partyName)) {
                        val = cnt.get(problemIndex).get(partyName);
                    }
                    ++val;
                    cnt.get(problemIndex).put(partyName, val);
                }

                if (sub.isVerdictOK()) {
                    acers.get(problemIndex).add(partyName);
                }

            }
        }

        for (int i = 0; i < problemSize; ++i) {
            long actry = 0;
            long acman = cnt.get(i).size();
            for (String acer : cnt.get(i).keySet()) {
                actry += cnt.get(i).get(acer);
            }
            ans.add((double) (actry) / acman);
        }

        return ans;
    }

}
