package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EntityNotFoundException;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Transactional
    public void delete(Long userId) {
        checkIfUserExists(userId);
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return (ids == null || ids.isEmpty()) ? userMapper.toUserDto(userRepository.findAll(pageable)) :
                userMapper.toUserDto(userRepository.findAllByIdIn(ids, pageable));
    }

    private void checkIfUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(User.class, userId);
        }
    }
}
