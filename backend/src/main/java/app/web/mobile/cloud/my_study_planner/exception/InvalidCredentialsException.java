package app.web.mobile.cloud.my_study_planner.exception;

/**
 * Eccezione personalizzata lanciata quando le credenziali fornite
 * per l'autenticazione di un utente non sono corrette.
 *
 * Estende RuntimeException per essere gestita dal GlobalExceptionHandler.
 */

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
