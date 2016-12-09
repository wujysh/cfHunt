package cn.edu.fudan.codeforces.ranking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wujy on 16-12-9.
 */
@Controller
public class StatisticsController {

    @RequestMapping("/statistics/user")
    public String statisticsUser() {
        return "statistics/user";
    }

    @RequestMapping("/statistics/problem")
    public String statisticsProblem() {
        return "statistics/problem";
    }

    @RequestMapping("/statistics/contest")
    public String statisticsContest() {
        return "statistics/contest";
    }

}
