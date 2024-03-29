package cn.edu.fudan.codeforces.ranking.entity;

import cn.edu.fudan.codeforces.ranking.util.StringUtil;

/**
 * Created by wujy on 16-1-16.
 * <p>
 * Represents a statistic data about a problem.
 * <p>
 * Field	        Description
 * contestId	    Integer. Id of the contest, containing the problem.
 * index	        String. Usually a letter of a letter, followed by a digit, that represent a problem index in a contest.
 * solvedCount	    Integer. Number of users, who solved the problem.
 */
public class ProblemStatistics {

    private Integer contestId;
    private String index;
    private Integer solvedCount;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getContestIdWithPaddingZero() {
        return getContestIdWithPaddingZero(6);
    }

    public String getContestIdWithPaddingZero(int numOfPadding) {
        return StringUtil.numWithPadding(contestId, numOfPadding);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(Integer solvedCount) {
        this.solvedCount = solvedCount;
    }

}
