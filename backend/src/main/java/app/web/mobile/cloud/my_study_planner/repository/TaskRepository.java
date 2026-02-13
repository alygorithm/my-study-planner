package app.web.mobile.cloud.my_study_planner.repository;

import app.web.mobile.cloud.my_study_planner.model.Task;
import app.web.mobile.cloud.my_study_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserOrderByTaskDayAsc(User user);
    Optional<Task> findByIdAndUser(Long id, User user);
    List<Task> findByUserAndTaskDayBetween(User user, LocalDateTime start, LocalDateTime end);
}