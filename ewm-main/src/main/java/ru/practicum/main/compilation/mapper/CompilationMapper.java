package ru.practicum.main.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.main.compilation.dto.CompilationRequestDto;
import ru.practicum.main.compilation.dto.CompilationResponseDto;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public abstract class CompilationMapper {

    @Autowired
    protected EventService eventService;

    @Mapping(target = "events",
            expression = "java(eventService.mapToShortDtoWithViewsAndRequests(setToList(compilation.getEvents())))")
    public abstract CompilationResponseDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationResponseDto> toCompilationDto(List<Compilation> compilations);

    @Mapping(target = "events", source = "events")
    public abstract Compilation toCompilation(CompilationRequestDto compilationRequestDto, Set<Event> events);

    protected List<Event> setToList(Set<Event> eventSet) {
        return new ArrayList<>(eventSet);
    }
}
