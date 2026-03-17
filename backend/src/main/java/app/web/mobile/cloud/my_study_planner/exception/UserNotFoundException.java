package app.web.mobile.cloud.my_study_planner.exception;

/**
 * Eccezione personalizzata lanciata quando un utente richiesto
 * non è presente nel sistema.
 *
 * Estende RuntimeException per essere gestita dal GlobalExceptionHandler
 * e restituire una risposta HTTP 404 (Not Found) al client.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}