package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.User;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujy on 16-12-2.
 */
@Service
public class UserService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
    private static String tablenameUser = "codeforces:user";
    private static HTable tableUser;

    public List<User> listUsers(Integer page, Integer max) throws IOException {
        --page;
        ArrayList<User> ans = new ArrayList<>();
        int cnt = 0;

        Connection conn = ConnectionFactory.createConnection(conf);
        tableUser = (HTable) conn.getTable(TableName.valueOf(tablenameUser));

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("email"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("firstName"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("lastName"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("rank"));
        ResultScanner scanner = tableUser.getScanner(scan);
        for (Result result : scanner) {
            if (cnt >= page * max && cnt < (page + 1) * max)
                ans.add(buildUser(result));
            ++cnt;
        }

        tableUser.close();
        conn.close();

        return ans;
    }

    public User getUser(String handle) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        tableUser = (HTable) conn.getTable(TableName.valueOf(tablenameUser));

        Get get = new Get(Bytes.toBytes(handle));
        Result result = tableUser.get(get);
        User ans = buildUser(result);

        tableUser.close();
        conn.close();

        return ans;
    }

    private User buildUser(Result result) {
        User res = new User();

        res.setHandle(Bytes.toString(result.getRow()));

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            Integer valueInt = Bytes.readAsInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            if (family.equals("info")) {

                if (qualifier.equals("email")) {
                    res.setEmail(valueStr);
                } else if (qualifier.equals("vkId")) {
                    res.setVkId(valueStr);
                } else if (qualifier.equals("openId")) {
                    res.setOpenId(valueStr);
                } else if (qualifier.equals("firstName")) {
                    res.setFirstName(valueStr);
                } else if (qualifier.equals("lastName")) {
                    res.setLastName(valueStr);
                } else if (qualifier.equals("country")) {
                    res.setCountry(valueStr);
                } else if (qualifier.equals("city")) {
                    res.setCity(valueStr);
                } else if (qualifier.equals("organization")) {
                    res.setOrganization(valueStr);
                } else if (qualifier.equals("contribution")) {
                    res.setContribution(valueInt);
                } else if (qualifier.equals("lastOnlineTimeSeconds")) {
                    res.setLastOnlineTimeSeconds(valueInt);
                } else if (qualifier.equals("registrationTimeSeconds")) {
                    res.setRegistrationTimeSeconds(valueInt);
                } else if (qualifier.equals("rank")) {
                    res.setRank(valueStr);
                } else if (qualifier.equals("maxRank")) {
                    res.setMaxRank(valueStr);
                }

            } else if (family.equals("rating")) {

                if (qualifier.equals("rating")) {
                    res.setRating(valueInt);
                } else if (qualifier.equals("maxRating")) {
                    res.setMaxRating(valueInt);
                }

            }

        }

        return res;
    }

}
