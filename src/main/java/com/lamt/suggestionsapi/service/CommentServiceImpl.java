package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.exception.EntityNotFoundException;
import com.lamt.suggestionsapi.exception.UserNotPermittedException;
import com.lamt.suggestionsapi.mapper.CommentMapper;
import com.lamt.suggestionsapi.model.CommentDto;
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

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MovieService movieService;
    private final UserService userService;

    @Override
    public CommentDto addComment(CommentDto comment, UUID movieId, UUID userId) {
        final var movie = movieService.getMovie(movieId);
        final var user = userService.getUser(userId);
        comment.setMovie(movie);
        comment.setUser(user);
        final var commentEntity = commentMapper.mapToEntity(comment);
        return commentMapper.mapToDto(commentRepository.save(commentEntity));
    }

    @Override
    public void deleteComment(UUID commentId, UUID userId) {
        final var comment = getComment(commentId);
        if (comment.getUser().getId() != userId) {
            throw new UserNotPermittedException();
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto getComment(UUID id) {
        return commentMapper.mapToDto(
                commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Comment.class)));
    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentMapper.mapAllToDto(commentRepository.findAll());
    }

    @Override
    public List<CommentDto> findUserCommentForMovie(UUID userId, UUID movieId) {
        return commentMapper.mapAllToDto(commentRepository.findAllByUserIdAndMovieId(userId, movieId));
    }
}
