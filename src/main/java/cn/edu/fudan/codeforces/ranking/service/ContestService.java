package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
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
public class ContestService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ContestService.class.getName());
    private static String tablenameContest = "codeforces:contest";
    private static HTable tableContest;

    public List<Contest> listContests(Integer page, Integer max) throws IOException {
        ArrayList<Contest> ans = new ArrayList<>();
        int cnt = 0;

        Connection conn = ConnectionFactory.createConnection(conf);
        tableContest = (HTable) conn.getTable(TableName.valueOf(tablenameContest));

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        ResultScanner scanner = tableContest.getScanner(scan);
        for (Result result : scanner) {
            if (cnt >= page * max && cnt < (page + 1) * max)
                ans.add(buildContest(result));
            ++cnt;
        }

        tableContest.close();
        conn.close();

        return ans;
    }

    public Contest getContest(String contestIdStr) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        tableContest = (HTable) conn.getTable(TableName.valueOf(tablenameContest));

        int contestId = Integer.valueOf(contestIdStr);

        Get get = new Get(Bytes.toBytes(String.format("%06d", contestId)));
        Result result = tableContest.get(get);
        Contest ans = buildContest(result);

        tableContest.close();
        conn.close();

        return ans;
    }

    private Contest buildContest(Result result) {
        Contest res = new Contest();

        res.setId(Integer.valueOf(Bytes.toString(result.getRow())));

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            Integer valueInt = Bytes.readAsInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            if (family.equals("info")) {

                if (qualifier.equals("name")) {
                    res.setName(valueStr);
                } else if (qualifier.equals("type")) {
                    res.setType(valueStr);
                } else if (qualifier.equals("phase")) {
                    res.setPhase(valueStr);
                } else if (qualifier.equals("frozen")) {
                    res.setFrozen(Boolean.valueOf(valueStr));
                }

            } else if (family.equals("time")) {

                if (qualifier.equals("startTimeSeconds")) {
                    res.setStartTimeSeconds(valueInt);
                } else if (qualifier.equals("durationSeconds")) {
                    res.setDurationSeconds(valueInt);
                } else if (qualifier.equals("relativeTimeSeconds")) {
                    res.setRelativeTimeSeconds(valueInt);
                }

            } else if (family.equals("other")) {

                if (qualifier.equals("preparedBy")) {
                    res.setPreparedBy(valueStr);
                } else if (qualifier.equals("websiteUrl")) {
                    res.setWebsiteUrl(valueStr);
                } else if (qualifier.equals("description")) {
                    res.setDescription(valueStr);
                } else if (qualifier.equals("difficulty")) {
                    res.setDifficulty(valueInt);
                } else if (qualifier.equals("kind")) {
                    res.setKind(valueStr);
                } else if (qualifier.equals("icpcRegion")) {
                    res.setIcpcRegion(valueStr);
                } else if (qualifier.equals("city")) {
                    res.setCity(valueStr);
                } else if (qualifier.equals("season")) {
                    res.setSeason(valueStr);
                }

            }

        }

        return res;
    }

}
