package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RegionNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.RegionDto;

import java.util.List;

public interface RegionService {

    RegionDto getById(int id) throws RegionNotFoundException;

    List<RegionDto> getAll();

    void create(RegionDto regionDto);

    void update(RegionDto regionDto) throws RegionNotFoundException;

    void delete(int id) throws RegionNotFoundException;
}
