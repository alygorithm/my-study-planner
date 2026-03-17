package app.web.mobile.cloud.my_study_planner.dto;

<<<<<<< HEAD
/**
 * DTO utilizzato per ricevere i dati di registrazione
 * di un nuovo utente dal client.
 *
 * Contiene le informazioni necessarie alla creazione di un account
 * all'interno del sistema.
 */

=======
>>>>>>> 60275efaa897e2ccda2fb5d032e748394b97ad35
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
