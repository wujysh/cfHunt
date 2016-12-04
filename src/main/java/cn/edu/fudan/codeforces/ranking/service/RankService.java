package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.RanklistRow;
import cn.edu.fudan.codeforces.ranking.entity.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class RankService {

    public List<RanklistRow> getRank(String contestIdStr, String rtime, Integer page, Integer max) {
        ArrayList<RanklistRow> ans = new ArrayList<>();

        ProblemService ps = new ProblemService();
        List<Problem> problems = ps.getProblemForContest(contestIdStr);
        SubmissionService ss = new SubmissionService();
        List<Submission> submissions = ss.getSubmissions(contestIdStr, rtime);

        return ans;
    }

}
