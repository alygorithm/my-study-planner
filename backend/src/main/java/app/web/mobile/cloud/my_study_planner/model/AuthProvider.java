package app.web.mobile.cloud.my_study_planner.model;

/**
 * Enum che rappresenta i provider di autenticazione disponibili nel sistema.
 *
 * Serve a distinguere tra utenti registrati direttamente nel sistema
 * (LOCAL) e utenti autenticati tramite provider esterni come Google (GOOGLE).
 */

public enum AuthProvider {
    LOCAL,
    GOOGLE
}