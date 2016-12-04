package cn.edu.fudan.codeforces.ranking.service;

import cn.edu.fudan.codeforces.ranking.entity.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by house on 12/4/16.
 */
@Service
public class ProblemService {

    private static Configuration conf;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "10.141.208.43,10.141.208.44,10.141.208.45");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.master.port", "60000");
        conf.setLong("hbase.rpc.timeout", Long.parseLong("60000"));
        conf = HBaseConfiguration.create(conf);
    }

    private static String tablenameProblem = "codeforces:problem";
    private static HTable tableProblem;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public List<Problem> listProblems(Integer page, Integer max) {
        return new ArrayList<>();
    }

    public Problem getProblem(String contestIdStr, String problemIdx) {
        Connection conn = ConnectionFactory.createConnection(conf);
        tableProblem = (HTable) conn.getTable(TableName.valueOf(tablenameProblem));

        int contestId = Integer.valueOf(contestIdStr);

        String rowkey = String.format("%06d-%s", contestId, problemIdx);

        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = tableProblem.get(get);
        Problem ans = buildProblem(result);

        tableProblem.close();
        conn.close();

        return ans;
    }

    private Problem buildProblem(Result result) {
        Problem res = new Problem();

        String[] token = Bytes.toString(result.getRow()).split("-");

        res.setContestId(Integer.valueOf(token[0]));
        res.setIndex(token[1]);

        for (Cell cell : result.rawCells()) {

            String family = Bytes.toStringBinary(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = Bytes.toStringBinary(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String valueStr = Bytes.toStringBinary(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            if (family.equals("info")) {

                if (qualifier.equals("name")) {
                    res.setName(valueStr);
                } else if (qualifier.equals("type")) {
                    res.setType(valueStr);
                } else if (qualifier.equals("points")) {
                    res.setPoints(Bytes.toFloat(cell.getValueArray(), cell.getValueOffset()));
                } else if (qualifier.equals("tags")) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(valueStr);
                    res.setTags(tmp);
                }

            }

        }

        return res;
    }

}
