package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityAlreadyExistsException;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.repository.UserRepository;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(String username) {
        var user = userRepository
                .findByUsername(username.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        return user;
    }

    @Override
    public User getUser(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        var user = userRepository
                .findByEmail(email.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        return user;
    }

    @Override
    public User createUser(User user) {
        var userExists = userRepository.findByUsername(user.getUsername()).isPresent()
                || userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new EntityAlreadyExistsException(User.class);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }

    @Override
    public void deleteuser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public Set<Movie> getUserSuggestions(String username) {
        User user = getUser(username);
        return user.getMovies();
    }

    @Override
    public Set<Movie> getUserLikes(String username) {
        User user = getUser(username);
        return user.getLikes();
    }

    @Override
    public Set<Movie> getUserSaved(String username) {
        User user = getUser(username);
        return user.getSaved();
    }

    @Override
    public List<Comment> getUserComments(String username) {
        User user = getUser(username);
        return user.getComments();
    }
}
