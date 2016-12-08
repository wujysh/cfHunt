package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.User;
import cn.edu.fudan.codeforces.ranking.mysql.DevelopmentService;
import cn.edu.fudan.codeforces.ranking.mysql.UserNumberService;
import cn.edu.fudan.codeforces.ranking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wujy on 16-12-2.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;
    private final UserNumberService userNumberService;
    private final DevelopmentService developmentService;

    @Autowired
    public UserController(UserService userService, 
    		UserNumberService userNumberService,
    		DevelopmentService developmentService) {
        this.userService = userService;
        this.userNumberService = userNumberService;
        this.developmentService = developmentService;
    }

    @RequestMapping("/user")
    public ModelAndView listUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("user/list");
        List<User> users = userService.listUsers(page, max);

        int totalPage = (int) Math.ceil(89342 / max), minPage = Math.max(1, page - 3), maxPage = Math.min(minPage + 6, totalPage);
        minPage = Math.max(1, maxPage - 6);
        mav.addObject("users", users);
        mav.addObject("page", page);
        mav.addObject("max", max);
        mav.addObject("totalPage", totalPage);
        mav.addObject("minPage", minPage);
        mav.addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping("/user/{handle}")
    public ModelAndView userInfo(@PathVariable String handle) throws IOException {
        ModelAndView mav = new ModelAndView("user/info");
        User user = userService.getUser(handle);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("/user")
    public String listUsersByCountry() {
    	Gson gson = new Gson();
    	Map<String, Integer> map = userNumberService.listUsersByCountry();
    	return gson.toJson(map);
    }
    
    @RequestMapping("/user")
    public String listUsersByRank() {
    	Gson gson = new Gson();
    	Map<String, Integer> map = userNumberService.listUsersByRank();
    	return gson.toJson(map);
    }
    
    @RequestMapping("/user/development")
    public String developmentByYear() {
    	Gson gson = new Gson();
    	Map<String, Integer> map = developmentService.getDevelopmentByYear();
    	return gson.toJson(map);
    }
    
}
