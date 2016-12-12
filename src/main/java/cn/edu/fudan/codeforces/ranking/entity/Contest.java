package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by house on 12/4/16.
 * <p>
 * Represents a contest on Codeforces.
 * <p>
 * Field	                Description
 * id	                    Integer.
 * name	                    String. Localized.
 * type	                    Enum: CF, IOI, ICPC. Scoring system used for the contest.
 * phase	                Enum: BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED.
 * frozen	                Boolean. If true, then the ranklist for the contest is frozen and shows only submissions, created before freeze.
 * durationSeconds	        Integer. Duration of the contest in seconds.
 * startTimeSeconds	        Integer. Can be absent. Contest start time in unix format.
 * relativeTimeSeconds	    Integer. Can be absent. Number of seconds, passed after the start of the contest. Can be negative.
 * preparedBy	            String. Can be absent. Handle of the user, how created the contest.
 * websiteUrl	            String. Can be absent. URL for contest-related website.
 * description	            String. Localized. Can be absent.
 * difficulty	            Integer. Can be absent. From 1 to 5. Larger number means more difficult problems.
 * kind	                    String. Localized. Can be absent. Human-readable type of the contest from the following categories: Official ACM-ICPC Contest, Official School Contest, Opencup Contest, School/University/City/Region Championship, Training Camp Contest, Official International Personal Contest, Training Contest.
 * icpcRegion	            String. Localized. Can be absent. Name of the ICPC Region for official ACM-ICPC contests.
 * country	                String. Localized. Can be absent.
 * city	                    String. Localized. Can be absent.
 * season	                String. Can be absent.
 * <p>
 * Structure in HBase
 * <p>
 * Table            codeforces:contest
 * <p>
 * Row key          {id}(padding)
 * <p>
 * Column Family 1  info
 * Columns          name, type, phase, frozen
 * <p>
 * Column Family 2  time
 * Columns          durationSeconds, startTimeSeconds, relativeTimeSeconds
 * <p>
 * Column Family 3  other
 * Columns          preparedBy, websiteUrl, description,
 * difficulty, kind, icpcRegion, city, season
 */
public class Contest {

    private Integer id;
    private String name;
    private Type type;
    private Phase phase;
    private Boolean frozen;
    private Integer durationSeconds;
    private Integer startTimeSeconds;
    private Integer relativeTimeSeconds;
    private String preparedBy;
    private String websiteUrl;
    private String description;
    private Integer difficulty;
    private String kind;
    private String icpcRegion;
    private String country;
    private String city;
    private String season;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setType(String type) {
        switch (type) {
            case "CF":
                this.type = Type.CF;
                break;
            case "IOI":
                this.type = Type.IOI;
                break;
            case "ICPC":
                this.type = Type.ICPC;
                break;
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void setPhase(String phase) {
        switch (phase) {
            case "BEFORE":
                this.phase = Phase.BEFORE;
                break;
            case "CODING":
                this.phase = Phase.CODING;
                break;
            case "PENDING_SYSTEM_TEST":
                this.phase = Phase.PENDING_SYSTEM_TEST;
                break;
            case "SYSTEM_TEST":
                this.phase = Phase.SYSTEM_TEST;
                break;
            case "FINISHED":
                this.phase = Phase.FINISHED;
                break;
        }
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(Integer startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

    public Integer getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(Integer relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIcpcRegion() {
        return icpcRegion;
    }

    public void setIcpcRegion(String icpcRegion) {
        this.icpcRegion = icpcRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------------------------------------------------\n");
        sb.append("Contest:\n");

        if (id != null) {
            sb.append("id: ").append(id).append("\n");
        }
        if (name != null) {
            sb.append("name: ").append(name).append("\n");
        }
        sb.append("type: ").append(type).append("\n");
        sb.append("phase: ").append(phase).append("\n");
        if (frozen != null) {
            sb.append("frozen: ").append(frozen).append("\n");
        }
        if (durationSeconds != null) {
            sb.append("durationSeconds: ").append(durationSeconds).append("\n");
        }
        if (startTimeSeconds != null) {
            sb.append("startTimeSeconds: ").append(startTimeSeconds).append("\n");
        }
        if (relativeTimeSeconds != null) {
            sb.append("relativeTimeSeconds: ").append(relativeTimeSeconds).append("\n");
        }
        if (preparedBy != null) {
            sb.append("preparedBy: ").append(preparedBy).append("\n");
        }
        if (websiteUrl != null) {
            sb.append("websiteUrl: ").append(websiteUrl).append("\n");
        }
        if (description != null) {
            sb.append("description: ").append(description).append("\n");
        }
        if (difficulty != null) {
            sb.append("difficulty: ").append(difficulty).append("\n");
        }
        if (kind != null) {
            sb.append("kind: ").append(kind).append("\n");
        }
        if (icpcRegion != null) {
            sb.append("icpcRegion: ").append(icpcRegion).append("\n");
        }
        if (country != null) {
            sb.append("country: ").append(country).append("\n");
        }
        if (city != null) {
            sb.append("city: ").append(city).append("\n");
        }
        if (season != null) {
            sb.append("season: ").append(season).append("\n");
        }

        sb.append("--------------------------------------------------");

        return sb.toString();
    }

    public enum Type {
        CF, IOI, ICPC
    }

    public enum Phase {
        BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED
    }

}