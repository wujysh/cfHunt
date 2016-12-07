package cn.edu.fudan.codeforces.ranking.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserService extends BaseMySQLService {

	public Map<String, Integer> listUsersByCountry() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		String sql = "SELECT country, count(*) as number FROM user group by country order by number DESC";
		ResultSet selectRes = stmt.executeQuery(sql);
		while (selectRes.next()) { // 循环输出结果集
			map.put(selectRes.getString("country"), selectRes.getInt("number"));
		}
		return map;
	}
	
	public Map<String, Integer> listUsersByRank() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		String sql = "SELECT rank, count(*) as number FROM user group by rank order by number DESC";
		ResultSet selectRes = stmt.executeQuery(sql);
		while (selectRes.next()) { // 循环输出结果集
			map.put(selectRes.getString("rank"), selectRes.getInt("number"));
		}
		return map;
	}
}
