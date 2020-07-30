package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.TownNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.TownDto;

import java.util.List;

public interface TownService {

    TownDto getById(int id) throws TownNotFoundException;

    List<TownDto> getAll();

    void create(TownDto townDto);

    void update(TownDto townDto) throws TownNotFoundException;

    void delete(int id) throws TownNotFoundException;
}
