package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public List<Problem> listProblems(Integer page, Integer max) {
        return new ArrayList<>();
    }

    public Problem getProblem(String handle) {
        return new Problem();
    }

}
