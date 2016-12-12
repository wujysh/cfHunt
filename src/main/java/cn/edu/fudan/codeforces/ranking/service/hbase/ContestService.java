package cn.edu.fudan.codeforces.ranking.service.hbase;

import cn.edu.fudan.codeforces.ranking.entity.Contest;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class ContestService extends BaseHBaseService {

    private static final Logger logger = LoggerFactory.getLogger(ContestService.class.getName());

    private static Table tableContest;

    static {
        try {
            String tablename = "codeforces:contest";
            tableContest = conn.getTable(TableName.valueOf(tablename));
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public List<Contest> listContests(Integer page, Integer max) throws IOException {
        --page;
        ArrayList<Contest> ans = new ArrayList<>();
        int cnt = 0;

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("type"));
        scan.addColumn(Bytes.toBytes("time"), Bytes.toBytes("durationSeconds"));
        scan.addColumn(Bytes.toBytes("time"), Bytes.toBytes("startTimeSeconds"));
        ResultScanner scanner = tableContest.getScanner(scan);
        for (Result result : scanner) {
            if (cnt >= page * max && cnt < (page + 1) * max)
                ans.add(buildContest(result));
            ++cnt;
        }

        return ans;
    }

    public Contest getContest(String contestIdStr) throws IOException {
        int contestId = Integer.valueOf(contestIdStr);

        Get get = new Get(Bytes.toBytes(String.format("%06d", contestId)));
        Result result = tableContest.get(get);

        return buildContest(result);
    }

    private Contest buildContest(Result result) throws UnsupportedEncodingException {
        Contest res = new Contest();

        res.setId(Integer.valueOf(Bytes.toString(result.getRow())));

        for (Cell cell : result.rawCells()) {
            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = new String(Bytes.copy(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()), "utf-8");
            Integer valueInt = Bytes.readAsInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            switch (family) {
                case "info":
                    switch (qualifier) {
                        case "name":
                            res.setName(valueStr);
                            break;
                        case "type":
                            res.setType(valueStr);
                            break;
                        case "phase":
                            res.setPhase(valueStr);
                            break;
                        case "frozen":
                            res.setFrozen(Boolean.valueOf(valueStr));
                            break;
                    }
                    break;
                case "time":
                    switch (qualifier) {
                        case "startTimeSeconds":
                            res.setStartTimeSeconds(valueInt);
                            break;
                        case "durationSeconds":
                            res.setDurationSeconds(valueInt);
                            break;
                        case "relativeTimeSeconds":
                            res.setRelativeTimeSeconds(valueInt);
                            break;
                    }
                    break;
                case "other":
                    switch (qualifier) {
                        case "preparedBy":
                            res.setPreparedBy(valueStr);
                            break;
                        case "websiteUrl":
                            res.setWebsiteUrl(valueStr);
                            break;
                        case "description":
                            res.setDescription(valueStr);
                            break;
                        case "difficulty":
                            res.setDifficulty(valueInt);
                            break;
                        case "kind":
                            res.setKind(valueStr);
                            break;
                        case "icpcRegion":
                            res.setIcpcRegion(valueStr);
                            break;
                        case "city":
                            res.setCity(valueStr);
                            break;
                        case "season":
                            res.setSeason(valueStr);
                            break;
                    }
                    break;
            }
        }
        return res;
    }

}
