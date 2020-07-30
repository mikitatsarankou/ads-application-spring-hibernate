package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.CategoryNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getById(int id) throws CategoryNotFoundException;

    List<CategoryDto> getAll();

    void create(CategoryDto categoryDto);

    void update(CategoryDto categoryDto) throws CategoryNotFoundException;

    void delete(int id) throws CategoryNotFoundException;
}
