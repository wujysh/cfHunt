package cn.edu.fudan.codeforces.ranking.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DevelopmentService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentService.class.getName());

    public Map<String, Integer> getDevelopmentByYear() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "select registrationTimeSeconds from user";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        ResultSet selectRes;
        try {
            selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                Date date = selectRes.getDate("registrationTimeSeconds");
                String month = sdf.format(date);
                if (map.containsKey(month)) {
                    int value = map.get(month);
                    map.put(month, value + 1);
                } else {
                    map.put(month, 1);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return map;
    }

}
