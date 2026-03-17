package app.web.mobile.cloud.my_study_planner.service;

import app.web.mobile.cloud.my_study_planner.model.FocusSession;
import app.web.mobile.cloud.my_study_planner.repository.FocusSessionRepository;
import app.web.mobile.cloud.my_study_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio per gestire le FocusSession (sessioni di studio concentrate).
 *
 * Fornisce metodi per creare e recuperare le sessioni associate a un utente.
 * Utilizza il repository FocusSessionRepository e il servizio TaskService per la logica di business.
 */

@Service
public class FocusSessionService {

    private final FocusSessionRepository focusSessionRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    public FocusSessionService(FocusSessionRepository focusSessionRepository,
                              UserRepository userRepository,
                              TaskService taskService) {
        this.focusSessionRepository = focusSessionRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    public FocusSession createSession(String username, FocusSession session) {
        return taskService.createFocusSession(username, session);
    }

    public List<FocusSession> getAllSessions(String username) {
        return taskService.getAllFocusSessions(username);
    }
}