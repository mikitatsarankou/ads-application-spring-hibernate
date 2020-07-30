package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser.UserRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User_;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl extends AbstractDAO<User, Integer> implements UserRepository {

    @Override
    public Class<User> entity() {
        return User.class;
    }

    @Override
    public User getByUsername(String username) throws UsernameNotFoundException {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(entity());
        Root<User> root = criteriaQuery.from(entity());
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.USERNAME), username));
        TypedQuery<User> typedQuery = eManager().createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("such username not found");
        }
    }

    @Override
    public List<User> getByRole(String role) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(entity());
        Root<User> root = criteriaQuery.from(entity());
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.ROLES), role));
        TypedQuery<User> typedQuery = eManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
