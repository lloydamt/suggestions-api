package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.repository.MovieRepository;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    UserService userService;

    @Override
    public Movie suggestMovie(Movie movie, String username) {
        User user = userService.getUserByEmail(username);
        movie.setUser(user);
        return movieRepository.save(movie);
    }

    @Override
    public Movie editMovie(Long id, String username, Movie movie) {
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
    public Movie getMovie(Long movieId) {
        Movie movie = unwrapMovie(movieRepository.findById(movieId));
        return movie;
    }

    @Override
    public Integer getMovieLikes(Long movieId) {
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
    public Set<Movie> getMoviesByUser(Long userId) {
        Set<Movie> movies = movieRepository.findByUserId(userId);
        return movies;
    }

    @Override
    public Movie likeMovie(Long movieId, String username) {
        return likeOrSaveUser(movieId, username, "like");
    }

    @Override
    public Movie saveMovie(Long movieId, String username) {
        return likeOrSaveUser(movieId, username, "save");
    }

    @Override
    public List<Comment> getMovieComments(Long movieId) {
        Movie movie = getMovie(movieId);
        return movie.getComments();
    }

    // Write tests for this
    @Override
    public void unlikeMovie(Long movieId, String username) {
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
    public void unsaveMovie(Long movieId, String username) {
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
    public void deleteMovie(Long id, String username) {
        checkUserOwnsMovie(id, username);
        movieRepository.deleteById(id);
    }

    private static Movie unwrapMovie(Optional<Movie> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new EntityNotFoundException(Movie.class);
    }

    private Movie likeOrSaveUser(Long movieId, String username, String interaction) {
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

    private void checkUserOwnsMovie(Long id, String username) {
        Movie movie = getMovie(id);
        if (!movie.getUser().getEmail().equalsIgnoreCase(username)) {
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
