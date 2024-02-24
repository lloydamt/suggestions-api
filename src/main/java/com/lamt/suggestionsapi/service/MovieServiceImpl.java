package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.mapper.MovieMapper;
import com.lamt.suggestionsapi.mapper.UserMapper;
import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.repository.MovieRepository;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserMapper userMapper;
    private final MovieMapper movieMapper;
    private final UserService userService;

    @Override
    public MovieDto suggestMovie(BaseMovieDto movie, String username) {
        final var user = userMapper.mapDtoToEntity(userService.getUser(username));
        final var movieEntity = movieMapper.mapDtoToEntity((MovieDto) movie);
        movieEntity.setUser(user);
        movieRepository.save(movieEntity);
        return movieMapper.mapEntityToDto(movieEntity);
    }

    @Override
    public BaseMovieDto editMovie(UUID id, String username, BaseMovieDto movie) {
        final var existingMovie = getMovie(id);

        checkUserOwnsMovie(existingMovie, username);

        if (movie.getUser() != null) {
            throw new UserNotPermittedException();
        }

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

        final var movieEntity = movieMapper.mapDtoToEntity(existingMovie);

        movieRepository.save(movieEntity);

        return movieMapper.mapEntityToDto(movieEntity);
    }

    @Override
    public MovieDto getMovie(UUID movieId) {
        return movieMapper.mapEntityToDto(
                movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class)));
    }

    @Override
    public Integer getMovieLikes(UUID movieId) {
        final var movie = getMovie(movieId);
        return movie.getLikes();
    }

    @Override
    public Set<BaseMovieDto> getLikedMovies() {
        return getMovieSubset("like");
    }

    @Override
    public Set<BaseMovieDto> getSavedMovies() {
        return getMovieSubset("save");
    }

    @Override
    public Set<MovieDto> getAllMovies() {
        return movieMapper.mapFullSetToDto(movieRepository.findAll());
    }

    @Override
    public Set<BaseMovieDto> getMoviesByUser(UUID userId) {
        return movieMapper.mapSetToDto(movieRepository.findByUserId(userId));
    }

    @Override
    public MovieDto likeMovie(UUID movieId, String username) {
        return likeOrSaveUser(movieId, username, "like");
    }

    @Override
    public MovieDto saveMovie(UUID movieId, String username) {
        return likeOrSaveUser(movieId, username, "save");
    }

    @Override
    public List<CommentDto> getMovieComments(UUID movieId) {
        final var movie = getMovie(movieId);
        return movie.getComments();
    }

    // Write tests for this
    @Override
    public void unlikeMovie(UUID movieId, String username) {
        final var movie = getMovie(movieId);
        final var user = userService.getUser(username);
        if (movie.getLikedBy().stream()
                .noneMatch(searchUser -> searchUser.getUsername().equals(user.getUsername()))) {
            throw new RuntimeException("Cannot perform action");
        }
        movie.getLikedBy().remove(user);
        if (movie.getLikes() > 0) {
            movie.setLikes(movie.getLikes() - 1);
        }
        movieRepository.save(movieMapper.mapDtoToEntity(movie));
    }

    @Override
    public void unsaveMovie(UUID movieId, String username) {
        final var movie = getMovie(movieId);
        final var user = userService.getUser(username);
        if (movie.getSavedBy().stream()
                .noneMatch(searchUser -> searchUser.getUsername().equals(user.getUsername()))) {
            throw new RuntimeException("Cannot perform action");
        }
        movie.getSavedBy().remove(user);
        if (movie.getSaves() > 0) {
            movie.setSaves(movie.getSaves() - 1);
        }
        movieRepository.save(movieMapper.mapDtoToEntity(movie));
    }

    @Override
    @Transactional
    public void deleteMovie(UUID id, String username) {
        checkUserOwnsMovie(getMovie(id), username);
        movieRepository.deleteById(id);
    }

    private MovieDto likeOrSaveUser(UUID movieId, String username, String interaction) {
        final var user = userService.getUser(username);
        final var movie = getMovie(movieId);
        if (interaction.equals("like") && !userLikesMovie(user, movie)) {
            movie.getLikedBy().add(user);
            movie.setLikes(movie.getLikes() + 1);
            movieRepository.save(movieMapper.mapDtoToEntity(movie));
            return movie;
        }

        if (interaction.equals("save") && !userSavedMovie(user, movie)) {
            movie.getSavedBy().add(user);
            movie.setSaves(movie.getSaves() + 1);
            movieRepository.save(movieMapper.mapDtoToEntity(movie));
            return movie;
        }

        throw new RuntimeException("Action cannot be performed twice");
    }

    private boolean userLikesMovie(UserDto user, MovieDto movie) {
        return user.getLikes().contains(movie);
    }

    private boolean userSavedMovie(UserDto user, MovieDto movie) {
        return user.getSaved().contains(movie);
    }

    private void checkUserOwnsMovie(MovieDto movie, String username) {
        if (!movie.getUser().getUsername().equalsIgnoreCase(username)) {
            throw new UserNotPermittedException();
        }
    }

    // Cause get liked movies to return movies if like > 0

    private Set<BaseMovieDto> getMovieSubset(String interaction) {
        if (!(interaction.equals("like") || interaction.equals("save"))) {
            throw new RuntimeException("Interaction is invalid");
        }
        final var movies = getAllMovies();
        return movies.stream().filter(movie -> condition(movie, interaction)).collect(Collectors.toSet());
    }

    private boolean condition(MovieDto movie, String interaction) {
        return interaction.equals("like") ? movie.getLikes() > 0 : movie.getSaves() > 0;
    }
}
