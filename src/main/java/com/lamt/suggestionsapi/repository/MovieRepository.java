package com.lamt.suggestionsapi.repository;

import com.lamt.suggestionsapi.entity.Movie;
import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, UUID> {
    Set<Movie> findByUserId(UUID userId);

    Set<Movie> findAll();

    @Transactional
    void deleteByIdAndUserId(UUID id, UUID userId);
}
