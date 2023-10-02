package com.lamt.suggestionsapi.repository;

import com.lamt.suggestionsapi.entity.Comment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
    List<Comment> findAllByUserIdAndMovieId(UUID userId, UUID movieId);
}
