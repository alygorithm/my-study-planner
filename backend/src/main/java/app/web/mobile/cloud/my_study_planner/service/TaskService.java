package app.web.mobile.cloud.my_study_planner.service;

import app.web.mobile.cloud.my_study_planner.model.FocusSession;
import app.web.mobile.cloud.my_study_planner.model.Task;
import app.web.mobile.cloud.my_study_planner.model.User;
import app.web.mobile.cloud.my_study_planner.repository.FocusSessionRepository;
import app.web.mobile.cloud.my_study_planner.repository.TaskRepository;
import app.web.mobile.cloud.my_study_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FocusSessionRepository focusSessionRepository;

    public TaskService(TaskRepository taskRepository, 
                      UserRepository userRepository,
                      FocusSessionRepository focusSessionRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.focusSessionRepository = focusSessionRepository;
    }

    public Task createTask(String username, Task task) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        if (task.getCompleted() != null && task.getCompleted()) {
            task.setStatus("COMPLETED");
            task.setCompletedAt(LocalDateTime.now());
        } else if (task.getStatus() == null) {
            task.setStatus("TODO");
        }
        
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return taskRepository.findByUserOrderByTaskDayAsc(user);
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
        
        if (taskDetails.getTitle() != null) task.setTitle(taskDetails.getTitle());
        if (taskDetails.getDescription() != null) task.setDescription(taskDetails.getDescription());
        if (taskDetails.getSubject() != null) task.setSubject(taskDetails.getSubject());
        if (taskDetails.getTaskDay() != null) task.setTaskDay(taskDetails.getTaskDay());
        if (taskDetails.getPriority() != null) task.setPriorityFromFrontend(taskDetails.getPriority());
        if (taskDetails.getCompleted() != null) {
            task.setCompleted(taskDetails.getCompleted());
            if (taskDetails.getCompleted()) {
                task.setCompletedAt(LocalDateTime.now());
            }
        }
        if (taskDetails.getDuration() != null) task.setDuration(taskDetails.getDuration());
        if (taskDetails.getTime() != null) task.setTime(taskDetails.getTime());
        
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

    public FocusSession createFocusSession(String username, FocusSession session) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        
        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        
        if (session.getSessionDay() == null) {
            session.setSessionDay(LocalDateTime.now());
        }
        
        return focusSessionRepository.save(session);
    }

    public List<FocusSession> getAllFocusSessions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return focusSessionRepository.findByUserOrderBySessionDayDesc(user);
    }
}