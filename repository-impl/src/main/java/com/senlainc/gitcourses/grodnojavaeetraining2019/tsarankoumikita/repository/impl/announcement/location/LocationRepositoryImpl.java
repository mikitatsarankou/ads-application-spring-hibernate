package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.LocationRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Location;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryImpl extends AbstractDAO<Location, Integer> implements LocationRepository {

    @Override
    public Class<Location> entity() {
        return Location.class;
    }
}
