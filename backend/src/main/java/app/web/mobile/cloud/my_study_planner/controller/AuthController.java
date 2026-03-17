package app.web.mobile.cloud.my_study_planner.controller;

import app.web.mobile.cloud.my_study_planner.dto.JwtResponse;
import app.web.mobile.cloud.my_study_planner.dto.LoginRequest;
import app.web.mobile.cloud.my_study_planner.dto.RegisterRequest;
import app.web.mobile.cloud.my_study_planner.model.User;
import app.web.mobile.cloud.my_study_planner.security.JwtUtils;
import app.web.mobile.cloud.my_study_planner.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller REST responsabile della gestione dell'autenticazione degli utenti
 * 
 * Esponde endpoint per:
 * - Registrazione di nuovi utenti
 * - Login con generazione di token JWT
 * - Recupero informazioni utente autenticato
 * - Eliminazione account
 * 
 * Utilizza UserService per la logica applicativa e JwtUtils per la gestione
 * dei token di autenticazione
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    /**
     * Riceve i dati di registrazione dal client, delega la creazione
     * dell'utente al servizio e restituisce l'utente creato
     */
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    /** 
     * Verifica le credenziali fornite e, se valide, genera un token JWT
     * che verrà poi utilizzato per autenticare le successive richieste
     */
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));
    }

    @GetMapping("/me")
    // l'utente viene recuperato tramite i dettagli presenti nel token
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/info")
    // restituisce solo dati selezionati come id, username, email e provider
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByUsername(userDetails.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("provider", user.getProvider().toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    // eliminazione effettuata tramite servizio utenti usando l'username estratto dal token
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.deleteUser(username, userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Account eliminato con successo");
        return ResponseEntity.ok(response);
    }
}