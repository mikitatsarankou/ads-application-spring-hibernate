package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location;

public class LocationDto {

    private Integer id;

    private CompressedRegionDto region;

    private CompressedTownDto town;

    public LocationDto(int id) {
        this.id = id;
    }

    public LocationDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompressedRegionDto getRegion() {
        return region;
    }

    public void setRegion(CompressedRegionDto region) {
        this.region = region;
    }

    public CompressedTownDto getTown() {
        return town;
    }

    public void setTown(CompressedTownDto town) {
        this.town = town;
    }
}
