package app.web.mobile.cloud.my_study_planner.service;

import app.web.mobile.cloud.my_study_planner.model.Task;
import app.web.mobile.cloud.my_study_planner.model.User;
import app.web.mobile.cloud.my_study_planner.repository.TaskRepository;
import app.web.mobile.cloud.my_study_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(String username, Task task) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return taskRepository.findByUserOrderByDueDateAsc(user);
    }

    public Task getTaskById(String username, Long taskId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        return taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task non trovato"));
    }

    public Task updateTask(String username, Long taskId, Task taskDetails) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task non trovato"));
        
        // Aggiorna solo i campi che sono stati inviati (non null)
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }
        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }
        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }
        
        task.setUpdatedAt(LocalDateTime.now());
        
        return taskRepository.save(task);
    }

    public void deleteTask(String username, Long taskId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task non trovato"));
        
        taskRepository.delete(task);
    }
}