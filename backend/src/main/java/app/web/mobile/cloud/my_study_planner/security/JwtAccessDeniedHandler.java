package app.web.mobile.cloud.my_study_planner.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Gestore per gli accessi negati (HTTP 403) quando un utente
 * autenticato tenta di accedere a una risorsa non autorizzata.
 *
 * Implementa AccessDeniedHandler per intercettare eccezioni
 * di tipo AccessDeniedException generate da Spring Security.
 *
 * Restituisce una risposta JSON con codice HTTP 403 e messaggio di errore.
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\": \"Access denied: " + accessDeniedException.getMessage() + "\"}");
        writer.flush();
    }
}