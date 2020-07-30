package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {

    T getById(PK id) throws ObjectNotFoundException;

    List<T> getAll();

    void create(T t);

    void update(T t, PK id) throws ObjectNotFoundException;

    void lazyUpdate(T t);

    void delete(PK id) throws ObjectNotFoundException;

    void lazyDelete(T t);

}
