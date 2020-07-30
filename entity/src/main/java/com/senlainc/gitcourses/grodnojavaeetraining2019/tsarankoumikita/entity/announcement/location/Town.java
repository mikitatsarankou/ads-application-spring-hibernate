package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location;

import javax.persistence.*;

@Entity
@Table(schema = "adsdb", name = "towns")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToOne(mappedBy = "town", fetch = FetchType.LAZY)
    private Location location;

    public Town(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Town() {
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
