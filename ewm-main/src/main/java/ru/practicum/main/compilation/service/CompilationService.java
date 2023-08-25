package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationRequestDto;
import ru.practicum.main.compilation.dto.CompilationResponseDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;


    @Transactional
    public CompilationResponseDto create(CompilationRequestDto compilationRequestDto) {
        if (compilationRequestDto.getPinned() == null) {
            compilationRequestDto.setPinned(false);
        }
        Set<Event> events = listToSet(eventRepository.findEventsByIdIn(compilationRequestDto.getEvents()));
        checkFoundEvents(events.size(), compilationRequestDto.getEvents().size());
        Compilation compilation = compilationRepository.save(
                compilationMapper.toCompilation(compilationRequestDto, events
                ));

        return compilationMapper.toCompilationDto(compilation);
    }


    @Transactional
    public CompilationResponseDto update(CompilationRequestDto compilationRequestDto, Long compId) {
        Compilation compilation = getCompilation(compId);
        setCompilation(compilationRequestDto, compilation);
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }


    @Transactional
    public void delete(Long compId) {
        checkExisted(compId);
        compilationRepository.deleteById(compId);
    }

    @Transactional(readOnly = true)
    public CompilationResponseDto getById(Long compId) {
        return compilationMapper.toCompilationDto(getCompilation(compId));
    }


    @Transactional(readOnly = true)
    public List<CompilationResponseDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Compilation> compilations = (pinned != null)
                ? compilationRepository.findAllByPinnedIs(pinned, pageable)
                : compilationRepository.findAll(pageable).toList();
        return compilationMapper.toCompilationDto(compilations);
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException(Compilation.class, compId));
    }

    private void checkExisted(Long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new EntityNotFoundException(Compilation.class, compId);
        }
    }

    private void checkFoundEvents(Integer found, Integer provided) {
        if (!found.equals(provided)) {
            throw new EntityNotFoundException("Not all compilations found");
        }
    }

    private Set<Event> listToSet(List<Event> events) {
        return new HashSet<>(events);
    }

    private void setCompilation(CompilationRequestDto dto, Compilation compilation) {
        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        if (dto.getEvents() != null) {
            Set<Event> events = listToSet(eventRepository.findEventsByIdIn(dto.getEvents()));
            checkFoundEvents(events.size(), dto.getEvents().size());
            compilation.setEvents(events);
        }
    }
}
