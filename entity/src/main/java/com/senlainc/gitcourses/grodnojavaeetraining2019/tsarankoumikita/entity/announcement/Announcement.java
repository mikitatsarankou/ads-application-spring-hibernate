package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Location;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.AdStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "adsdb", name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "announcement_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "announcement_text", nullable = false)
    private String announcementText;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "announcement",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Column(nullable = false)
    private int priority;

    @Column(name = "priority_pay_date", nullable = true)
    private LocalDateTime highPriorityPayDate;

    @Column(name = "paid_priority_expire_date", nullable = true)
    private LocalDateTime paidPriorityExpireDate;

    public static final int DEFAULT_ANNOUNCEMENT_PRIORITY = 5;

    public static final int ABOVE_AVERAGE_ANNOUNCEMENT_PRIORITY = 7;

    public static final int HIGH_ANNOUNCEMENT_PRIORITY = 9;

    public Announcement(Integer id, AdStatus status, LocalDateTime createDate, Category category, Location location, String announcementText, int price, Profile profile, List<Comment> comments, int priority, LocalDateTime highPriorityPayDate, LocalDateTime paidPriorityExpireDate) {
        this.id = id;
        this.status = status;
        this.createDate = createDate;
        this.category = category;
        this.location = location;
        this.announcementText = announcementText;
        this.price = price;
        this.profile = profile;
        this.comments = comments;
        this.priority = priority;
        this.highPriorityPayDate = highPriorityPayDate;
        this.paidPriorityExpireDate = paidPriorityExpireDate;
    }

    public Announcement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdStatus getStatus() {
        return status;
    }

    public void setStatus(AdStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getHighPriorityPayDate() {
        return highPriorityPayDate;
    }

    public void setHighPriorityPayDate(LocalDateTime highPriorityPayDate) {
        this.highPriorityPayDate = highPriorityPayDate;
    }

    public LocalDateTime getPaidPriorityExpireDate() {
        return paidPriorityExpireDate;
    }

    public void setPaidPriorityExpireDate(LocalDateTime paidPriorityExpireDate) {
        this.paidPriorityExpireDate = paidPriorityExpireDate;
    }
}
