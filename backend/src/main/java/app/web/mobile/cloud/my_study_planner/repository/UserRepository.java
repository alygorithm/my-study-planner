package app.web.mobile.cloud.my_study_planner.repository;

import app.web.mobile.cloud.my_study_planner.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}

