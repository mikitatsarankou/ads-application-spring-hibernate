package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.RegionRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Region;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class RegionRepositoryImpl extends AbstractDAO<Region, Integer> implements RegionRepository {

    @Override
    public Class<Region> entity() {
        return Region.class;
    }
}
