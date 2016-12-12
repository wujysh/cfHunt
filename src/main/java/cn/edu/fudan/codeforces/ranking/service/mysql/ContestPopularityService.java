package cn.edu.fudan.codeforces.ranking.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContestPopularityService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(ContestPopularityService.class.getName());

    public Map<String, Integer> listContestPopularityByRank(String contestId) {
        Map<String, Integer> map = new HashMap<>();
        try {
            String sql = "SELECT rank, count(*) as number FROM submission, user WHERE contestId = "
                    + contestId
                    + " and submission.user = user.handle GROUP BY user.rank";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                map.put(rank, number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    public Map<String, Integer> listContestPopularityByCountry(String contestId) {
        Map<String, Integer> map = new HashMap<>();
        try {
            String sql = "SELECT country, count(*) as number FROM submission, user WHERE contestId = "
                    + contestId
                    + " and submission.user = user.handle GROUP BY user.country";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                int number = selectRes.getInt("number");
                map.put(country, number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

}
