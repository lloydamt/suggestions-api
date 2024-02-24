package com.lamt.suggestionsapi.service.interfaces;

import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MovieService {
    MovieDto suggestMovie(BaseMovieDto movie, String username);

    BaseMovieDto likeMovie(UUID movieId, String username);

    BaseMovieDto saveMovie(UUID movieId, String username);

    BaseMovieDto editMovie(UUID id, String username, BaseMovieDto movie);

    MovieDto getMovie(UUID movieId);

    Integer getMovieLikes(UUID movieId);

    Set<BaseMovieDto> getLikedMovies();

    Set<BaseMovieDto> getSavedMovies();

    Set<MovieDto> getAllMovies();

    Set<BaseMovieDto> getMoviesByUser(UUID userId);

    List<CommentDto> getMovieComments(UUID movieId);

    void unlikeMovie(UUID movieId, String username);

    void unsaveMovie(UUID movieId, String username);

    void deleteMovie(UUID movieId, String username);
}
