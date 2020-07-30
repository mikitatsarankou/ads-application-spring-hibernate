package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile;

public class RatingDto {

    private Integer id;

    private ProfileDto profile;

    private int sales;

    private int overallRating;

    public RatingDto(ProfileDto profile, int sales, int overallRating) {
        this.profile = profile;
        this.sales = sales;
        this.overallRating = overallRating;
    }

    public RatingDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }
}
