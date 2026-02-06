package app.web.mobile.cloud.my_study_planner.security;

import app.web.mobile.cloud.my_study_planner.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
            throws ServletException, IOException {
        
        String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            
            // Verifica se il token è valido
            if (jwtUtils.validateToken(jwt)) {
                username = jwtUtils.getUsernameFromToken(jwt);
            }
        }
        
        // Se abbiamo uno username e l'utente non è ancora autenticato
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Cerca l'utente nel database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Crea l'oggetto di autenticazione
                UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // Imposta l'autenticazione nel contesto di sicurezza
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
            } catch (Exception e) {
                // Se l'utente non viene trovato, lascia il contesto di sicurezza vuoto
                // Questo farà scattare l'AuthenticationEntryPoint
                SecurityContextHolder.clearContext();
            }
        }
        
        filterChain.doFilter(request, response);
    }
}