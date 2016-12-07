package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by wujy on 16-1-16.
 */

/**
 * Represents a submissions results of a user for a problem.
 * <p>
 * Field	                    Description
 * points	                    Floating point number.
 * penalty	                    Integer. Penalty (in ICPC meaning) of the party for this problem.
 * rejectedAttemptCount         Integer. Number of incorrect submissions.
 * bestSubmissionTimeSeconds    Integer. Number of seconds after the start of the contest before the submission, that brought maximal amount of points for this problem.
 */
public class ProblemResult {

    private Float points;
    private Integer penalty;
    private Integer rejectedAttemptCount;
    private Integer bestSubmissionTimeSeconds;

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

    public Integer getBestSubmissionTimeSeconds() {
        return bestSubmissionTimeSeconds;
    }

    public void setBestSubmissionTimeSeconds(Integer bestSubmissionTimeSeconds) {
        this.bestSubmissionTimeSeconds = bestSubmissionTimeSeconds;
    }

}
