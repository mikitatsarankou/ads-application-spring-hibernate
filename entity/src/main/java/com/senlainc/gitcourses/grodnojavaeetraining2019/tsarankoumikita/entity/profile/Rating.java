package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile;

import javax.persistence.*;

@Entity
@Table(schema = "adsdb", name = "profiles_ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "number_of_sales", nullable = false)
    private int sales;

    @Column(name = "overall_rating", nullable = false)
    private int overallRating;

    public static final int ANNOUNCEMENT_POINTS = 3;

    public static final int COMMENT_POINTS = 2;

    public static final int SALE_POINTS = 5;

    public Rating(Integer id, Profile profile, int sales, int overallRating) {
        this.id = id;
        this.profile = profile;
        this.sales = sales;
        this.overallRating = overallRating;
    }

    public Rating() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
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
