package app.web.mobile.cloud.my_study_planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestore globale delle eccezioni per le API REST.
 *
 * Utilizza @RestControllerAdvice per intercettare tutte le eccezioni
 * lanciate dai controller e restituire risposte HTTP standardizzate
 * al client.
 *
 * Gestisce sia eccezioni personalizzate che generiche.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestisce le eccezioni di tipo InvalidCredentialsException.
     *
     * Restituisce uno stato HTTP 401 (UNAUTHORIZED) con un messaggio di errore.
     *
     * @param ex eccezione lanciata quando le credenziali sono errate
     * @return risposta HTTP contenente il messaggio di errore
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    /**
     * Gestisce le eccezioni di tipo UserAlreadyExistsException.
     *
     * Restituisce uno stato HTTP 400 (BAD_REQUEST) con un messaggio di errore
     * indicando che l'utente esiste già nel sistema.
     *
     * @param ex eccezione lanciata quando si tenta di registrare un utente già esistente
     * @return risposta HTTP contenente il messaggio di errore
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Gestisce le eccezioni di tipo UserNotFoundException.
     *
     * Restituisce uno stato HTTP 404 (NOT_FOUND) con un messaggio di errore.
     *
     * @param ex eccezione lanciata quando l'utente richiesto non esiste
     * @return risposta HTTP contenente il messaggio di errore
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * Gestisce le eccezioni di tipo UsernameNotFoundException (Spring Security).
     *
     * Restituisce uno stato HTTP 404 (NOT_FOUND) con un messaggio generico
     * "Utente non trovato".
     *
     * @param ex eccezione lanciata quando lo username non è presente nel sistema
     * @return risposta HTTP contenente il messaggio di errore
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Utente non trovato");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * Gestisce tutte le eccezioni generiche non catturate dai metodi precedenti.
     *
     * Restituisce uno stato HTTP 500 (INTERNAL_SERVER_ERROR) con un messaggio
     * di errore, permettendo di gestire eventuali errori imprevisti.
     *
     * @param ex eccezione generica lanciata dal sistema
     * @return risposta HTTP contenente il messaggio di errore
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        ex.printStackTrace(); // Per debug in console
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage() != null ? ex.getMessage() : "Internal server error");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}