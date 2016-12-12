package cn.edu.fudan.codeforces.ranking.service.mysql;

import cn.edu.fudan.codeforces.ranking.entity.Tableclass;
import cn.edu.fudan.codeforces.ranking.util.ByteUtil;
import cn.edu.fudan.codeforces.ranking.util.StringUtil;
import org.apache.hadoop.hbase.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class ProblemDifficultyService extends BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemDifficultyService.class.getName());

    public Map<String, Float> listProblemDifficultyByRank(String contestId, String index) throws IOException {
        Map<String, Float> map = new HashMap<>();
        Tableclass tc= ByteUtil.getTableclass(contestId);
        try {
            String sql = "SELECT rank, count(distinct "+tc.submission+".id) as number FROM "+tc.submission+", user, "+tc.party+","+tc.problem+" WHERE "+tc.submission+".contestid="
                    + contestId
                    + " and "+tc.submission+".problem = "+tc.problem+".id and "+tc.problem+".numindex = '" + index
                    + "' and "+tc.submission+".author="+tc.party+".id and "+tc.party+".members = user.handle and verdict = 'OK' GROUP BY user.rank";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                map.put(rank, (float) number);
            }
            sql = "SELECT rank, count(distinct submission.id) as number FROM submission, user, party, problem WHERE submission.contestid="
                    + contestId
                    + " and submission.problem = problem.id and problem.numindex = '" + index
                    + "' and submission.author=party.id and party.members = user.handle GROUP BY user.rank";
            selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String rank = selectRes.getString("rank");
                int number = selectRes.getInt("number");
                float successNum = map.get(rank);
                map.put(rank, successNum / (float) number);
                System.out.println(rank+number);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return map;
    }
    
    public List<Pair<String, Float>> listProblemDifficultyByCountry(String contestId, String index) throws IOException {
        Map<String, Float> map = new HashMap<>();
        List<Pair<String, Float>> ret = new ArrayList<>();
        Tableclass tc= ByteUtil.getTableclass(contestId);
        try {
            String sql = "SELECT country, count(distinct "+tc.submission+".id) as number FROM "+tc.submission+", user, "+tc.party+", "+tc.problem+" WHERE "+tc.submission+".contestid="
                    + contestId
                    + " and "+tc.submission+".problem = "+tc.problem+".id and "+tc.problem+".numindex = '" + index
                    + "' and "+tc.submission+".author="+tc.party+".id and "+tc.party+".members = user.handle and verdict = 'OK' GROUP BY user.country ";
            ResultSet selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                int number = selectRes.getInt("number");
                map.put(country, (float) number);
            }
            sql = "SELECT country, count(distinct "+tc.submission+".id) as number FROM "+tc.submission+", user, "+tc.party+", "+tc.problem+" WHERE "+tc.submission+".contestid="
                    + contestId
                    + " and "+tc.submission+".problem = "+tc.problem+".id and "+tc.problem+".numindex = '" + index
                    + "' and "+tc.submission+".author="+tc.party+".id and "+tc.party+".members = user.handle GROUP BY user.country ";
            selectRes = getStmt().executeQuery(sql);
            while (selectRes.next()) { // 循环输出结果集
                String country = selectRes.getString("country");
                int number = selectRes.getInt("number");
                if(map.get(country)!=null) {
                    float successNum = map.get(country);
                    if (number >= 10) {
                        ret.add(new Pair<>(StringUtil.handleCountryName(country), successNum / (float) number));
                    }
                }else{
                    ret.add(new Pair<>(StringUtil.handleCountryName(country), 0f));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        ret.sort((o1, o2) -> (int) (o2.getSecond() - o1.getSecond()));
        return ret;
    }

}
