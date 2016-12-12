package cn.edu.fudan.codeforces.ranking.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProblemDifficultyService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemDifficultyService.class.getName());

    public Map<String, Float> listProblemDifficultyByRank(String contestId, String index) throws IOException {
        Map<String, Float> map = new HashMap<>();

        try {
            String sql = "SELECT rank, count(*) as number FROM submission, user, party, problem WHERE submission.contestid="
                    + contestId
                    + " and submission.problem = problem.id and problem.numindex = '" + index
                    + "' and submission.author=party.id and party.members = user.handle and verdict = 'OK' GROUP BY user.rank";
            ResultSet selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                map.put(rank, (float) number);
            }
            sql = "SELECT rank, count(*) as number FROM submission, user, party, problem WHERE submission.contestid="
                    + contestId
                    + " and submission.problem = problem.id and problem.numindex = '" + index
                    + "' and submission.author=party.id and party.members = user.handle GROUP BY user.rank";
            selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                float successNum = map.get(rank);
                map.put(rank, successNum / (float) number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }
    
    public Map<String, Float> listProblemDifficultyByCountry(String contestId, String index) throws IOException {
        Map<String, Float> map = new HashMap<>();

        try {
            String sql = "SELECT country, count(distinct user.handle) as number FROM submission, user, party, problem WHERE submission.contestid="
                    + contestId
                    + " and submission.problem = problem.id and problem.numindex = '" + index
                    + "' and submission.author=party.id and party.members = user.handle and verdict = 'OK' GROUP BY user.country";
            ResultSet selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                int number = selectRes.getInt("number");
                map.put(country, (float) number);
            }
            sql = "SELECT country, count(distinct user.handle) as number FROM submission, user, party, problem WHERE submission.contestid="
                    + contestId
                    + " and submission.problem = problem.id and problem.numindex = '" + index
                    + "' and submission.author=party.id and party.members = user.handle GROUP BY user.country";
            selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                int number = selectRes.getInt("number");
                float successNum = map.get(country);
                map.put(country, successNum / (float) number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

}
