package com.lamt.suggestionsapi.repository;

import com.lamt.suggestionsapi.entity.Comment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByUserIdAndMovieId(Long userId, Long movieId);
}
