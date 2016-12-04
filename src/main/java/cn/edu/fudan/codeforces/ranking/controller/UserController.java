package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.User;
import cn.edu.fudan.codeforces.ranking.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/list")
    public ModelAndView listUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
        ModelAndView mav = new ModelAndView("user/list");
        List<User> users = userService.listUsers(page, max);
        mav.addObject("users", users);
        return mav;
    }

    @RequestMapping("/info")
    public ModelAndView userInfo(@RequestParam("handle") String handle) throws IOException {
        ModelAndView mav = new ModelAndView("user/info");
        User user = userService.getUser(handle);
        mav.addObject("user", user);
        return mav;
    }

}
