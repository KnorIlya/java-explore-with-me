package ru.practicum.main.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, Long entityId) {
        super(entityClass.getSimpleName() + " with id=" + entityId + " was not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
