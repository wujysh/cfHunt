package cn.edu.fudan.codeforces.ranking.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BaseMySQLService {

    private static final Logger logger = LoggerFactory.getLogger(BaseMySQLService.class.getName());

    static Statement stmt;  //创建声明

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();  //MYSQL驱动
            Connection con = DriverManager.getConnection("jdbc:mysql://10.131.247.131:3306/tongji", "root", "123456");
            stmt = con.createStatement();
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

}
