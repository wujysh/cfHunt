package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.service.ContestService;
import cn.edu.fudan.codeforces.ranking.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class.getName());

    private final ContestService contestService;
    private final ProblemService problemService;

    @Autowired
    public ContestController(ContestService contestService, ProblemService problemService) {
        this.contestService = contestService;
        this.problemService = problemService;
    }

    @RequestMapping("/contest")
    public ModelAndView listProblems(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("contest/list");
        List<Contest> contests = contestService.listContests(page, max);
        mav.addObject("contests", contests);
        return mav;
    }

    @RequestMapping("/contest/{contestId}")
    public ModelAndView contestInfo(@PathVariable String contestId) throws IOException {
        ModelAndView mav = new ModelAndView("contest/info");
        Contest contest = contestService.getContest(contestId);
        List<Problem> problems = problemService.getProblemForContest(contestId);
        mav.addObject("contest", contest);
        mav.addObject("problems", problems);
        return mav;
    }

}
