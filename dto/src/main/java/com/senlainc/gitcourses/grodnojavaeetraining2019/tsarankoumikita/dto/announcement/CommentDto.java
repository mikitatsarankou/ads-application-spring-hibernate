package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;

import java.time.LocalDateTime;

public class CommentDto {

    private Integer id;

    private ProfileDto profile;

    private CompressedAnnouncementDto announcement;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String commentText;

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

    public CompressedAnnouncementDto getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(CompressedAnnouncementDto announcement) {
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
