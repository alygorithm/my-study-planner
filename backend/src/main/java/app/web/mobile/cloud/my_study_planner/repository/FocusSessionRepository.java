package app.web.mobile.cloud.my_study_planner.repository;

import app.web.mobile.cloud.my_study_planner.model.FocusSession;
import app.web.mobile.cloud.my_study_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {
    List<FocusSession> findByUserOrderBySessionDayDesc(User user);
    List<FocusSession> findByUserAndSessionDayBetween(User user, LocalDateTime start, LocalDateTime end);
}