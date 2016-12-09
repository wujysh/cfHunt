package cn.edu.fudan.codeforces.ranking.entity;

/**
 * Created by house on 12/4/16.
 * <p>
 * Represents a Codeforces user.
 * <p>
 * Field	                Description
 * handle	                String. Codeforces user handle.
 * email	                String. Shown only if user allowed to share his contact info.
 * vkId	                    String. User id for VK social network. Shown only if user allowed to share his contact info.
 * openId	                String. Shown only if user allowed to share his contact info.
 * firstName	            String. Localized. Can be absent.
 * lastName	                String. Localized. Can be absent.
 * country	                String. Localized. Can be absent.
 * city	                    String. Localized. Can be absent.
 * organization	            String. Localized. Can be absent.
 * contribution	            Integer. User contribution.
 * rank	                    String. Localized.
 * rating	                Integer.
 * maxRank	                String. Localized.
 * maxRating	            Integer.
 * lastOnlineTimeSeconds    Integer. Time, when user was last seen online, in unix format.
 * registrationTimeSeconds  Integer. Time, when user was registered, in unix format.
 * <p>
 * Structure in HBase
 * <p>
 * Table            codeforces:user
 * <p>
 * Row key          {handle}
 * Column Family 1  info
 * Columns          email, vkId, openId, firstName, lastName, country, city, organization,
 * contribution, lastOnlineTimeSeconds, registrationTimeSeconds, rank, maxRank
 * <p>
 * Column Family 2  rating
 * Columns          rating, maxRating
 */
public class User {

    private String handle;
    private String email;
    private String vkId;
    private String openId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String organization;
    private Integer contribution;
    private String rank;
    private Integer rating;
    private String maxRank;
    private Integer maxRating;
    private Integer lastOnlineTimeSeconds;
    private Integer registrationTimeSeconds;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public Integer getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public void setLastOnlineTimeSeconds(Integer lastOnlineTimeSeconds) {
        this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
    }

    public Integer getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public void setRegistrationTimeSeconds(Integer registrationTimeSeconds) {
        this.registrationTimeSeconds = registrationTimeSeconds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------------------------------------------------\n");
        sb.append("User:\n");

        if (handle != null) {
            sb.append("handle: ").append(handle).append("\n");
        }
        if (email != null) {
            sb.append("email: ").append(email).append("\n");
        }
        if (vkId != null) {
            sb.append("vkId: ").append(vkId).append("\n");
        }
        if (openId != null) {
            sb.append("openId: ").append(openId).append("\n");
        }
        if (firstName != null) {
            sb.append("firstName: ").append(firstName).append("\n");
        }
        if (lastName != null) {
            sb.append("lastName: ").append(lastName).append("\n");
        }
        if (country != null) {
            sb.append("country: ").append(country).append("\n");
        }
        if (city != null) {
            sb.append("city: ").append(city).append("\n");
        }
        if (organization != null) {
            sb.append("organization: ").append(organization).append("\n");
        }
        if (contribution != null) {
            sb.append("contribution: ").append(contribution).append("\n");
        }
        if (rank != null) {
            sb.append("rank: ").append(rank).append("\n");
        }
        if (rating != null) {
            sb.append("rating: ").append(rating).append("\n");
        }
        if (maxRank != null) {
            sb.append("maxRank: ").append(maxRank).append("\n");
        }
        if (maxRating != null) {
            sb.append("maxRating: ").append(maxRating).append("\n");
        }
        if (lastOnlineTimeSeconds != null) {
            sb.append("lastOnlineTimeSeconds: ").append(lastOnlineTimeSeconds).append("\n");
        }
        if (registrationTimeSeconds != null) {
            sb.append("registrationTimeSeconds: ").append(registrationTimeSeconds).append("\n");
        }

        sb.append("--------------------------------------------------");

        return sb.toString();
    }

}