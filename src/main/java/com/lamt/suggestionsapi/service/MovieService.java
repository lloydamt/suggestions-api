package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import java.util.List;
import java.util.Set;

public interface MovieService {
    Movie suggestMovie(Movie movie, String username);

    Movie likeMovie(Long movieId, String username);

    Movie saveMovie(Long movieId, String username);

    Movie editMovie(Long id, String username, Movie movie);

    Movie getMovie(Long movieId);

    Integer getMovieLikes(Long movieId);

    Set<Movie> getLikedMovies();

    Set<Movie> getSavedMovies();

    Set<Movie> getAllMovies();

    Set<Movie> getMoviesByUser(Long userId);

    List<Comment> getMovieComments(Long movieId);

    void unlikeMovie(Long movieId, String username);

    void unsaveMovie(Long movieId, String username);

    void deleteMovie(Long movieId, String username);
}
