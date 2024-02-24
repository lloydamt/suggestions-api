package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityAlreadyExistsException;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.mapper.UserMapper;
import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
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
    private final UserMapper userMapper;

    @Override
    public UserDto getUser(String username) {
        return userMapper.mapEntityToDto(userRepository
                .findByUsername(username.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(User.class)));
    }

    @Override
    public UserDto getUser(UUID id) {
        return userMapper.mapEntityToDto(
                userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class)));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.mapEntityToDto(userRepository
                .findByEmail(email.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(User.class)));
    }

    @Override
    public UserDto createUser(User user) {
        var userExists = userRepository.findByUsername(user.getUsername()).isPresent()
                || userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new EntityAlreadyExistsException(User.class);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        return userMapper.mapEntityToDto(userRepository.save(user));
    }

    @Override
    public void deleteuser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public Set<BaseMovieDto> getUserSuggestions(String username) {
        final var user = getUser(username);
        return user.getMovies();
    }

    @Override
    public Set<BaseMovieDto> getUserLikes(String username) {
        final var user = getUser(username);
        return user.getLikes();
    }

    @Override
    public Set<BaseMovieDto> getUserSaved(String username) {
        final var user = getUser(username);
        return user.getSaved();
    }

    @Override
    public List<CommentDto> getUserComments(String username) {
        final var user = getUser(username);
        return user.getComments();
    }
}
