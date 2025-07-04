package com.example.springbootjwtauthapi.user.repository;

import com.example.springbootjwtauthapi.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public User save(User user) {
        if (user.getId() == null) {
            user = User.builder()
                    .id(sequence.incrementAndGet())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .password(user.getPassword())
                    .userRoles(user.getUserRoles())
                    .build();
        }
        store.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByUsername(String username) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return store.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
