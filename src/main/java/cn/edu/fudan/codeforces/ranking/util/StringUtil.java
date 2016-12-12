package cn.edu.fudan.codeforces.ranking.util;

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

    public static String handleCountryName(String country) {
        if (country != null) {
            switch (country) {
                case "United States (USA)":
                    return "United States of America";
                case "Korea, Republic of":
                    return "South Korea";
                case "Korea,DPR":
                    return "North Korea";
                case "The Netherlands":
                    return "Netherlands";
            }
        }
        return country;
    }

}
