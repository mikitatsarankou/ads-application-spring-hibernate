package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.CategoryNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.CategoryRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.CategoryService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Category;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto getById(int id) throws CategoryNotFoundException{
        try {
            Category category = categoryRepository.getById(id);
            return modelMapper.map(category, CategoryDto.class);
        } catch (ObjectNotFoundException e) {
            throw new CategoryNotFoundException("such category ID not found");
        }
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.getAll().stream()
                .map(x -> modelMapper.map(x, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(CategoryDto categoryDto) {
        Category categoryToRepo = modelMapper.map(categoryDto, Category.class);
        categoryRepository.create(categoryToRepo);
    }

    @Override
    public void update(CategoryDto categoryDto) throws CategoryNotFoundException {
        Category categoryToRepo = modelMapper.map(categoryDto, Category.class);
        try {
            categoryRepository.update(categoryToRepo, categoryToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new CategoryNotFoundException("such category not found");
        }

    }

    @Override
    public void delete(int id) throws CategoryNotFoundException {
        try {
            categoryRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new CategoryNotFoundException("such category not found");
        }
    }
}
