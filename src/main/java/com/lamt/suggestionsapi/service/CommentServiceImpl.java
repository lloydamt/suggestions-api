package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.repository.CommentRepository;
import com.lamt.suggestionsapi.service.interfaces.CommentService;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    MovieService movieService;
    UserService userService;

    @Override
    public Comment addComment(Comment comment, UUID movieId, UUID userId) {
        Movie movie = movieService.getMovie(movieId);
        User user = userService.getUser(userId);
        comment.setMovie(movie);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId, UUID userId) {
        Comment comment = getComment(commentId);
        if (comment.getUser().getId() != userId) {
            throw new UserNotPermittedException();
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getComment(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Comment.class));
        return comment;
    }

    @Override
    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    @Override
    public List<Comment> findUserCommentForMovie(UUID userId, UUID movieId) {
        return commentRepository.findAllByUserIdAndMovieId(userId, movieId);
    }
}
