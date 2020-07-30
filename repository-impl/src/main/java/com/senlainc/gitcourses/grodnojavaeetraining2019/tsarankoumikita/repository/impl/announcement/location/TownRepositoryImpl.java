package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.TownRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Town;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TownRepositoryImpl extends AbstractDAO<Town, Integer> implements TownRepository {

    @Override
    public Class<Town> entity() {
        return Town.class;
    }
}
