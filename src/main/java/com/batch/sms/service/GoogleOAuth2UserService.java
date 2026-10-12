package com.batch.sms.service;

import com.batch.sms.entity.User;
import com.batch.sms.repository.UserRepository;
import com.batch.sms.security.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User findOrCreateUser(String email) {

        // Return the existing user if this email is already registered.
        User existingUser = userRepository.findByUsername(email).orElse(null);

        if (existingUser != null) {
            return existingUser;
        }

        // Create a random password that cannot be used as a known
        // Google login password.
        String randomPassword = UUID.randomUUID().toString();

        User newUser = new User();
        newUser.setUsername(email);
        newUser.setPassword(passwordEncoder.encode(randomPassword));
        newUser.setRole(Role.USER);

        // Save the new Google user in the database.
        return userRepository.save(newUser);

    }
}
