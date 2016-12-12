package cn.edu.fudan.codeforces.ranking.service.mysql;

import cn.edu.fudan.codeforces.ranking.entity.Tableclass;
import cn.edu.fudan.codeforces.ranking.util.ByteUtil;
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

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

@Service
public class ContestPopularityService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(ContestPopularityService.class.getName());

    public Map<String, Integer> listContestPopularityByRank(String contestId) {
        Map<String, Integer> map = new HashMap<>();
        Tableclass tc = ByteUtil.getTableclass(contestId);
        try {
            String sql = "SELECT rank, count(distinct user.handle) as number FROM "+tc.submission+", "+tc.party+", user WHERE "+tc.submission+".contestId = "
                    + contestId
                    + " and "+tc.submission+".author="+tc.party+".id and "+tc.party+".members = user.handle GROUP BY user.rank";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                System.out.println(rank+"  "+number);
                map.put(rank, number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    public List<Pair<String, Integer>> listContestPopularityByCountry(String contestId) {
        List<Pair<String, Integer>> ret = new ArrayList<>();
        Tableclass tc = ByteUtil.getTableclass(contestId);

        try {
            String sql = "SELECT country, count(distinct user.handle) as number FROM "+tc.submission+", "+tc.party+", user WHERE "+tc.submission+".contestid = " + contestId +
                    " and "+tc.submission+".author="+tc.party+".id and "+tc.party+".members = user.handle GROUP BY user.country order by number desc";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                System.out.println(country);
                if (country != null) {
                    switch (country) {
                        case "United States (USA)":
                            country = "United States of America";
                            break;
                        case "Korea, Republic of":
                            country = "South Korea";
                            break;
                        case "Korea,DPR":
                            country = "North Korea";
                            break;
                        case "The Netherlands":
                            country = "Netherlands";
                            break;
                    }
                }
                ret.add(new Pair<>(country, selectRes.getInt("number")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }


}
