package app.web.mobile.cloud.my_study_planner.service;

import app.web.mobile.cloud.my_study_planner.dto.LoginRequest;
import app.web.mobile.cloud.my_study_planner.dto.RegisterRequest;
import app.web.mobile.cloud.my_study_planner.exception.InvalidCredentialsException;
import app.web.mobile.cloud.my_study_planner.exception.UserAlreadyExistsException;
import app.web.mobile.cloud.my_study_planner.exception.UserNotFoundException;
import app.web.mobile.cloud.my_study_planner.model.AuthProvider;
import app.web.mobile.cloud.my_study_planner.model.User;
import app.web.mobile.cloud.my_study_planner.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servizio per gestire le operazioni sugli utenti.
 *
 * Questo servizio si occupa di registrazione, login, recupero e cancellazione degli utenti.
 * Implementa anche la logica di validazione delle credenziali e gestione degli account OAuth/LOCAL.
 *
 * Collabora con il repository UserRepository e usa BCryptPasswordEncoder per la gestione delle password.
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email già utilizzata");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username già utilizzato");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider(AuthProvider.LOCAL);

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenziali non valide"));

        // Controlla se l'utente è stato eliminato (soft delete futuro)
        if (user.getPassword() == null && user.getProvider() != AuthProvider.GOOGLE) {
            throw new InvalidCredentialsException("Account eliminato");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenziali non valide");
        }

        return user;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
    }

    public void deleteUser(String username, UserDetails authenticatedUser) {
        User userToDelete = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        if (!userToDelete.getUsername().equals(authenticatedUser.getUsername())) {
            throw new RuntimeException("Non hai il permesso di eliminare questo account");
        }

        userRepository.delete(userToDelete);
    }

    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
        userRepository.delete(user);
    }
}