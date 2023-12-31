package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.repository.RequestRepository;
import ru.practicum.stats.StatClient;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ComponentScan("ru.practicum.stats")
public class StatService {

    private final StatClient statClient;
    private final RequestRepository requestRepository;

    @Transactional
    public void hit(String uri, String ip) {
        EndpointHitDto hit = buildHit(uri, ip);
        statClient.addHit(hit);
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getViews(List<Event> events) {
        Map<Long, Long> views = new HashMap<>();

        List<Event> publishedEvents = events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .collect(Collectors.toList());

        Optional<LocalDateTime> minPublished = publishedEvents.stream()
                .map(Event::getPublishedOn)
                .min(LocalDateTime::compareTo);

        if (minPublished.isPresent()) {
            LocalDateTime start = minPublished.get();
            LocalDateTime end = LocalDateTime.now();
            List<String> uris = publishedEvents.stream()
                    .map(e -> "/events/" + e.getId())
                    .collect(Collectors.toList());

            List<ViewStatsDto> stats = statClient.getStats(start, end, uris, true);
            stats.forEach(s -> {
                Long eventId = Long.parseLong(s.getUri().substring(s.getUri().lastIndexOf("/") + 1));
                views.put(eventId, s.getHits());
            });
        }

        return views;
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getConfirmedRequests(List<Event> events) {
        List<Long> publishedIds = events.stream()
                .filter(e -> e.getPublishedOn() != null)
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> confirmedRequests = new HashMap<>();

        requestRepository.getConfirmedRequests(publishedIds)
                .forEach(cr -> confirmedRequests.put(cr.getEventId(), cr.getConfirmed()));

        return confirmedRequests;
    }


    private EndpointHitDto buildHit(String uri, String ip) {
        return EndpointHitDto.builder()
                .app("ewm-main")
                .uri(uri)
                .timestamp(LocalDateTime.now())
                .ip(ip)
                .build();
    }
}
