package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location;

import java.util.List;

public class RegionDto {

    private Integer id;

    private String name;

    private List<TownDto> towns;

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

    public List<TownDto> getTowns() {
        return towns;
    }

    public void setTowns(List<TownDto> towns) {
        this.towns = towns;
    }
}
