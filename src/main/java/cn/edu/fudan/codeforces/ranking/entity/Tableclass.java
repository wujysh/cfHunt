package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by zqj on 2016/12/12.
 */
public class Tableclass {
    public String submission;
    public String problem;
    public String party;

    public Tableclass(String i) {
        if(i.equals("0"))
        {
            this.submission = "submission";
            this.problem = "problem";
            this.party = "party";
        }else {
            this.submission = "submission" + i;
            this.problem = "problem" + i;
            this.party = "party" + i;
        }
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
