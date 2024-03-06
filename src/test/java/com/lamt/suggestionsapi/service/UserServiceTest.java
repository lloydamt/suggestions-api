package com.lamt.suggestionsapi.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityAlreadyExistsException;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.mapper.UserMapper;
import com.lamt.suggestionsapi.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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

    @BeforeEach
    void setUp() {
        final var mapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder, mapper);
    }

    @Test
    public void getUserTest() {
        final User user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        final var actual = userService.getUser("username");

        assertEquals(actual.getUsername(), user.getUsername());
        assertEquals(actual.getEmail(), user.getEmail());
    }

    @Test
    public void getUserByIdTest() {
        final var user = buildUser();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        final var actual = userService.getUser(UUID.randomUUID());

        assertEquals(actual.getUsername(), user.getUsername());
        assertEquals(actual.getEmail(), user.getEmail());
    }

    @Test
    public void getNonExistentUserTest() {
        final var exception = assertThrows(EntityNotFoundException.class, () -> userService.getUser("username"));
        verify(userRepository).findByUsername("username");
        assertEquals(exception.getMessage(), "The user was not found");
    }

    @Test
    public void createUserTest() {
        User user = buildUser();
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        final var actual = userService.createUser(user);

        assertEquals(actual.getEmail(), user.getEmail());
        assertEquals(actual.getUsername(), user.getUsername());
    }

    @Test
    public void createDuplicateUserTest() {
        var user = buildUser();
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        final var exception = assertThrows(EntityAlreadyExistsException.class, () -> userService.createUser(user));
        assertEquals(exception.getMessage(), "The user already exists");
    }

    @Test
    public void getUserSuggestionsTest() {
        final var user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        final var result = userService.getUserSuggestions("username");

        assertEquals(1, result.size());
        assertEquals("title1", result.stream().findFirst().get().getTitle());
    }

    @Test
    public void getUserLikesTest() {
        final var user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        final var result = userService.getUserLikes(user.getUsername());

        assertEquals(1, result.size());
        assertEquals("title1", result.stream().findFirst().get().getTitle());
    }

    @Test
    public void getUserSavedTest() {
        final var user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        final var result = userService.getUserSaved("username");

        assertEquals(1, result.size());
        assertEquals("title1", result.stream().findFirst().get().getTitle());
    }

    @Test
    public void getUserCommentsTest() {
        final var user = buildUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        final var result = userService.getUserComments("username");

        assertEquals("Content", result.get(0).getContent());
    }

    @Test
    public void deleteUserTest() {
        final var username = "user";

        userService.deleteuser(username);

        verify(userRepository).deleteByUsername(username);
    }

    private User buildUser() {
        return User.builder()
                .email("user@gmail.com")
                .username("username")
                .likes(buildMovies())
                .saved(buildMovies())
                .movies(buildMovies())
                .comments(buildComments())
                .build();
    }

    private Set<Movie> buildMovies() {
        final var movie = Movie.builder()
                .title("title1")
                .description("desc")
                .year(2022)
                .likes(0)
                .saves(0)
                .build();
        return Stream.of(movie).collect(Collectors.toSet());
    }

    private List<Comment> buildComments() {
        return List.of(Comment.builder()
                .content("Content")
                .user(User.builder().username("user").build())
                .build());
    }
}
