package com.lamt.suggestionsapi.repository;

import com.lamt.suggestionsapi.entity.Movie;
import jakarta.transaction.Transactional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Set<Movie> findByUserId(Long userId);

    Set<Movie> findAll();

    @Transactional
    void deleteByIdAndUserId(Long id, Long userId);
}
