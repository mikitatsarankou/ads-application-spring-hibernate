package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "adsdb", name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Town> towns;

    @OneToOne(mappedBy = "region", fetch = FetchType.LAZY)
    private Location location;

    public Region(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Region() {
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

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
