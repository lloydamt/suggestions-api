package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.entity.Comment;
import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment, Long movieId);

    void deleteComment(Long commentId);

    Comment getComment(Long id);

    List<Comment> getAllComments();

    List<Comment> findUserCommentForMovie(Long userId, Long movieId);
}
