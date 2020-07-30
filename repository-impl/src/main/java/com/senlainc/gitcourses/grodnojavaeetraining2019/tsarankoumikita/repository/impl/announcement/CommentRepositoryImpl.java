package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.CommentRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Comment;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Comment_;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.AbstractDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractDAO<Comment, Integer> implements CommentRepository {

    @Override
    public Class<Comment> entity() {
        return Comment.class;
    }

    public List<Comment> getAllComments(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(entity());
        Root<Comment> root = query.from(entity());
        query.select(root)
                .orderBy(criteriaBuilder.desc(root.get(Comment_.CREATE_DATE)));
        TypedQuery<Comment> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(limit);
        typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }

    public List<Comment> getCommentsByProfileId(int profileId, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(entity());
        Root<Comment> root = query.from(entity());
        query.select(root)
                .where(criteriaBuilder.equal(root.get(Comment_.PROFILE), profileId))
                .orderBy(criteriaBuilder.desc(root.get(Comment_.CREATE_DATE)));
        TypedQuery<Comment> typedQuery = eManager().createQuery(query);
        typedQuery.setMaxResults(limit);
        typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }

    @Override
    public List<Comment> getCommentsByAnnouncementId(int announcementId) {
        CriteriaBuilder criteriaBuilder = eManager().getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(entity());
        Root<Comment> root = query.from(entity());
        query.select(root)
                .where(criteriaBuilder.equal(root.get(Comment_.ANNOUNCEMENT), announcementId))
                .orderBy(criteriaBuilder.desc(root.get(Comment_.CREATE_DATE)));
        TypedQuery<Comment> typedQuery = eManager().createQuery(query);
        return typedQuery.getResultList();
    }
}
