package app.web.mobile.cloud.my_study_planner.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Gestore per le richieste non autorizzate (HTTP 401) quando un utente
 * non autenticato tenta di accedere a una risorsa protetta.
 *
 * Implementa AuthenticationEntryPoint per intercettare eccezioni
 * di tipo AuthenticationException generate da Spring Security.
 *
 * Restituisce una risposta JSON con codice HTTP 401 e messaggio di errore.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\": \"Unauthorized: " + authException.getMessage() + "\"}");
        writer.flush();
    }
}