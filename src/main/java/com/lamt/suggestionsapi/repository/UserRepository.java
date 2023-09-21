package com.lamt.suggestionsapi.repository;

import com.lamt.suggestionsapi.entity.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameAndEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByUsername(String username);
}
