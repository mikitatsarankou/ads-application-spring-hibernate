package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Comment;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "adsdb", name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Profile(Integer id, String name, String surname, String email, String phone, LocalDateTime createDate, User user, List<Announcement> announcements, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.createDate = createDate;
        this.user = user;
        this.announcements = announcements;
        this.comments = comments;
    }

    public Profile() {
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
