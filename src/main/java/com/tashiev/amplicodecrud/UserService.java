package com.tashiev.amplicodecrud;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tashiev.amplicodecrud.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public UserService(UserMapper userMapper,
                       UserRepository userRepository,
                       ObjectMapper objectMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public Page<UserDtoV1> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDtoV1);
    }

    public UserDtoV1 getOne(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userMapper.toUserDtoV1(userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<UserDtoV1> getMany(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .map(userMapper::toUserDtoV1)
                .toList();
    }

    public UserDtoV1 create(UserDtoV1 dto) {
        User user = userMapper.toEntity(dto);
        User resultUser = userRepository.save(user);
        return userMapper.toUserDtoV1(resultUser);
    }

    public UserDtoV1 patch(Long id, JsonNode patchNode) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        UserDtoV1 userDtoV1 = userMapper.toUserDtoV1(user);
        objectMapper.readerForUpdating(userDtoV1).readValue(patchNode);
        userMapper.updateWithNull(userDtoV1, user);

        User resultUser = userRepository.save(user);
        return userMapper.toUserDtoV1(resultUser);
    }

    public List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException {
        Collection<User> users = userRepository.findAllById(ids);

        for (User user : users) {
            UserDtoV1 userDtoV1 = userMapper.toUserDtoV1(user);
            objectMapper.readerForUpdating(userDtoV1).readValue(patchNode);
            userMapper.updateWithNull(userDtoV1, user);
        }

        List<User> resultUsers = userRepository.saveAll(users);
        return resultUsers.stream()
                .map(User::getId)
                .toList();
    }

    public UserDtoV1 delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toUserDtoV1(user);
    }

    public void deleteMany(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }
}
