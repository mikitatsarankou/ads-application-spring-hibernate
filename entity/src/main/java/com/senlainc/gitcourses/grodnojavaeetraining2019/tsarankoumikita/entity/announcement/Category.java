package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "adsdb", name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Announcement> announcement;

    public Category(Integer id, String name, List<Announcement> announcement) {
        this.id = id;
        this.name = name;
        this.announcement = announcement;
    }

    public Category() {
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

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }
}
