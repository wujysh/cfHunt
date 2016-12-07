package cn.edu.fudan.codeforces.ranking.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BaseMySQLService {
	static Connection con; //定义一个MYSQL链接对象
	static Statement stmt; //创建声明
	
	static {
	    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
			con = DriverManager.getConnection("jdbc:mysql://10.131.247.131:3306/codeforces", "root", "123456"); //链接本地MYSQL
		    stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
