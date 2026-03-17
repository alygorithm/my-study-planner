package app.web.mobile.cloud.my_study_planner.repository;

import app.web.mobile.cloud.my_study_planner.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository JPA per gestire le operazioni CRUD sugli utenti.
 *
 * Estende JpaRepository per fornire metodi base di persistenza e query
 * personalizzate per recuperare utenti in base a email, username o provider esterno.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}

