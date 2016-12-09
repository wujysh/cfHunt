package cn.edu.fudan.codeforces.ranking.service.hbase;

import cn.edu.fudan.codeforces.ranking.service.BaseService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wujy on 16-12-4.
 */
public class BaseHBaseService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseHBaseService.class.getName());

    static Connection conn;

    static {
        Configuration conf = HBaseConfiguration.create();
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

}
