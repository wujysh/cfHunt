package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.Submission;
import cn.edu.fudan.codeforces.ranking.service.SubmissionService;
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
 * Created by wujy on 16-12-4.
 */
@Controller
public class SubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class.getName());

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @RequestMapping("/submission")
    public ModelAndView listSubmissions(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("submission/list");
        List<Submission> submissions = submissionService.listSubmissions(page, max);
        mav.addObject("submissions", submissions);
        return mav;
    }

}
