package app.web.mobile.cloud.my_study_planner.controller;

import app.web.mobile.cloud.my_study_planner.model.Task;
import app.web.mobile.cloud.my_study_planner.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody Task task) {
        Task createdTask = taskService.createTask(userDetails.getUsername(), task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        List<Task> tasks = taskService.getAllTasks(userDetails.getUsername());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long id) {
        Task task = taskService.getTaskById(userDetails.getUsername(), id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id,
                                        @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(userDetails.getUsername(), id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id) {
        taskService.deleteTask(userDetails.getUsername(), id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task eliminato con successo");
        return ResponseEntity.ok(response);
    }
}