package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<T, PK extends Serializable> implements GenericDAO<T, PK> {

    private final Logger logger = LogManager.getLogger();

    public abstract Class<T> entity();

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager eManager() {
        return entityManager;
    }

    @Override
    public T getById(PK id) throws ObjectNotFoundException {
        logger.info("getting " + entity().getSimpleName().toLowerCase() + "  by id");
        T entity = entityManager.find(entity(), id);
        if (entity != null) {
            return entity;
        } else {
            throw new ObjectNotFoundException("there's no such " + entity().getSimpleName().toLowerCase() + " with id " + id);
        }
    }

    @Override
    public List<T> getAll() {
        logger.info("getting list of " + entity().getSimpleName().toLowerCase());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entity());
        Root<T> root = query.from(entity());
        query.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public void create(T entity) {
        logger.info("creating " + entity().getSimpleName().toLowerCase());
        entityManager.merge(entity);
        logger.info(entity().getSimpleName().toLowerCase() + " was created");
    }

    @Override
    public void update(T entity, PK id) throws ObjectNotFoundException {
        getById(id);
        logger.info("updating " + entity().getSimpleName().toLowerCase());
        entityManager.merge(entity);
        logger.info( entity().getSimpleName().toLowerCase() + " was updated");
    }

    @Override
    public void lazyUpdate(T entity) {
        logger.info("updating " + entity().getSimpleName().toLowerCase());
        entityManager.merge(entity);
        logger.info( entity().getSimpleName().toLowerCase() + " was updated");
    }

    @Override
    public void delete(PK id) throws ObjectNotFoundException {
        logger.info("deleting " + entity().getSimpleName().toLowerCase());
        T entity = getById(id);
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        logger.info( entity().getSimpleName().toLowerCase() + " was deleted");
    }

    @Override
    public void lazyDelete(T entity) {
        logger.info("deleting " + entity().getSimpleName().toLowerCase());
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        logger.info( entity().getSimpleName().toLowerCase() + " was deleted");
    }
}
