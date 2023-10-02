package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MovieService {
    Movie suggestMovie(Movie movie, String username);

    Movie likeMovie(UUID movieId, String username);

    Movie saveMovie(UUID movieId, String username);

    Movie editMovie(UUID id, String username, Movie movie);

    Movie getMovie(UUID movieId);

    Integer getMovieLikes(UUID movieId);

    Set<Movie> getLikedMovies();

    Set<Movie> getSavedMovies();

    Set<Movie> getAllMovies();

    Set<Movie> getMoviesByUser(UUID userId);

    List<Comment> getMovieComments(UUID movieId);

    void unlikeMovie(UUID movieId, String username);

    void unsaveMovie(UUID movieId, String username);

    void deleteMovie(UUID movieId, String username);
}
