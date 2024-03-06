package com.lamt.suggestionsapi.web;

import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.service.interfaces.CommentService;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable UUID id) {
        return new ResponseEntity<>(movieService.getMovie(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<MovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<Set<Movie>> getMoviesByUser(@PathVariable UUID userId) {
    //     return new ResponseEntity<>(movieService.getMoviesByUser(userId), HttpStatus.OK);
    // }

    @GetMapping("/{movieId}/comments")
    public ResponseEntity<List<CommentDto>> getMovieComments(@PathVariable UUID movieId) {
        return new ResponseEntity<>(movieService.getMovieComments(movieId), HttpStatus.OK);
    }

    @GetMapping("/liked")
    public ResponseEntity<Set<BaseMovieDto>> getLikedMovies() {
        return new ResponseEntity<>(movieService.getLikedMovies(), HttpStatus.OK);
    }

    @GetMapping("/saved")
    public ResponseEntity<Set<BaseMovieDto>> getSavedMovies() {
        return new ResponseEntity<>(movieService.getSavedMovies(), HttpStatus.OK);
    }

    @PostMapping("/suggest")
    public ResponseEntity<BaseMovieDto> suggestMovie(
            @Valid @RequestBody BaseMovieDto movie, Authentication authentication) {
        return new ResponseEntity<>(movieService.suggestMovie(movie, authentication.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseMovieDto> editMovie(
            @RequestBody BaseMovieDto movie, @PathVariable UUID id, Authentication authentication) {
        return new ResponseEntity<>(movieService.editMovie(id, authentication.getName(), movie), HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/like")
    public ResponseEntity<BaseMovieDto> likeMovie(@PathVariable UUID movieId, Authentication authentication) {
        return new ResponseEntity<>(movieService.likeMovie(movieId, authentication.getName()), HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/save")
    public ResponseEntity<BaseMovieDto> saveMovie(@PathVariable UUID movieId, Authentication authentication) {
        return new ResponseEntity<>(movieService.saveMovie(movieId, authentication.getName()), HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/unlike")
    public ResponseEntity<HttpStatus> unlikeMovie(@PathVariable UUID movieId, Authentication authentication) {
        movieService.unlikeMovie(movieId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/unsave")
    public ResponseEntity<HttpStatus> unsaveMovie(@PathVariable UUID movieId, Authentication authentication) {
        movieService.unsaveMovie(movieId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable UUID id, Authentication authentication) {
        movieService.deleteMovie(id, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/user/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getUserCommentsForMovie(UUID movieId, UUID userId) {
        return new ResponseEntity<>(commentService.findUserCommentForMovie(movieId, userId), HttpStatus.OK);
    }

    @PostMapping("/{movieId}/comment")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable UUID movieId, @Valid @RequestBody CommentDto comment, Authentication authentication) {
        var userId = UUID.fromString(authentication.getName());
        return new ResponseEntity<>(commentService.addComment(comment, movieId, userId), HttpStatus.CREATED);
    }
}
