package ru.practicum.main.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    Event event;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    User requester;

    @Column(name = "status", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    RequestState status;

    @Column(name = "created", nullable = false)
    LocalDateTime created;
}
