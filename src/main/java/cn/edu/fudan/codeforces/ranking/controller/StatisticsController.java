package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.service.mysql.ContestPopularityService;
import cn.edu.fudan.codeforces.ranking.service.mysql.DevelopmentService;
import cn.edu.fudan.codeforces.ranking.service.mysql.ProblemDifficultyService;
import cn.edu.fudan.codeforces.ranking.service.mysql.UserNumberService;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wujy on 16-12-9.
 */
@Controller
public class StatisticsController {

    private final UserNumberService userNumberService;
    private final DevelopmentService developmentService;
    private final ProblemDifficultyService problemDifficultyService;
    private final ContestPopularityService contestPopularityService;

    @Autowired
    public StatisticsController(UserNumberService userNumberService,
                                DevelopmentService developmentService,
                                ProblemDifficultyService problemDifficultyService,
                                ContestPopularityService contestPopularityService) {
        this.userNumberService = userNumberService;
        this.developmentService = developmentService;
        this.problemDifficultyService = problemDifficultyService;
        this.contestPopularityService = contestPopularityService;
    }

    @RequestMapping("/statistics/user/rank")
    public ModelAndView statisticsUserRank() {
        ModelAndView mav = new ModelAndView("statistics/user_rank");

        Map<String, Integer> map = userNumberService.listUsersByRank();
        Map<String, Integer> mapChina = userNumberService.listUsersByRankAndCountry("China");
        Map<String, Integer> mapRussia = userNumberService.listUsersByRankAndCountry("Russia");
        Map<String, Integer> mapIndia = userNumberService.listUsersByRankAndCountry("India");

        List<String> key = new ArrayList<>();
        key.add("legendary grandmaster");
        key.add("international grandmaster");
        key.add("grandmaster");
        key.add("international master");
        key.add("master");
        key.add("candidate master");
        key.add("expert");
        key.add("specialist");
        key.add("pupil");
        key.add("newbie");
        Collections.reverse(key);
        mav.addObject("key", key);

        List<Integer> value = key.stream().map(map::get).collect(Collectors.toList());
        List<Integer> valueChina = key.stream().map(mapChina::get).collect(Collectors.toList());
        List<Integer> valueRussia = key.stream().map(mapRussia::get).collect(Collectors.toList());
        List<Integer> valueIndia = key.stream().map(mapIndia::get).collect(Collectors.toList());
        mav.addObject("value", value);
        mav.addObject("valueChina", valueChina);
        mav.addObject("valueRussia", valueRussia);
        mav.addObject("valueIndia", valueIndia);

        return mav;
    }

    @RequestMapping("/statistics/user/time")
    public String statisticsUserTime() {
        return "statistics/user_time";
    }

    @RequestMapping("/statistics/user/country")
    public ModelAndView statisticsUserCountry() {
        ModelAndView mav = new ModelAndView("statistics/user_country");
        List<Pair<String, Integer>> counts = userNumberService.listUsersByCountry();
        mav.addObject("counts", counts);
        return mav;
    }

    @RequestMapping("/statistics/problem")
    public String statisticsProblem() {
        return "statistics/problem";
    }

    @RequestMapping("/statistics/contest")
    public String statisticsContest() {
        return "statistics/contest";
    }

    @RequestMapping("/contest/{contestId}/statistics/rank")
    public ModelAndView contestPopularityByRank(@PathVariable String contestId) throws IOException {
        Map<String, Integer> map = contestPopularityService.listContestPopularityByRank(contestId);
        ModelAndView mav = new ModelAndView("statistics/contest_rank");
        List<String> key = new ArrayList<>();
        key.add("legendary grandmaster");
        key.add("international grandmaster");
        key.add("grandmaster");
        key.add("international master");
        key.add("master");
        key.add("candidate master");
        key.add("expert");
        key.add("specialist");
        key.add("pupil");
        key.add("newbie");
        Collections.reverse(key);
        List<Integer> value = key.stream().map(map::get).collect(Collectors.toList());
        mav.addObject("key", key);
        mav.addObject("value", value);
        return mav;
    }

    @RequestMapping("/contest/{contestId}/statistics/country")
    public ModelAndView contestPopularityByCountry(@PathVariable String contestId) throws IOException {
        List<Pair<String, Integer>> counts = contestPopularityService.listContestPopularityByCountry(contestId);
        ModelAndView mav = new ModelAndView("statistics/contest_country");
        mav.addObject("counts", counts);
        return mav;
    }

    @RequestMapping("/problem/{contestId}/{index}/statistics/country")
    public ModelAndView problemDifficultyByCountry(@PathVariable String contestId,
                                                   @PathVariable String index) throws IOException {
        List<Pair<String, Float>> counts = problemDifficultyService.listProblemDifficultyByCountry(contestId, index);
        ModelAndView mav = new ModelAndView("statistics/problem_country");
        mav.addObject("counts", counts);
        return mav;
    }

    @RequestMapping("/problem/{contestId}/{index}/statistics/rank")
    public ModelAndView problemDifficultyByRank(@PathVariable String contestId,
                                                @PathVariable String index) throws IOException {
        Map<String, Float> map = problemDifficultyService.listProblemDifficultyByRank(contestId, index);
        ModelAndView mav = new ModelAndView("statistics/contest_rank");
        List<String> key = new ArrayList<>();
        key.add("legendary grandmaster");
        key.add("international grandmaster");
        key.add("grandmaster");
        key.add("international master");
        key.add("master");
        key.add("candidate master");
        key.add("expert");
        key.add("specialist");
        key.add("pupil");
        key.add("newbie");
        Collections.reverse(key);
        List<Float> value = key.stream().map(map::get).collect(Collectors.toList());
        mav.addObject("key", key);
        mav.addObject("value", value);
        return mav;
    }

}
