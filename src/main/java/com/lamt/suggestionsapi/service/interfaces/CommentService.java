package com.lamt.suggestionsapi.service.interfaces;

import com.lamt.suggestionsapi.model.CommentDto;
import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDto addComment(CommentDto comment, UUID movieId, UUID userId);

    void deleteComment(UUID commentId, UUID userId);

    CommentDto getComment(UUID id);

    List<CommentDto> getAllComments();

    List<CommentDto> findUserCommentForMovie(UUID userId, UUID movieId);
}
