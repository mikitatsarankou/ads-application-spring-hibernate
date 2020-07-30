package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "adsdb", name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    public Comment(Integer id, Profile profile, Announcement announcement, LocalDateTime createDate, String commentText) {
        this.id = id;
        this.profile = profile;
        this.announcement = announcement;
        this.createDate = createDate;
        this.commentText = commentText;
    }

    public Comment() {
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

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
