package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.AnnouncementRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.AdStatus;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnnouncementRepositoryImpl extends AbstractDAO<Announcement, Integer> implements AnnouncementRepository {

    @Override
    public Class<Announcement> entity() {
        return Announcement.class;
    }

    @Override
    public Integer getLastCreatedIdByProfileId(int profileId) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Announcement> root = query.from(entity());
        query.select(root.get(Announcement_.ID))
                .where(criteriaBuilder.equal(root.get(Announcement_.PROFILE), profileId))
                .orderBy(criteriaBuilder.desc(root.get(Announcement_.ID)));
        TypedQuery<Integer> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(1);
        return typedQuery.getSingleResult();
    }

    @Override
    public Profile getProfileFromAnnouncement(int id) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Profile> query = criteriaBuilder.createQuery(Profile.class);
        Root<Announcement> root = query.from(entity());
        query.select(root.get(Announcement_.PROFILE))
                .where(criteriaBuilder.equal(root.get(Announcement_.ID), id));
        TypedQuery<Profile> typedQuery = eManager().createQuery(query);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Announcement> getAnnouncementsByProfileId(int id, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(entity());
        Root<Announcement> root = query.from(entity());
        query.select(root)
                .where(criteriaBuilder.equal(root.get(Announcement_.PROFILE), id))
                .orderBy(criteriaBuilder.desc(root.get(Announcement_.CREATE_DATE)));
        TypedQuery<Announcement> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(limit);
        typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }

    @Override
    public List<Announcement> getAnnouncementsWithStatusSoldByProfileId(int id, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(entity());
        Root<Announcement> root = query.from(entity());
        Predicate categoryPredicate = criteriaBuilder.equal(root.get(Announcement_.PROFILE), id);
        Predicate locationPredicate = criteriaBuilder.equal(root.get(Announcement_.STATUS), AdStatus.SOLD);
        Predicate finalPredicate = criteriaBuilder.and(categoryPredicate, locationPredicate);
        query.select(root)
                .where(finalPredicate);
        TypedQuery<Announcement> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(limit);
        typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }

    @Override
    public List<Announcement>  getAllAnnouncements(int offset, int limit, int categoryId, int locationId, String sortBy, String sortType) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(entity());
        Root<Announcement> root = query.from(entity());

        if (categoryId != 0 && locationId != 0) {
            Predicate categoryPredicate = criteriaBuilder.equal(root.get(Announcement_.CATEGORY), categoryId);
            Predicate locationPredicate = criteriaBuilder.equal(root.get(Announcement_.LOCATION), locationId);
            Predicate finalPredicate = criteriaBuilder.and(categoryPredicate, locationPredicate);
            query.select(root).where(finalPredicate);
        } else {
            if (categoryId != 0) {
                query.select(root)
                        .where(criteriaBuilder.equal(root.get(Announcement_.CATEGORY), categoryId));
            }
            if (locationId != 0) {
                query.select(root)
                        .where(criteriaBuilder.equal(root.get(Announcement_.LOCATION), locationId));
            }
        }

        List<Order> orderList = new ArrayList<>();
        Order statusOrder = criteriaBuilder.asc(root.get(Announcement_.STATUS));
        orderList.add(statusOrder);
        Order priorityOrder = criteriaBuilder.desc(root.get(Announcement_.PRIORITY));
        orderList.add(priorityOrder);


        if (!sortBy.equals("null")) {
            Order sortByOrder;
            if (sortType.equals("desc")) {
                sortByOrder = criteriaBuilder.desc(root.get(sortBy));
                orderList.add(sortByOrder);
            } else if (sortType.equals("asc")){
                sortByOrder = criteriaBuilder.asc(root.get(sortBy));
                orderList.add(sortByOrder);
            }
        }

        Order highPriorityPayDateOrder = criteriaBuilder.desc(root.get(Announcement_.HIGH_PRIORITY_PAY_DATE));
        orderList.add(highPriorityPayDateOrder);
        Order createDateOrder = criteriaBuilder.desc(root.get(Announcement_.CREATE_DATE));
        orderList.add(createDateOrder);

        query.select(root)
                .orderBy(orderList);

        TypedQuery<Announcement> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(limit);
        typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }

    @Override
    public List<Announcement> getAllWherePriorityNotDefault() {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(entity());
        Root<Announcement> root = query.from(entity());
        query.select(root)
                .where(criteriaBuilder.greaterThan(root.get(Announcement_.PRIORITY),
                        Announcement.DEFAULT_ANNOUNCEMENT_PRIORITY));
        TypedQuery<Announcement> typedQuery = eManager().createQuery(query);
        return typedQuery.getResultList();
    }
}
