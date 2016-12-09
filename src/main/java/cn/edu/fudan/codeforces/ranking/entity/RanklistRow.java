package cn.edu.fudan.codeforces.ranking.entity;

import java.util.List;

/**
 * Created by house on 12/7/16.
 * <p>
 * Represents a ranklist row.
 * <p>
 * Field	                    Description
 * handle	                    User handle.
 * points	                    For CF, total amount of points. For ICPC, AC number.
 * penalty	                    For CF, penalty for all problems. For ICPC, total cost time for all problem.
 * problemResults	            List of ProblemResult objects. Party results for each problem. Order of the problems is the same as in "problems" field of the returned object.
 */
public class RanklistRow {

    private String handle;
    private Float points;
    private Integer penalty;
    private List<ProblemResult> problemResults;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

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

    public List<ProblemResult> getProblemResults() {
        return problemResults;
    }

    public void setProblemResults(List<ProblemResult> problemResults) {
        this.problemResults = problemResults;
    }

}
