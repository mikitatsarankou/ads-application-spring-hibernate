package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location;

public class TownDto {

    private Integer id;

    private String name;

    private RegionDto region;

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

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }
}
