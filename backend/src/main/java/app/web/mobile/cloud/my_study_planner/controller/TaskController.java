package app.web.mobile.cloud.my_study_planner.controller;

import app.web.mobile.cloud.my_study_planner.model.FocusSession;
import app.web.mobile.cloud.my_study_planner.model.Task;
import app.web.mobile.cloud.my_study_planner.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST per la gestione delle task e delle sessioni di focus
 * 
 * Espone endpoint per :
 * - Creazione, lettura, aggiornamento ed eliminazione task
 * - Creazione e recupero delle sessioni di studio
 * 
 * Tutte le operazioni sono associate all'utente autenticato tramite Spring Security
 * La logica applicativa viene delegata al TaskService
 */
@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody Task task) {
        Task createdTask = taskService.createTask(userDetails.getUsername(), task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        List<Task> tasks = taskService.getAllTasks(userDetails.getUsername());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long id) {
        Task task = taskService.getTaskById(userDetails.getUsername(), id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id,
                                        @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(userDetails.getUsername(), id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id) {
        taskService.deleteTask(userDetails.getUsername(), id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task eliminato con successo");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/focus-sessions")
    public ResponseEntity<?> createFocusSession(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestBody FocusSession session) {
        FocusSession createdSession = taskService.createFocusSession(userDetails.getUsername(), session);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/focus-sessions")
    public ResponseEntity<?> getAllFocusSessions(@AuthenticationPrincipal UserDetails userDetails) {
        List<FocusSession> sessions = taskService.getAllFocusSessions(userDetails.getUsername());
        return ResponseEntity.ok(sessions);
    }
}