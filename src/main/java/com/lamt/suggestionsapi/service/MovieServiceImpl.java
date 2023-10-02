package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.repository.MovieRepository;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    UserService userService;

    @Override
    public Movie suggestMovie(Movie movie, String username) {
        User user = userService.getUser(username);
        movie.setUser(user);
        return movieRepository.save(movie);
    }

    @Override
    public Movie editMovie(UUID id, String username, Movie movie) {
        checkUserOwnsMovie(id, username);

        Movie existingMovie = this.getMovie(id);

        if (movie.getDescription() != null) {
            existingMovie.setDescription(movie.getDescription());
        }

        if (movie.getTitle() != null) {
            existingMovie.setTitle(movie.getTitle());
        }

        if (movie.getYear() != null) {
            existingMovie.setYear(movie.getYear());
        }

        if (movie.getGenre() != null) {
            existingMovie.setGenre(movie.getGenre());
        }

        return movieRepository.save(existingMovie);
    }

    @Override
    public Movie getMovie(UUID movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class));
        return movie;
    }

    @Override
    public Integer getMovieLikes(UUID movieId) {
        Movie movie = getMovie(movieId);
        return movie.getLikes();
    }

    @Override
    public Set<Movie> getLikedMovies() {
        return getMovieSubset("like");
    }

    @Override
    public Set<Movie> getSavedMovies() {
        return getMovieSubset("saved");
    }

    @Override
    public Set<Movie> getAllMovies() {
        Set<Movie> allMovies = movieRepository.findAll();
        return allMovies;
    }

    @Override
    public Set<Movie> getMoviesByUser(UUID userId) {
        Set<Movie> movies = movieRepository.findByUserId(userId);
        return movies;
    }

    @Override
    public Movie likeMovie(UUID movieId, String username) {
        return likeOrSaveUser(movieId, username, "like");
    }

    @Override
    public Movie saveMovie(UUID movieId, String username) {
        return likeOrSaveUser(movieId, username, "save");
    }

    @Override
    public List<Comment> getMovieComments(UUID movieId) {
        Movie movie = getMovie(movieId);
        return movie.getComments();
    }

    // Write tests for this
    @Override
    public void unlikeMovie(UUID movieId, String username) {
        Movie movie = getMovie(movieId);
        User user = userService.getUserByEmail(username);
        if (movie.getLikedBy().contains(user)) {
            movie.getLikedBy().remove(user);
        } else {
            throw new RuntimeException("Cannot perform action");
        }
        if (movie.getLikes() > 0) {
            movie.setLikes(movie.getLikes() - 1);
        }
        movieRepository.save(movie);
    }

    @Override
    public void unsaveMovie(UUID movieId, String username) {
        Movie movie = getMovie(movieId);
        User user = userService.getUserByEmail(username);
        movie.getSavedBy().remove(user);
        if (movie.getSaves() > 0) {
            movie.setSaves(movie.getSaves() - 1);
        }
        movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(UUID id, String username) {
        checkUserOwnsMovie(id, username);
        movieRepository.deleteById(id);
    }

    private Movie likeOrSaveUser(UUID movieId, String username, String interaction) {
        User user = userService.getUserByEmail(username);
        Movie movie = getMovie(movieId);
        if (interaction.equals("like") && !userLikesMovie(user, movie)) {
            movie.getLikedBy().add(user);
            movie.setLikes(movie.getLikes() + 1);
            return movieRepository.save(movie);
        }

        if (interaction.equals("save") && !userSavedMovie(user, movie)) {
            movie.getSavedBy().add(user);
            movie.setSaves(movie.getSaves() + 1);
            return movieRepository.save(movie);
        }

        throw new RuntimeException("Action cannot be performed twice");
    }

    private boolean userLikesMovie(User user, Movie movie) {
        return user.getLikes().contains(movie);
    }

    private boolean userSavedMovie(User user, Movie movie) {
        return user.getSaved().contains(movie);
    }

    private void checkUserOwnsMovie(UUID id, String username) {
        Movie movie = getMovie(id);
        if (!movie.getUser().getUsername().equalsIgnoreCase(username)) {
            throw new UserNotPermittedException();
        }
    }

    // Cause get liked movies to return movies if like > 0

    private Set<Movie> getMovieSubset(String interaction) {
        Set<Movie> movies = getAllMovies();
        Set<Movie> movieSubset = new HashSet<>();
        for (Movie movie : movies) {
            if (!condition(movie, interaction).isEmpty()) {
                movieSubset.add(movie);
            }
        }
        return movieSubset;
    }

    private Set<User> condition(Movie movie, String interaction) {
        return interaction.equals("like") ? movie.getLikedBy() : movie.getSavedBy();
    }
}
