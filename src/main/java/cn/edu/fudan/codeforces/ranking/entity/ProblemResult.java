package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by house on 12/7/16.
 */

/**
 * Represents a submissions results of a user for a problem.
 * <p>
 * Field	                    Description
 * points	                    For CF, the user gets these points by AC. For ICPC, point is 1 or 0.
 * penalty	                    For CF, penalty for this problem. For ICPC, total cost time for this problem.
 * rejectedAttemptCount         Integer. Number of incorrect submissions.
 * acTimeSeconds                Integer. Number of seconds after the start of the contest before the AC submission.
 */
public class ProblemResult {

    private Float points;
    private Integer penalty;
    private Integer rejectedAttemptCount;
    private Integer acTimeSeconds;

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getRejectedAttemptCount() {
        return rejectedAttemptCount;
    }

    public void setRejectedAttemptCount(Integer rejectedAttemptCount) {
        this.rejectedAttemptCount = rejectedAttemptCount;
    }

    public Integer getAcTimeSeconds() {
        return acTimeSeconds;
    }

    public void setAcTimeSeconds(Integer acTimeSeconds) {
        this.acTimeSeconds = acTimeSeconds;
    }

}
