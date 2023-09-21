package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    MovieService movieService;
    UserService userService;

    @Override
    public Comment addComment(Comment comment, Long movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Movie movie = movieService.getMovie(movieId);
        User user = userService.getUserByEmail(authentication.getName());
        comment.setMovie(movie);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Comment comment = getComment(commentId);
        if (comment.getUser() != user) {
            throw new UserNotPermittedException();
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getComment(Long id) {
        Comment comment = unWrapComment(commentRepository.findById(id));
        return comment;
    }

    @Override
    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    @Override
    public List<Comment> findUserCommentForMovie(Long userId, Long movieId) {
        return commentRepository.findAllByUserIdAndMovieId(userId, movieId);
    }

    private static Comment unWrapComment(Optional<Comment> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new EntityNotFoundException(Comment.class);
    }
}
