package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import cn.edu.fudan.codeforces.ranking.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Created by wujy on 16-12-2.
 */
@Controller
@RequestMapping("/contest")
public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class.getName());

    private final ContestService contestService;

    @Autowired
    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @RequestMapping("/list")
    public ModelAndView listProblems(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("contest/list");
        List<Contest> contests = contestService.listContests(page, max);
        mav.addObject("contests", contests);
        return mav;
    }

    @RequestMapping("/info")
    public ModelAndView userInfo(@RequestParam("contestId") String contestId) throws IOException {
        ModelAndView mav = new ModelAndView("contest/info");
        Contest contest = contestService.getContest(contestId);
        mav.addObject("contest", contest);
        return mav;
    }

}
