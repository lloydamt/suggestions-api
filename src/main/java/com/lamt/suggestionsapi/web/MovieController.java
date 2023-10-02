package com.lamt.suggestionsapi.web;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
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
    CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable UUID id) {
        return new ResponseEntity<>(movieService.getMovie(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<Set<Movie>> getMoviesByUser(@PathVariable UUID userId) {
    //     return new ResponseEntity<>(movieService.getMoviesByUser(userId), HttpStatus.OK);
    // }

    @GetMapping("/{movieId}/comments")
    public ResponseEntity<List<Comment>> getMovieComments(@PathVariable UUID movieId) {
        return new ResponseEntity<>(movieService.getMovieComments(movieId), HttpStatus.OK);
    }

    @GetMapping("/liked")
    public ResponseEntity<Set<Movie>> getLikedMovies() {
        return new ResponseEntity<>(movieService.getLikedMovies(), HttpStatus.OK);
    }

    @GetMapping("/saved")
    public ResponseEntity<Set<Movie>> getSavedMovies() {
        return new ResponseEntity<>(movieService.getSavedMovies(), HttpStatus.OK);
    }

    @PostMapping("/suggest")
    public ResponseEntity<Movie> suggestMovie(@Valid @RequestBody Movie movie, Authentication authentication) {
        return new ResponseEntity<>(movieService.suggestMovie(movie, authentication.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> editMovie(
            @RequestBody Movie movie, @PathVariable UUID id, Authentication authentication) {
        return new ResponseEntity<>(movieService.editMovie(id, authentication.getName(), movie), HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/like")
    public ResponseEntity<Movie> likeMovie(@PathVariable UUID movieId, Authentication authentication) {
        return new ResponseEntity<>(movieService.likeMovie(movieId, authentication.getName()), HttpStatus.OK);
    }

    @PatchMapping("/{movieId}/save")
    public ResponseEntity<Movie> saveMovie(@PathVariable UUID movieId, Authentication authentication) {
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
    public ResponseEntity<List<Comment>> getUserCommentsForMovie(UUID movieId, UUID userId) {
        return new ResponseEntity<>(commentService.findUserCommentForMovie(movieId, userId), HttpStatus.OK);
    }

    @PostMapping("/{movieId}/comment")
    public ResponseEntity<Comment> addComment(
            @PathVariable UUID movieId, @Valid @RequestBody Comment comment, Authentication authentication) {
        var userId = UUID.fromString(authentication.getName());
        return new ResponseEntity<Comment>(commentService.addComment(comment, movieId, userId), HttpStatus.CREATED);
    }
}
