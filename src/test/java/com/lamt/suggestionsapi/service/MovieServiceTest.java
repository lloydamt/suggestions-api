package com.lamt.suggestionsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.mapper.MovieMapper;
import com.lamt.suggestionsapi.mapper.UserMapper;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import com.lamt.suggestionsapi.repository.MovieRepository;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.time.LocalDateTime;
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

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    UserService userService;

    @InjectMocks
    MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        final var movieMapper = Mappers.getMapper(MovieMapper.class);
        final var userMapper = Mappers.getMapper(UserMapper.class);
        movieService = new MovieServiceImpl(movieRepository, userMapper, movieMapper, userService);
    }

    @Test
    public void getMovieTest() {
        var id = UUID.randomUUID();

        var movie = buildMovie();

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        final var result = movieService.getMovie(id);

        assertEntityEqualsDto(movie, result);
    }

    private static void assertEntityEqualsDto(Movie movie, MovieDto result) {
        assertEquals(movie.getTitle(), result.getTitle());
        assertEquals(movie.getDescription(), result.getDescription());
        assertEquals(movie.getYear(), result.getYear());
        assertEquals(movie.getId(), result.getId());
        assertEquals(movie.getLikes(), result.getLikes());
        assertEquals(movie.getSaves(), result.getSaves());
        assertEquals(movie.getSaves(), result.getSaves());
    }

    @Test
    public void suggestMovieTest() {
        when(userService.getUser("username")).thenReturn(buildUser());

        var suggestedMovie =
                movieService.suggestMovie(MovieDto.builder().title("movie").build(), "username");

        assertEquals("movie", suggestedMovie.getTitle());
    }

    @Test
    public void editMovieTitleTest() {
        final var id = UUID.randomUUID();
        final var newMovie = MovieDto.builder().title("new movie").build();
        final var movie = buildMovie();

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        final var result = movieService.editMovie(id, "username", newMovie);

        assertEquals(newMovie.getTitle(), result.getTitle());
    }

    @Test
    public void editMovieUserTest() {
        final var id = UUID.randomUUID();
        final var newMovie = MovieDto.builder()
                .title("new movie")
                .user(BaseUserDto.builder().username("user").build())
                .build();

        when(movieRepository.findById(id)).thenReturn(Optional.of(buildMovie()));

        assertThrows(UserNotPermittedException.class, () -> movieService.editMovie(id, "username", newMovie));
    }

    @Test
    public void getMovieLikesTest() {
        var id = UUID.randomUUID();
        final var movie = buildMovie();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        final var likes = movieService.getMovieLikes(id);

        assertEquals(0, likes);
    }

    @Test
    public void getSavedMoviesTest() {
        final var movies = Stream.of(buildMovie().toBuilder().saves(1).build()).collect(Collectors.toSet());

        when(movieRepository.findAll()).thenReturn(movies);

        final var result = movieService.getSavedMovies();

        assertEquals(1, result.size());
        assertEquals("movie", result.stream().toList().get(0).getTitle());
    }

    @Test
    public void getLikedMoviesTest() {
        final var movies = Stream.of(
                        buildMovie(),
                        Movie.builder().title("liked movie").likes(1).build())
                .collect(Collectors.toSet());

        when(movieRepository.findAll()).thenReturn(movies);

        final var result = movieService.getLikedMovies();

        assertEquals(1, result.size());
    }

    @Test
    public void getMovieByUserTest() {
        final var movie = buildMovie();

        when(movieRepository.findByUserId(any())).thenReturn(Set.of(movie));

        final var result = movieService.getMoviesByUser(UUID.randomUUID());

        assertEquals(1, result.size());
    }

    @Test
    public void getAllMoviesTest() {
        final var movies = Stream.of(
                        buildMovie(),
                        Movie.builder().title("saved movie").saves(1).build())
                .collect(Collectors.toSet());

        when(movieRepository.findAll()).thenReturn(movies);

        final var result = movieService.getAllMovies();

        assertEquals(2, result.size());
    }

    @Test
    public void likeMovieTest() {
        final var id = UUID.randomUUID();
        final var movie = buildMovie();
        final var user = UserDto.builder().username("username").likes(Set.of()).build();

        when(userService.getUser(any(String.class))).thenReturn(user);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        final var result = movieService.likeMovie(id, "username");

        assertEquals(1, result.getLikedBy().size());
        assertEquals(1, result.getLikes());
        assertTrue(result.getLikedBy().contains(user));
    }

    // add new test for user already likes movie

    @Test
    public void saveMovieTest() {
        final var id = UUID.randomUUID();
        final var movie = buildMovie();
        final var user = UserDto.builder().username("username").saved(Set.of()).build();

        when(userService.getUser(any(String.class))).thenReturn(user);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        final var result = movieService.saveMovie(id, "username");

        assertEquals(1, result.getSavedBy().size());
        assertEquals(1, result.getSaves());
        assertTrue(result.getSavedBy().contains(user));
    }

    @Test
    public void getMovieCommentsTest() {
        final var comment = Comment.builder()
                .content("new comment")
                .movie(buildMovie())
                .user(User.builder().build())
                .timestamp(LocalDateTime.now())
                .build();
        final var movie = buildMovie().toBuilder().comments(List.of(comment)).build();

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        final var result = movieService.getMovieComments(UUID.randomUUID());

        assertEquals(1, result.size());
        assertEquals("new comment", result.get(0).getContent());
        assertEquals("movie", result.get(0).getMovie().getTitle());
    }

    @Test
    public void unlikeMovieTest() {
        final var id = UUID.randomUUID();
        final var user =
                User.builder().username("username").email("user@gmail.com").build();
        final var movie =
                buildMovie().toBuilder().likedBy(Set.of(user)).likes(1).build();

        when(userService.getUser("username")).thenReturn(buildUser());
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        movieService.unlikeMovie(id, "username");

        verify(userService).getUser("username");
        verify(movieRepository).save(any());
    }

    @Test
    public void unsaveMovieTest() {
        final var id = UUID.randomUUID();
        final var user =
                User.builder().username("username").email("user@gmail.com").build();
        final var movie =
                buildMovie().toBuilder().savedBy(Set.of(user)).saves(1).build();

        when(userService.getUser("username")).thenReturn(buildUser());
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        movieService.unsaveMovie(id, "username");

        verify(userService).getUser("username");
        verify(movieRepository).save(any());
    }

    @Test
    public void testDeleteMovie() {
        final var user = User.builder().username("username").build();
        Movie movie = buildMovie().toBuilder().user(user).build();

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        movieService.deleteMovie(UUID.randomUUID(), "username");

        verify(movieRepository).findById(any());
        verify(movieRepository).deleteById(any());
    }

    private Movie buildMovie() {
        return Movie.builder()
                .title("movie")
                .description("desc")
                .user(User.builder().username("username").build())
                .year(2022)
                .likes(0)
                .saves(0)
                .savedBy(Set.of())
                .likedBy(Set.of())
                .build();
    }

    private UserDto buildUser() {
        return UserDto.builder().email("user@gmail.com").username("username").build();
    }
}
