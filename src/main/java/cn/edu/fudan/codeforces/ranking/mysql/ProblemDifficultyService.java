package cn.edu.fudan.codeforces.ranking.mysql;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.fudan.codeforces.ranking.entity.Problem;

public class ProblemDifficultyService extends BaseMySQLService{
	private static final Logger logger = LoggerFactory.getLogger(ProblemDifficultyService.class.getName());
	
	 public List<Problem> listContests(Integer page, Integer max) throws IOException, SQLException {
		 List<Problem> aList = null;
		 String sql = "?????"; //TODO
		 ResultSet selectRes = stmt.executeQuery(sql);
		 while (selectRes.next()) { //循环输出结果集
             String username = selectRes.getString("username");
             String password = selectRes.getString("password");
         }
		 return aList; 
	 }
}
