package ru.practicum.main.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;
}
