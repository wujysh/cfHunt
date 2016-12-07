package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by house on 12/4/16.
 */

/**
 * Represents a submission.
 * <p>
 * Field	                Description
 * id	                    Integer.
 * contestId	            Integer.
 * creationTimeSeconds	    Integer. Time, when submission was created, in unix-format.
 * relativeTimeSeconds	    Integer. Number of seconds, passed after the start of the contest (or a virtual start for virtual parties), before the submission.
 * problem	                Problem object.
 * author	                Party object.
 * programmingLanguage	    String.
 * verdict	                Enum: FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED. Can be absent.
 * testset	                Enum: SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, ..., TESTS10. Testset used for judging the submission.
 * passedTestCount	        Integer. Number of passed tests.
 * timeConsumedMillis       Integer. Maximum time in milliseconds, consumed by solution for one test.
 * memoryConsumedBytes	    Integer. Maximum memory in bytes, consumed by solution for one test.
 */
public class Submission {

    private Integer id;
    private Integer contestId;
    private Integer creationTimeSeconds;
    private Integer relativeTimeSeconds;
    private Problem problem;
    private Party author;
    private String programmingLanguage;
    private Verdict verdict;
    private Testset testset;
    private Integer passedTestCount;
    private Integer timeConsumedMillis;
    private Integer memoryConsumedBytes;

    public Submission() {
        problem = new Problem();
        author = new Party();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    public void setCreationTimeSeconds(Integer creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public Integer getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(Integer relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Party getAuthor() {
        return author;
    }

    public void setAuthor(Party author) {
        this.author = author;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public boolean isVerdictOK() {
        return verdict == Verdict.OK;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public void setVerdict(String verdict) {
        if (verdict.equals("FAILED")) {
            this.verdict = Verdict.FAILED;
        } else if (verdict.equals("OK")) {
            this.verdict = Verdict.OK;
        } else if (verdict.equals("PARTIAL")) {
            this.verdict = Verdict.PARTIAL;
        } else if (verdict.equals("COMPILATION_ERROR")) {
            this.verdict = Verdict.COMPILATION_ERROR;
        } else if (verdict.equals("RUNTIME_ERROR")) {
            this.verdict = Verdict.RUNTIME_ERROR;
        } else if (verdict.equals("WRONG_ANSWER")) {
            this.verdict = Verdict.WRONG_ANSWER;
        } else if (verdict.equals("PRESENTATION_ERROR")) {
            this.verdict = Verdict.PRESENTATION_ERROR;
        } else if (verdict.equals("TIME_LIMIT_EXCEEDED")) {
            this.verdict = Verdict.TIME_LIMIT_EXCEEDED;
        } else if (verdict.equals("MEMORY_LIMIT_EXCEEDED")) {
            this.verdict = Verdict.MEMORY_LIMIT_EXCEEDED;
        } else if (verdict.equals("IDLENESS_LIMIT_EXCEEDED")) {
            this.verdict = Verdict.IDLENESS_LIMIT_EXCEEDED;
        } else if (verdict.equals("SECURITY_VIOLATED")) {
            this.verdict = Verdict.SECURITY_VIOLATED;
        } else if (verdict.equals("CRASHED")) {
            this.verdict = Verdict.CRASHED;
        } else if (verdict.equals("INPUT_PREPARATION_CRASHED")) {
            this.verdict = Verdict.INPUT_PREPARATION_CRASHED;
        } else if (verdict.equals("CHALLENGED")) {
            this.verdict = Verdict.CHALLENGED;
        } else if (verdict.equals("SKIPPED")) {
            this.verdict = Verdict.SKIPPED;
        } else if (verdict.equals("TESTING")) {
            this.verdict = Verdict.TESTING;
        } else if (verdict.equals("REJECTED")) {
            this.verdict = Verdict.REJECTED;
        }
    }

    public Testset getTestset() {
        return testset;
    }

    public void setTestset(Testset testset) {
        this.testset = testset;
    }

    public void setTestset(String testset) {
        if (testset.equals("SAMPLES")) {
            this.testset = Testset.SAMPLES;
        } else if (testset.equals("PRETESTS")) {
            this.testset = Testset.PRETESTS;
        } else if (testset.equals("TESTS")) {
            this.testset = Testset.TESTS;
        } else if (testset.equals("CHALLENGES")) {
            this.testset = Testset.CHALLENGES;
        } else if (testset.equals("TESTS1")) {
            this.testset = Testset.TESTS1;
        } else if (testset.equals("TESTS2")) {
            this.testset = Testset.TESTS2;
        } else if (testset.equals("TESTS3")) {
            this.testset = Testset.TESTS3;
        } else if (testset.equals("TESTS4")) {
            this.testset = Testset.TESTS4;
        } else if (testset.equals("TESTS5")) {
            this.testset = Testset.TESTS5;
        } else if (testset.equals("TESTS6")) {
            this.testset = Testset.TESTS6;
        } else if (testset.equals("TESTS7")) {
            this.testset = Testset.TESTS7;
        } else if (testset.equals("TESTS8")) {
            this.testset = Testset.TESTS8;
        } else if (testset.equals("TESTS9")) {
            this.testset = Testset.TESTS9;
        } else if (testset.equals("TESTS10")) {
            this.testset = Testset.TESTS10;
        }
    }

    public Integer getPassedTestCount() {
        return passedTestCount;
    }

    public void setPassedTestCount(Integer passedTestCount) {
        this.passedTestCount = passedTestCount;
    }

    public Integer getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    public void setTimeConsumedMillis(Integer timeConsumedMillis) {
        this.timeConsumedMillis = timeConsumedMillis;
    }

    public Integer getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }

    public void setMemoryConsumedBytes(Integer memoryConsumedBytes) {
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------------------------------------------------\n");
        sb.append("Submission:\n");

        if (id != null) {
            sb.append("id: " + id + "\n");
        }
        if (contestId != null) {
            sb.append("contestId: " + contestId + "\n");
        }
        if (creationTimeSeconds != null) {
            sb.append("creationTimeSeconds: " + creationTimeSeconds + "\n");
        }
        if (relativeTimeSeconds != null) {
            sb.append("relativeTimeSeconds: " + relativeTimeSeconds + "\n");
        }
        if (problem.getIndex() != null) {
            sb.append("problemIndex: " + problem.getIndex() + "\n");
        }
        // party
        if (author.getTeamId() != null) {
            sb.append("teamId: " + author.getTeamId() + "\n");
        }
        if (author.getTeamName() != null) {
            sb.append("teamName: " + author.getTeamName() + "\n");
        }
        if (author.getRoom() != null) {
            sb.append("room: " + author.getRoom() + "\n");
        }
        if (author.getStartTimeSeconds() != null) {
            sb.append("startTimeSeconds: " + author.getStartTimeSeconds() + "\n");
        }
        if (author.getMembers() != null && author.getMembers().size() > 0) {
            sb.append("members:");
            for (String mem : author.getMembers()) {
                sb.append(" " + mem);
            }
            sb.append("\n");
        }
        if (programmingLanguage != null) {
            sb.append("programmingLanguage: " + programmingLanguage + "\n");
        }
        sb.append("verdict: " + verdict + "\n");
        sb.append("testset: " + testset + "\n");
        if (passedTestCount != null) {
            sb.append("passedTestCount: " + passedTestCount + "\n");
        }
        if (timeConsumedMillis != null) {
            sb.append("timeConsumedMillis: " + timeConsumedMillis + "\n");
        }
        if (memoryConsumedBytes != null) {
            sb.append("memoryConsumedBytes: " + memoryConsumedBytes + "\n");
        }

        sb.append("--------------------------------------------------");

        return sb.toString();
    }

    private enum Verdict {
        FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED
    }

    private enum Testset {
        SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, TESTS2, TESTS3, TESTS4, TESTS5, TESTS6, TESTS7, TESTS8, TESTS9, TESTS10
    }

}