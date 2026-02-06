package app.web.mobile.cloud.my_study_planner.dto;

public class AuthResponse {

    private Long id;
    private String username;
    private String email;
    private String token; // <- nuovo

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
