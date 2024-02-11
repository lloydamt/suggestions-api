package com.lamt.suggestionsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.repository.MovieRepository;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    public void getMovieTest() {
        var id = UUID.randomUUID();
        var movie = buildMovie();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        assertEquals(movie, movieService.getMovie(id));
    }

    @Test
    public void suggestMovieTest() {
        User user = buildUser();
        Movie movie = buildMovie();

        when(userService.getUser("username")).thenReturn(user);
        when(movieRepository.save(movie)).thenReturn(movie);

        var suggestedMovie = movieService.suggestMovie(movie, user.getUsername());

        assertEquals(movie, suggestedMovie);
    }

    @Test
    public void editMovieTitleTest() {
        var id = UUID.randomUUID();
        User user1 = buildUser();
        var movie = buildMovie().toBuilder().id(id).user(user1).build();
        var movie2 = movie.toBuilder().title("newMovie").build();
        user1.setMovies(Stream.of(movie).collect(Collectors.toSet()));

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        movie = movieService.editMovie(id, user1.getUsername(), movie2);

        assertEquals("newMovie", movie.getTitle());
        assertEquals(movie.getDescription(), movie2.getDescription());
        assertEquals(movie.getYear(), movie2.getYear());
    }

    @Test
    public void editMovieUserTest() {
        var id = UUID.randomUUID();
        User user = buildUser();
        User user2 = User.builder()
                .email("email")
                .username("newUser")
                .password("newPassword")
                .build();
        Movie movie = buildMovie().toBuilder().id(id).user(user).build();
        Movie newMovie = movie.toBuilder().user(user2).build();

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        var editMovie = movieService.editMovie(id, user.getUsername(), newMovie);
        assertEquals(user, editMovie.getUser());
    }

    @Test
    public void getMovieLikesTest() {
        var id = UUID.randomUUID();
        Movie movie = buildMovie().toBuilder().id(id).build();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Integer likes = movieService.getMovieLikes(id);

        assertEquals(Integer.valueOf(0), likes);
    }

    @Test
    public void getSavedMoviesTest() {
        User user1 = buildUser();
        Set<User> savedBy = Stream.of(user1).collect(Collectors.toSet());
        Movie movie1 = buildMovie().toBuilder().savedBy(savedBy).build();
        Movie movie2 = buildMovie().toBuilder().title("Movie 2").build();

        Set<Movie> saved = Stream.of(movie1).collect(Collectors.toSet());
        Set<Movie> fullList = new HashSet<>(Stream.of(movie1, movie2).collect(Collectors.toSet()));

        when(movieRepository.findAll()).thenReturn(fullList);

        assertEquals(saved, movieService.getSavedMovies());
    }

    @Test
    public void getLikedMoviesTest() {
        User user1 = buildUser();
        Set<User> likedBy = new HashSet<>(Stream.of(user1).collect(Collectors.toSet()));
        Movie movie1 = buildMovie().toBuilder().likedBy(likedBy).build();
        Movie movie2 = buildMovie().toBuilder().title("Movie 2").build();

        Set<Movie> liked = new HashSet<>(Stream.of(movie1).collect(Collectors.toSet()));
        Set<Movie> fullList = new HashSet<>(Stream.of(movie1, movie2).collect(Collectors.toSet()));

        when(movieRepository.findAll()).thenReturn(fullList);

        assertEquals(liked, movieService.getLikedMovies());
    }

    @Test
    public void getMovieByUserTest() {
        User user1 = buildUser();
        Movie movie1 = buildMovie().toBuilder().build();
        Movie movie2 = buildMovie().toBuilder().title("Movie 2").build();
        Set<Movie> fullList = new HashSet<>(Stream.of(movie1, movie2).collect(Collectors.toSet()));
        user1.setMovies(fullList);
        when(movieRepository.findByUserId(any())).thenReturn(fullList);

        assertEquals(fullList, movieService.getMoviesByUser(UUID.randomUUID()));
    }

    @Test
    public void getAllMoviesTest() {
        Movie movie1 = buildMovie().toBuilder().build();
        Movie movie2 = buildMovie().toBuilder().title("Movie 2").build();
        Set<Movie> fullList = new HashSet<>(Stream.of(movie1, movie2).collect(Collectors.toSet()));

        when(movieRepository.findAll()).thenReturn(fullList);

        var movies = movieService.getAllMovies();
        assertEquals(fullList, movies);
    }

    @Test
    public void likeMovieTest() {
        var id = UUID.randomUUID();
        User user1 = buildUser();
        Movie movie = buildMovie().toBuilder().id(id).build();

        when(userService.getUser("username")).thenReturn(user1);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        var likedMovie = movieService.likeMovie(id, "username");

        assertTrue(likedMovie.getLikedBy().contains(user1));
        assertEquals(Integer.valueOf(1), likedMovie.getLikes());
    }

    @Test
    public void saveMovieTest() {
        var id = UUID.randomUUID();
        User user1 = buildUser();
        Movie movie = buildMovie().toBuilder().id(id).build();

        when(userService.getUser("username")).thenReturn(user1);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        var savedMovie = movieService.saveMovie(id, "username");

        assertTrue(savedMovie.getSavedBy().contains(user1));
        assertEquals(Integer.valueOf(1), savedMovie.getSaves());
    }

    @Test
    public void getMovieCommentsTest() {
        Comment comment1 = Comment.builder()
                .content("new comment")
                .user(buildUser())
                .movie(buildMovie())
                .timestamp(LocalDateTime.now())
                .build();
        Comment comment2 = Comment.builder()
                .content("another comment")
                .user(new User())
                .movie(buildMovie())
                .timestamp(LocalDateTime.now())
                .build();
        List<Comment> comments = Arrays.asList(comment1, comment2);
        Movie movie = buildMovie().toBuilder().comments(comments).build();

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        assertEquals(comments, movieService.getMovieComments(UUID.randomUUID()));
    }

    @Test
    public void unlikeMovieTest() {
        var id = UUID.randomUUID();
        User user = buildUser();
        Movie movie =
                buildMovie().toBuilder().likedBy(new HashSet<>(List.of(user))).build();

        when(userService.getUser("username")).thenReturn(user);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        movieService.unlikeMovie(id, "username");

        verify(userService).getUser("username");
        verify(movieRepository).save(any());
        assertFalse(movieService.getMovie(id).getLikedBy().contains(user));
    }

    @Test
    public void unsaveMovieTest() {
        var id = UUID.randomUUID();
        User user = buildUser();
        Movie movie =
                buildMovie().toBuilder().savedBy(new HashSet<>(List.of(user))).build();
        when(userService.getUser("username")).thenReturn(user);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        movieService.unsaveMovie(id, "username");

        verify(userService).getUser("username");
        verify(movieRepository).save(any());
        assertFalse(movieService.getMovie(id).getSavedBy().contains(user));
    }

    @Test
    public void testDeleteMovie() {
        User user1 = buildUser();
        Movie movie = buildMovie().toBuilder().user(user1).build();

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        movieService.deleteMovie(UUID.randomUUID(), "username");

        verify(movieRepository).findById(any());
        verify(movieRepository).deleteById(any());
    }

    private Movie buildMovie() {
        return Movie.builder()
                .title("movie")
                .description("desc")
                .year(2022)
                .likes(0)
                .saves(0)
                .savedBy(new HashSet<>())
                .likedBy(new HashSet<>())
                .build();
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
}
