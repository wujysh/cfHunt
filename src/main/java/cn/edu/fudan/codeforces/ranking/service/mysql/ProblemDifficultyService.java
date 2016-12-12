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
            String sql = "SELECT rank, count(*) as number FROM submission WHERE contestId = "
                    + contestId
                    + " and numIndex = " + index
                    + " and state = 'SUCCESS' GROUP BY rank";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                map.put(rank, (float) number);
            }
            sql = "SELECT rank, count(*) as number FROM submission WHERE contestId = "
                    + contestId
                    + " and numIndex = " + index
                    + " GROUP BY rank";
            selectRes = getStmt().executeQuery(sql);
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

}
