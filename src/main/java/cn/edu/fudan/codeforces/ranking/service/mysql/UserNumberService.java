package cn.edu.fudan.codeforces.ranking.service.mysql;

import cn.edu.fudan.codeforces.ranking.util.StringUtil;
import org.apache.hadoop.hbase.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserNumberService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(UserNumberService.class.getName());

    public List<Pair<String, Integer>> listUsersByCountry() {
        List<Pair<String, Integer>> ret = new ArrayList<>();
        String sql = "SELECT country, count(*) as number FROM user group by country order by number DESC";
        ResultSet selectRes;
        try {
            selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) {
                ret.add(new Pair<>(StringUtil.handleCountryName(selectRes.getString("country")), selectRes.getInt("number")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    public Map<String, Integer> listUsersByRankAndCountry(String country) {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT rank, count(*) as number FROM user where country = '" + country + "' group by rank order by number DESC";
        try {
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) {
                map.put(selectRes.getString("rank"), selectRes.getInt("number"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    public Map<String, Integer> listUsersByRank() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT rank, count(*) as number FROM user group by rank order by number DESC";
        try {
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) {
                map.put(selectRes.getString("rank"), selectRes.getInt("number"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

}
