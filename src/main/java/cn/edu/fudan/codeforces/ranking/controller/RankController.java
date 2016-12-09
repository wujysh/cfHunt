package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.entity.RanklistRow;
import cn.edu.fudan.codeforces.ranking.service.hbase.ContestService;
import cn.edu.fudan.codeforces.ranking.service.hbase.ProblemService;
import cn.edu.fudan.codeforces.ranking.service.hbase.RankService;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Created by wujy on 16-12-2.
 */
@Controller
public class RankController {

    private final RankService rankService;
    private final ContestService contestService;
    private final ProblemService problemService;

    @Autowired
    public RankController(RankService rankService,
                          ContestService contestService,
                          ProblemService problemService) {
        this.rankService = rankService;
        this.contestService = contestService;
        this.problemService = problemService;
    }

    @RequestMapping("/contest/{contestId}/rank")
    public ModelAndView contestInfo(@PathVariable String contestId,
                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "max", defaultValue = "100") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("contest/rank");
        Contest contest = contestService.getContest(contestId);
        List<Problem> problems = problemService.getProblemForContest(contestId);
        Pair<Integer, List<RanklistRow>> ranklist = rankService.getRank(contest, problems, "", page, max);
        mav.addObject("ranklist", ranklist.getSecond());
        mav.addObject("contest", contest);
        mav.addObject("problems", problems);
        mav.addObject("total", ranklist.getFirst());

        int totalPage = (int) Math.ceil(ranklist.getFirst() / max), minPage = Math.max(1, page - 3), maxPage = Math.min(minPage + 6, totalPage);
        minPage = Math.max(1, maxPage - 6);
        mav.addObject("page", page);
        mav.addObject("max", max);
        mav.addObject("totalPage", totalPage);
        mav.addObject("minPage", minPage);
        mav.addObject("maxPage", maxPage);
        return mav;
    }

}
