package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.profile.ProfileRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile_;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ProfileRepositoryImpl extends AbstractDAO<Profile, Integer> implements ProfileRepository {

    @Override
    public Class<Profile> entity() {
        return Profile.class;
    }

    @Override
    public Profile getProfileByUserId(int userId) throws UserNotFoundException {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Profile> criteriaQuery = criteriaBuilder.createQuery(entity());
        Root<Profile> root = criteriaQuery.from(entity());
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(Profile_.USER), userId));
        TypedQuery<Profile> typedQuery = eManager().createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException("such user does not exists");
        }
    }
}
