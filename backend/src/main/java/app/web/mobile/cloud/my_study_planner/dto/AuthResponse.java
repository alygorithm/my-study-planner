package app.web.mobile.cloud.my_study_planner.dto;

/**
 * DTO (Data Transfer Object) per la restituzione dei dati di autenticazione al client
 * 
 * Contiene le informazioni principali dell'utente autenticato insieme
 * al token JWT necessario per autorizzare le richieste successive
 */
public class AuthResponse {

    private Long id;
    private String username;
    private String email;
    private String token; 

    public AuthResponse(Long id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}
