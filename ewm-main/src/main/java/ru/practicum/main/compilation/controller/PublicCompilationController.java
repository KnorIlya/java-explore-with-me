package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationResponseDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequestMapping("/compilations")
@RestController
@RequiredArgsConstructor
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationResponseDto getById(@PathVariable Long compId) {
        return compilationService.getById(compId);
    }

    @GetMapping
    public List<CompilationResponseDto> getAll(@RequestParam(required = false) Boolean pinned,
                                               @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        return compilationService.getAll(pinned, from, size);
    }
}
