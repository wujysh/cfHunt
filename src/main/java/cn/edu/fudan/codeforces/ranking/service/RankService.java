package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.RanklistRow;
import cn.edu.fudan.codeforces.ranking.entity.Submission;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by house on 12/4/16.
 */
@Service
public class RankService extends BaseService {

    public List<RanklistRow> getRank(String contestIdStr, String rtime, Integer page, Integer max) throws IOException {
        ArrayList<RanklistRow> ans = new ArrayList<>();

        ContestService cs = new ContestService();
        Contest contest = cs.getContest(contestIdStr);
        ProblemService ps = new ProblemService();
        List<Problem> problems = ps.getProblemForContest(contestIdStr);
        SubmissionService ss = new SubmissionService();
        List<Submission> submissions = ss.getSubmissions(contestIdStr, rtime);

        ArrayList<RanklistRow> totalRank;
        if (contest.getType() == Contest.Type.CF) {
            totalRank = getCFRank(submissions, problems);
        } else {
            totalRank = getOtherRank(submissions);
        }

        for (int idx = page * max; idx < (page + 1) * max && idx < totalRank.size(); ++idx) {
            ans.add(totalRank.get(idx));
        }

        return ans;
    }

    private ArrayList<RanklistRow> getCFRank(List<Submission> submissions, List<Problem> problems) {
        // TODO
        return null;
    }

    private ArrayList<RanklistRow> getOtherRank(List<Submission> submissions) {
        // TODO
        return null;
    }

}
