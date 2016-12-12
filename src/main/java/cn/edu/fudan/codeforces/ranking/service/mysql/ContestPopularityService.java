package cn.edu.fudan.codeforces.ranking.service.mysql;

import cn.edu.fudan.codeforces.ranking.util.ByteUtil;
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
public class ContestPopularityService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(ContestPopularityService.class.getName());

    public Map<String, Integer> listContestPopularityByRank(String contestId) {
        Map<String, Integer> map = new HashMap<>();
        if (Integer.parseInt(contestId) > 5) contestId = "5";
        TableClass tc = ByteUtil.getTableclass(contestId);
        try {
            String sql = "SELECT rank, count(distinct user.handle) as number FROM " + tc.submission + ", " + tc.party + ", user WHERE " + tc.submission + ".contestId = "
                    + contestId
                    + " and " + tc.submission + ".author=" + tc.party + ".id and " + tc.party + ".members = user.handle GROUP BY user.rank";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                System.out.println(rank + "  " + number);
                map.put(rank, number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    public List<Pair<String, Integer>> listContestPopularityByCountry(String contestId) {
        if (Integer.parseInt(contestId) > 5) contestId = "5";
        List<Pair<String, Integer>> ret = new ArrayList<>();
        TableClass tc = ByteUtil.getTableclass(contestId);
        try {
            String sql = "SELECT country, count(distinct user.handle) as number FROM " + tc.submission + ", " + tc.party + ", user WHERE " + tc.submission + ".contestid = " + contestId +
                    " and " + tc.submission + ".author=" + tc.party + ".id and " + tc.party + ".members = user.handle GROUP BY user.country order by number desc";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                ret.add(new Pair<>(StringUtil.handleCountryName(selectRes.getString("country")), selectRes.getInt("number")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

}
