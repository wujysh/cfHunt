package cn.edu.fudan.codeforces.ranking.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNumberService extends BaseMySQLService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserNumberService.class.getName());

	public Map<String, Integer> listUsersByCountry() {
		Map<String, Integer> map = new HashMap<>();
		String sql = "SELECT country, count(*) as number FROM user group by country order by number DESC";
		ResultSet selectRes;
		try {
			selectRes = stmt.executeQuery(sql);
			while (selectRes.next()) {
				map.put(selectRes.getString("country"), selectRes.getInt("number"));
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
			ResultSet selectRes = stmt.executeQuery(sql);
			while (selectRes.next()) {
				map.put(selectRes.getString("rank"), selectRes.getInt("number"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return map;
	}
}
