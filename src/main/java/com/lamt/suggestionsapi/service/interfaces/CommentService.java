package com.lamt.suggestionsapi.service.interfaces;

import com.lamt.suggestionsapi.entity.Comment;
import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment addComment(Comment comment, UUID movieId, UUID userId);

    void deleteComment(UUID commentId, UUID userId);

    Comment getComment(UUID id);

    List<Comment> getAllComments();

    List<Comment> findUserCommentForMovie(UUID userId, UUID movieId);
}
