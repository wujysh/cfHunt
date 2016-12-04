package cn.edu.fudan.codeforces.ranking.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * Created by wujy on 16-12-4.
 */
public class BaseService {

    static Configuration conf;

    static {
        conf = HBaseConfiguration.create();
    }

}
