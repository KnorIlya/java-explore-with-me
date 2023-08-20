package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final StatsRepository repository;
    private final EndpointHitMapper mapper;


    @Transactional
    public void addHit(EndpointHitDto endpointHitDto) {

        repository.save(mapper.toEndpointHit(endpointHitDto));
    }


    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        if (start.isAfter(end)) {
            throw new IllegalStateException("Invalid time interval");
        }

        if (uris == null || uris.isEmpty()) {
            return unique ? repository.getStatsUniqueIp(start, end) : repository.getStats(start, end);
        } else {
            return unique ? repository.getStatsUniqueIpForUris(start, end, uris) : repository.getStatsForUris(start, end, uris);
        }
    }


}
