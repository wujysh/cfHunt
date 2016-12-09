package cn.edu.fudan.codeforces.ranking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wujy on 16-12-9.
 */
@Controller
public class StatisitcsController {

    @RequestMapping("/statistics/user")
    public String statisitcsUser() {
        return "statisitcs/user";
    }

    @RequestMapping("/statistics/problem")
    public String statisitcsProblem() {
        return "statisitcs/problem";
    }

    @RequestMapping("/statistics/contest")
    public String statisitcsContest() {
        return "statisitcs/contest";
    }

}
