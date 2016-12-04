package cn.edu.fudan.codeforces.ranking.entity;

import java.util.List;

/**
 * Created by house on 12/4/16.
 */

/**
 * Represents a problem.
 * <p>
 * Field	        Description
 * contestId	    Integer. Id of the contest, containing the problem.
 * index	        String. Usually a letter of a letter, followed by a digit, that represent a problem index in a contest.
 * name	            String. Localized.
 * type	            Enum: PROGRAMMING, QUESTION.
 * points	        Floating point number. Can be absent. Maximum ammount of points for the problem.
 * tags	            String list. Problem tags.
 */
public class Problem {

    private Integer contestId;
    private String index;
    private String name;
    private Type type;
    private Float points;
    private List<String> tags;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        if (type.equals("PROGRAMMING")) {
            this.type = Type.PROGRAMMING;
        } else if (type.equals("QUESTION")) {
            this.type = Type.QUESTION;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------------------------------------------------\n");
        sb.append("Problem:\n");

        if (contestId != null) {
            sb.append("contestId: " + contestId + "\n");
        }
        if (index != null) {
            sb.append("index: " + index + "\n");
        }
        if (name != null) {
            sb.append("name: " + name + "\n");
        }
        sb.append("type: " + type + "\n");
        if (points != null) {
            sb.append("points: " + points + "\n");
        }
        if (tags != null) {
            sb.append("tags:");
            for (String tag : tags) {
                sb.append(" ");
                sb.append(tag);
            }
            sb.append("\n");
        }

        sb.append("--------------------------------------------------");

        return sb.toString();
    }

    private enum Type {
        PROGRAMMING, QUESTION
    }

}