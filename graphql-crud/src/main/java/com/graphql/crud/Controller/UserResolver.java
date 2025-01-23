package com.graphql.crud.Controller;

import com.graphql.crud.Model.User;
import com.graphql.crud.Repository.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class UserResolver {
    private final UserRepository userRepository;

    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public User getUser(@Argument Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @MutationMapping
    public User createUser(@Argument String name, @Argument String email) {
        // Check if a user with the same email already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("A user with this email already exists: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }


    @MutationMapping
    public User deleteUser(@Argument Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return user;
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String name, @Argument String email) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        if (name != null) {
            user.setName(name);
        }
        if (email != null) {
            user.setEmail(email);
        }
        return userRepository.save(user);
    }

}