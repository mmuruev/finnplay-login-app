package org.finnplay.app.repository;

import org.finnplay.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLoginEmailIgnoreCase(String login);

    Boolean existsByLoginEmailIgnoreCase(String login);
}
