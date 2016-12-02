package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujy on 16-12-2.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public List<User> listUsers(Integer page, Integer max) {
        return new ArrayList<>();
    }

    public User getUser(String handle) {
        return new User();
    }

}
