package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    List<Event> findEventsByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findEventByIdAndInitiatorId(Long eventId, Long userId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' AND " +
            "e.id = ?1")
    Optional<Event> getEventIfPublished(Long eventId);

    List<Event> findEventsByIdIn(List<Long> ids);

    List<Event> findEventsByCategoryId(Long catId);
}
