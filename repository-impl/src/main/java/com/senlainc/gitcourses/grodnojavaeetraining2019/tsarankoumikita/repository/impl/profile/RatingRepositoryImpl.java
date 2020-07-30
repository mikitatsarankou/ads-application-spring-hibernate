package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RatingNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.profile.RatingRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating_;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RatingRepositoryImpl extends AbstractDAO<Rating, Integer> implements RatingRepository {

    @Override
    public Class<Rating> entity() {
        return Rating.class;
    }

    @Override
    public Rating getByProfileId(int profileId) throws RatingNotFoundException {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Rating> criteriaQuery = criteriaBuilder.createQuery(entity());
        Root<Rating> root = criteriaQuery.from(entity());
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(Rating_.PROFILE), profileId));
        TypedQuery<Rating> typedQuery = eManager().createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new RatingNotFoundException("such rating ID not found");
        }
    }
}
