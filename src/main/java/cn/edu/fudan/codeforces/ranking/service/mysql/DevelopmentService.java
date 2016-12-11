package cn.edu.fudan.codeforces.ranking.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DevelopmentService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentService.class.getName());

    public Map<Date, Integer> getDevelopmentByYear() {
        Map<Date, Integer> map = new TreeMap<>();
        String sql = "select registrationTimeSeconds from user";

        ResultSet selectRes;
        try {
            selectRes = stmt.executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                Date date = new Date(selectRes.getLong("registrationTimeSeconds") * 1000L);
                date.setDate(1);
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                if (map.containsKey(date)) {
                    int value = map.get(date);
                    map.put(date, value + 1);
                } else {
                    map.put(date, 1);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

}
