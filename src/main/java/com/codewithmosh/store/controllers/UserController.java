package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.user.ChangePasswordRequest;
import com.codewithmosh.store.dtos.user.RegisterUserRequest;
import com.codewithmosh.store.dtos.user.UpdateUserRequset;
import com.codewithmosh.store.dtos.user.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Iterable<UserDto> gerAllUsers(
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort
    ) {
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable Long id
    ) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(foundUser.get()));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("email", "Email is already registered"));
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequset request
    ) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, foundUser);
        userRepository.save(foundUser);

        return ResponseEntity.ok(userMapper.toDto(foundUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(foundUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }
        if (!foundUser.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        foundUser.setPassword(request.getNewPassword());
        userRepository.save(foundUser);

        return ResponseEntity.noContent().build();
    }

}
