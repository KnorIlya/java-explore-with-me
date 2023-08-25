package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationRequestDto;
import ru.practicum.main.compilation.dto.CompilationResponseDto;
import ru.practicum.main.compilation.service.CompilationService;
import ru.practicum.main.flag.OnCreate;
import ru.practicum.main.flag.OnUpdate;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationResponseDto> create(@RequestBody @Validated(OnCreate.class) CompilationRequestDto compilationRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.create(compilationRequestDto));
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationResponseDto> update(@RequestBody @Validated(OnUpdate.class) CompilationRequestDto compilationRequestDto,
                                         @PathVariable Long compId) {
        return ResponseEntity.ok(compilationService.update(compilationRequestDto, compId));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> delete(@PathVariable Long compId) {
        compilationService.delete(compId);
        return ResponseEntity.noContent().build();
    }
}
