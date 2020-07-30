package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.LocationDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.AdStatusDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDto {

    private Integer id;

    private AdStatusDto status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private CategoryDto category;

    private LocationDto location;

    private String announcementText;

    private int price;

    private ProfileDto profile;

    private List<CompressedCommentDto> comments;

    private int priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime highPriorityPayDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paidPriorityExpireDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdStatusDto getStatus() {
        return status;
    }

    public void setStatus(AdStatusDto status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
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

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public List<CompressedCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CompressedCommentDto> comments) {
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
