package app.web.mobile.cloud.my_study_planner.dto;

/**
 * DTO utilizzato per ricevere i dati di login dal client.
 *
 * Contiene le credenziali necessarie per autenticare un utente
 * nel sistema.
 */

public class LoginRequest {

    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
