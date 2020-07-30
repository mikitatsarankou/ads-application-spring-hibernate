package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser.RoleRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.RoleName;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role_;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleRepositoryImpl extends AbstractDAO<Role, Integer> implements RoleRepository {

    @Override
    public Class<Role> entity() {
        return Role.class;
    }

    @Override
    public Role getByName(RoleName name) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(entity());
        Root<Role> root = criteriaQuery.from(entity());
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(Role_.NAME), name));
        TypedQuery<Role> typedQuery = eManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }
}
