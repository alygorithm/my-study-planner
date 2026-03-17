package app.web.mobile.cloud.my_study_planner.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Rappresenta un errore restituito dalle API in caso di eccezioni o richieste non valide.
 *
 * Contiene informazioni utili al client per comprendere l'errore:
 * - messaggio descrittivo
 * - codice di stato HTTP
 * - timestamp della generazione dell'errore
 */

public class ApiError {
    
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
