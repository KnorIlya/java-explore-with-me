package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.EntityNotFoundException;
import ru.practicum.main.exception.ForbiddenException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;


    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(categoryMapper.toCategory(newCategoryDto));
        return categoryMapper.toCategoryDto(category);
    }


    @Transactional
    public CategoryDto update(NewCategoryDto newCategoryDto, Long catId) {
        checkCategoryExisted(catId);
        Category updated = categoryMapper.toCategory(newCategoryDto);
        updated.setId(catId);
        return categoryMapper.toCategoryDto(categoryRepository.save(updated));
    }


    @Transactional
    public void delete(Long catId) {
        checkCategoryExisted(catId);
        if (!isEmpty(catId)) {
            throw new ForbiddenException("The category is not empty");
        }
        categoryRepository.deleteById(catId);
    }


    @Transactional(readOnly = true)
    public CategoryDto getById(Long catId) {
        return categoryMapper.toCategoryDto(getCategory(catId));
    }


    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryMapper.toCategoryDto(categoryRepository.findAll(pageable).toList());
    }

    private boolean isEmpty(Long catId) {
        return eventRepository.findEventsByCategoryId(catId).isEmpty();
    }

    private Category getCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, catId));
    }

    private void checkCategoryExisted(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new EntityNotFoundException(Category.class, catId);
        }
    }
}
