package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by house on 12/7/16.
 */
@Service
public class RankService extends BaseService {

    public List<RanklistRow> getRank(String contestIdStr, String rtime, Integer page, Integer max) throws IOException {
        --page;
        ArrayList<RanklistRow> ans = new ArrayList<>();

        ContestService cs = new ContestService();
        Contest contest = cs.getContest(contestIdStr);
        ProblemService ps = new ProblemService();
        List<Problem> problems = ps.getProblemForContest(contestIdStr);
        SubmissionService ss = new SubmissionService();
        List<Submission> submissions = ss.getSubmissions(contestIdStr, rtime);

        HashMap<String, Integer> problemIdx = new HashMap<>();
        ArrayList<Float> points = new ArrayList<>();
        int problemSize = problems.size();
        for (int i = 0; i < problemSize; ++i) {
            problemIdx.put(problems.get(i).getIndex(), i);
            points.add(problems.get(i).getPoints());
        }

        HashMap<String, ArrayList<Integer>> cnt = new HashMap<>();
        HashMap<String, ArrayList<Integer>> tim = new HashMap<>();

        for (Submission sub : submissions) {
            for (String auth : sub.getAuthor().getMembers()) {

                if (!cnt.containsKey(auth)) {
                    ArrayList<Integer> tmp1 = new ArrayList<>();
                    ArrayList<Integer> tmp2 = new ArrayList<>();
                    for (int j = 0; j < problemSize; ++j) {
                        tmp1.add(0);
                        tmp2.add(0);
                    }
                    cnt.put(auth, tmp1);
                    tim.put(auth, tmp2);
                }

                int probIdx = problemIdx.get(sub.getProblem().getIndex());

                ArrayList<Integer> tmp = cnt.get(auth);
                tmp.set(probIdx, tmp.get(probIdx) + 1);

                if (sub.isVerdictOK()) {
                    tim.get(auth).set(probIdx, sub.getRelativeTimeSeconds());
                } else {
                    tim.get(auth).set(probIdx, 0);
                }

            }
        }

        ArrayList<RanklistRow> totalRank;
        if (contest.getType() == Contest.Type.CF) {
            totalRank = getCFRank(cnt, tim, points);
        } else {
            totalRank = getICPCRank(cnt, tim, problemSize);
        }

        for (int idx = page * max; idx < (page + 1) * max && idx < totalRank.size(); ++idx) {
            ans.add(totalRank.get(idx));
        }

        return ans;
    }

    class CFComparator implements Comparator<RanklistRow> {
        public final int compare(RanklistRow pFirst, RanklistRow pSecond) {
            if (pFirst.getPoints() < pSecond.getPoints()) {
                return -1;
            }
            if (pFirst.getPoints() > pSecond.getPoints()) {
                return 1;
            }
            return 0;
        }
    }

    private ArrayList<RanklistRow> getCFRank(HashMap<String, ArrayList<Integer>> cnt,
                                             HashMap<String, ArrayList<Integer>> tim, ArrayList<Float> points) {
        ArrayList<RanklistRow> ans = new ArrayList<>();

        for (String auth : cnt.keySet()) {

            RanklistRow row = new RanklistRow();
            row.setHandle(auth);
            int penalty = 0;
            float getpoints = 0;
            ArrayList<ProblemResult> resList = new ArrayList<>();

            for (int i = 0; i < points.size(); ++i) {
                ProblemResult res = new ProblemResult();
                if (tim.get(auth).get(i) > 0) {
                    res.setPoints(Math.max(points.get(i) / 2,
                            points.get(i) - 20 * (cnt.get(auth).get(i) - 1) - tim.get(auth).get(i) / 60));
                    res.setPenalty(20 * (cnt.get(auth).get(i) - 1));
                    res.setAcTimeSeconds(tim.get(auth).get(i));
                    res.setRejectedAttemptCount(cnt.get(auth).get(i) - 1);

                    penalty += res.getPenalty();
                    getpoints += res.getPoints();
                } else {
                    res.setPoints(0f);
                    res.setPenalty(0);
                    res.setAcTimeSeconds(0);
                    res.setRejectedAttemptCount(0);
                }
                resList.add(res);
            }

            row.setPenalty(penalty);
            row.setPoints(getpoints);
            row.setProblemResults(resList);

            ans.add(row);

        }

        ans.sort(new CFComparator());

        return ans;
    }

    class ICPCComparator implements Comparator<RanklistRow> {
        public final int compare(RanklistRow pFirst, RanklistRow pSecond) {
            if (pFirst.getPoints() < pSecond.getPoints()) {
                return -1;
            }
            if (pFirst.getPoints() > pSecond.getPoints()) {
                return 1;
            }
            if (pFirst.getPenalty() > pSecond.getPenalty()) {
                return -1;
            }
            if (pFirst.getPenalty() < pSecond.getPenalty()) {
                return 1;
            }
            return 0;
        }
    }

    private ArrayList<RanklistRow> getICPCRank(HashMap<String, ArrayList<Integer>> cnt,
                                               HashMap<String, ArrayList<Integer>> tim, int problemSize) {
        ArrayList<RanklistRow> ans = new ArrayList<>();

        for (String auth : cnt.keySet()) {

            RanklistRow row = new RanklistRow();
            row.setHandle(auth);
            int penalty = 0;
            int getpoints = 0;
            ArrayList<ProblemResult> resList = new ArrayList<>();

            for (int i = 0; i < problemSize; ++i) {
                ProblemResult res = new ProblemResult();
                if (tim.get(auth).get(i) > 0) {
                    res.setPoints(1f);
                    res.setPenalty(20 * (cnt.get(auth).get(i) - 1) + tim.get(auth).get(i));
                    res.setAcTimeSeconds(tim.get(auth).get(i));
                    res.setRejectedAttemptCount(cnt.get(auth).get(i) - 1);

                    penalty += res.getPenalty();
                    ++getpoints;
                } else {
                    res.setPoints(0f);
                    res.setPenalty(0);
                    res.setAcTimeSeconds(0);
                    res.setRejectedAttemptCount(0);
                }
                resList.add(res);
            }

            row.setPenalty(penalty);
            row.setPoints((float) getpoints);
            row.setProblemResults(resList);

            ans.add(row);

        }

        ans.sort(new ICPCComparator());

        return ans;
    }

}
