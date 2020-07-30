package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "adsdb", name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id", nullable =  false)
    private Town town;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    public Location(Integer id, Region region, Town town, List<Announcement> announcements) {
        this.id = id;
        this.region = region;
        this.town = town;
        this.announcements = announcements;
    }

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

}
