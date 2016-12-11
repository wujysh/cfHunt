package cn.edu.fudan.codeforces.ranking.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wujy on 16-11-17.
 */
public class StringUtil {

    public static String numWithPadding(int num, int numOfPadding, String padding) {
        String strNum = String.valueOf(num);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numOfPadding - strNum.length(); i++) {
            stringBuilder.append(padding);
        }
        stringBuilder.append(strNum);
        return stringBuilder.toString();
    }

    public static String numWithPadding(int num, int numOfPadding) {
        return numWithPadding(num, numOfPadding, "0");
    }

    public static Map<Long, Integer> sortMapByKey(Map<Long, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            System.out.println("null");
            return null;

        }
        Map<Long, Integer> sortedMap = new TreeMap<Long, Integer>(new Comparator<Long>() {
            public int compare(Long key1, Long key2) {
                System.out.println(key1 - key2+"");
                return (int) (key1 - key2);
            }});
        for ( int i=0;i<sortedMap.keySet().size();i++)
        {
            System.out.println(sortedMap.keySet().toString());
        }
        return sortedMap;
    }

}
