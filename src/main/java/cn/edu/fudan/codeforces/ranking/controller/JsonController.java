package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.service.mysql.ContestPopularityService;
import cn.edu.fudan.codeforces.ranking.service.mysql.DevelopmentService;
import cn.edu.fudan.codeforces.ranking.service.mysql.ProblemDifficultyService;
import cn.edu.fudan.codeforces.ranking.service.mysql.UserNumberService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by wujy on 16-12-9.
 */
@RestController
public class JsonController {

    private final UserNumberService userNumberService;
    private final DevelopmentService developmentService;
    private final ProblemDifficultyService problemDifficultyService;
    private final ContestPopularityService contestPopularityService;

    @Autowired
    public JsonController(UserNumberService userNumberService,
                          DevelopmentService developmentService,
                          ProblemDifficultyService problemDifficultyService,
                          ContestPopularityService contestPopularityService) {
        this.userNumberService = userNumberService;
        this.developmentService = developmentService;
        this.problemDifficultyService = problemDifficultyService;
        this.contestPopularityService = contestPopularityService;
    }

    @RequestMapping("/json/user/country")
    public String usersByCountry() {
        Gson gson = new Gson();
        Map<String, Integer> map = userNumberService.listUsersByCountry();
        return gson.toJson(map);
    }

    @RequestMapping("/json/user/rank")
    public String usersByRank() {
        Gson gson = new Gson();
        Map<String, Integer> map = userNumberService.listUsersByRank();
        return gson.toJson(map);
    }

    @RequestMapping("/json/user/year")
    public String usersByYear() {
        Map<Date, Integer> map = developmentService.getDevelopmentByYear();
        String ret = "[";
        int cnt = 0;
        for (Map.Entry<Date, Integer> e : map.entrySet()) {
            if (++cnt > 1) ret += ",";
            ret = ret + "[Date.UTC(" + String.valueOf(e.getKey().getYear()+1900) + "," + String.valueOf(e.getKey().getMonth()+1) + ",1)," + e.getValue() + "]";
        }
        ret += "]";
        return ret;
    }

    @RequestMapping("/json/problem/rank/{contestId}/{index}")
    public String problemDifficultyByRank(@PathVariable String contestId,
                                          @PathVariable String index) throws IOException {
        Gson gson = new Gson();
        Map<String, Float> map = problemDifficultyService.listProblemDifficultyByRank(contestId, index);
        return gson.toJson(map);
    }
    
    @RequestMapping("/json/problem/country/{contestId}/{index}")
    public String problemDifficultyByCountry(@PathVariable String contestId,
                                          @PathVariable String index) throws IOException {
        Gson gson = new Gson();
        Map<String, Float> map = problemDifficultyService.listProblemDifficultyByCountry(contestId, index);
        return gson.toJson(map);
    }

    @RequestMapping("/json/contest/{contestId}/rank")
    public String contestPopularityByRank(@PathVariable String contestId) throws IOException {
        Gson gson = new Gson();
        Map<String, Integer> map = contestPopularityService.listContestPopularityByRank(contestId);
        return gson.toJson(map);
    }

    @RequestMapping("/json/contest/{contestId}/country")
    public String contestPopularityByCountry(@PathVariable String contestId) throws IOException {
        Gson gson = new Gson();
        Map<String, Integer> map = contestPopularityService.listContestPopularityByCountry(contestId);
        return gson.toJson(map);
    }

}
