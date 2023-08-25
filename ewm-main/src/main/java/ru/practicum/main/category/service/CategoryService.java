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
        checkIfCategoryExists(catId);
        Category updatedCategory = categoryMapper.toCategory(newCategoryDto);
        updatedCategory.setId(catId);
        return categoryMapper.toCategoryDto(categoryRepository.save(updatedCategory));
    }


    @Transactional
    public void delete(Long catId) {
        checkIfCategoryExistsAndGet(catId);
        checkIfEmpty(catId);
        categoryRepository.deleteById(catId);
    }


    @Transactional(readOnly = true)
    public CategoryDto getById(Long catId) {
        return categoryMapper.toCategoryDto(checkIfCategoryExistsAndGet(catId));
    }


    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryMapper.toCategoryDto(categoryRepository.findAll(pageable).toList());
    }

    private void checkIfEmpty(Long catId) {
        if (!eventRepository.findEventsByCategoryId(catId).isEmpty()) {
            throw new ForbiddenException("The category is not empty");
        }
    }

    private Category checkIfCategoryExistsAndGet(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, catId));
    }

    private void checkIfCategoryExists(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new EntityNotFoundException(Category.class, catId);
        }
    }
}
