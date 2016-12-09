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
 * Created by house on 12/4/16.
 */
@Service
public class UserService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
    private static HTable tableUser;

    static {
        String tablename = "codeforces:user";
        try {
            tableUser = (HTable) conn.getTable(TableName.valueOf(tablename));
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public List<User> listUsers(Integer page, Integer max) throws IOException {
        --page;
        ArrayList<User> ans = new ArrayList<>();
        int cnt = 0;

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

        return ans;
    }

    public User getUser(String handle) throws IOException {
        Get get = new Get(Bytes.toBytes(handle));
        Result result = tableUser.get(get);

        return buildUser(result);
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
                switch (qualifier) {
                    case "email":
                        res.setEmail(valueStr);
                        break;
                    case "vkId":
                        res.setVkId(valueStr);
                        break;
                    case "openId":
                        res.setOpenId(valueStr);
                        break;
                    case "firstName":
                        res.setFirstName(valueStr);
                        break;
                    case "lastName":
                        res.setLastName(valueStr);
                        break;
                    case "country":
                        res.setCountry(valueStr);
                        break;
                    case "city":
                        res.setCity(valueStr);
                        break;
                    case "organization":
                        res.setOrganization(valueStr);
                        break;
                    case "contribution":
                        res.setContribution(valueInt);
                        break;
                    case "lastOnlineTimeSeconds":
                        res.setLastOnlineTimeSeconds(valueInt);
                        break;
                    case "registrationTimeSeconds":
                        res.setRegistrationTimeSeconds(valueInt);
                        break;
                    case "rank":
                        res.setRank(valueStr);
                        break;
                    case "maxRank":
                        res.setMaxRank(valueStr);
                        break;
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
