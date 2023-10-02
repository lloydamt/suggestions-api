package com.lamt.suggestionsapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityAlreadyExistsException;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void getUserTest() {
        User user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals(userService.getUser("username"), user);
        assertEquals(userService.getUser("username").getUsername(), user.getUsername());
    }

    @Test
    public void getNonExistentUserTest() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUser("username");
        });
        verify(userRepository).findByUsername("username");
        assertEquals(exception.getMessage(), "The user was not found");
    }

    @Test
    public void createUserTest() {
        User user = buildUser();
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(userService.createUser(user), user);
    }

    @Test
    public void createDuplicateUserTest() {
        var user = buildUser();
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        RuntimeException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });
        assertEquals(exception.getMessage(), "The user already exists");
    }

    @Test
    public void getUserSuggestionsTest() {
        User user = buildUser();
        var movies = buildMovies();
        user.setMovies(movies);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals(userService.getUserSuggestions("username"), movies);
    }

    @Test
    public void getUserLikesTest() {
        User user = buildUser();
        var movies = buildMovies();
        user.setLikes(movies);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals(userService.getUserLikes(user.getUsername()), movies);
    }

    @Test
    public void getUserSavedTest() {
        User user = buildUser();
        var movies = buildMovies();
        user.setSaved(movies);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals(userService.getUserSaved("username"), movies);
    }

    @Test
    public void getUserCommentsTest() {
        User user = buildUser();
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        comments.add(new Comment());
        user.setComments(comments);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals(userService.getUserComments("username"), comments);
    }

    private User buildUser() {
        return User.builder()
                .email("user@gmail.com")
                .username("username")
                .password("password")
                .likes(new HashSet<Movie>())
                .saved(new HashSet<Movie>())
                .build();
    }

    private Set<Movie> buildMovies() {
        Movie movie1 = Movie.builder()
                .title("title1")
                .description("desc")
                .year(2022)
                .likes(0)
                .saves(0)
                .build();
        Movie movie2 = Movie.builder()
                .title("title2")
                .description("desc2")
                .year(2023)
                .likes(0)
                .saves(0)
                .build();
        var moviesSet = Stream.of(movie1, movie2).collect(Collectors.toSet());
        Set<Movie> movies = new HashSet<>(moviesSet);
        return movies;
    }
}
