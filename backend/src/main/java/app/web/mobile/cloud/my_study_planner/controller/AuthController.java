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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/info")
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
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.deleteUser(username, userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Account eliminato con successo");
        return ResponseEntity.ok(response);
    }
}