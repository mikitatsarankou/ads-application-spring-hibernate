package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.CategoryNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.CategoryService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(){
        return categoryService.getAll();
    }

    @GetMapping(value = "/{id}")
    public CategoryDto getById(@PathVariable int id) throws CategoryNotFoundException {
        return categoryService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody CategoryDto categoryDto) throws CategoryNotFoundException {
        categoryService.update(categoryDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryDto categoryDto) {
        categoryService.create(categoryDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws CategoryNotFoundException {
        categoryService.delete(id);
    }
}
