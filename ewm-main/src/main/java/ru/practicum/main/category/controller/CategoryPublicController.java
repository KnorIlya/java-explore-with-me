package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        return categoryService.getById(catId);
    }

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return categoryService.getAll(from, size);
    }

}
