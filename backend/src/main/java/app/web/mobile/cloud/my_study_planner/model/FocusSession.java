package app.web.mobile.cloud.my_study_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "focus_sessions")
public class FocusSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Integer minutes;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "session_day")
    private LocalDateTime sessionDay;

    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // GETTER E SETTER STANDARD

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getMinutes() { return minutes; }
    public void setMinutes(Integer minutes) { this.minutes = minutes; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public LocalDateTime getSessionDay() { return sessionDay; }
    public void setSessionDay(LocalDateTime sessionDay) { this.sessionDay = sessionDay; }

    @JsonProperty("day")
    public String getDayAsString() {
        return sessionDay != null ? sessionDay.toString() : null;
    }

    @JsonProperty("day")
    public void setDayFromString(String day) {
        if (day != null && !day.isEmpty()) {
            try {
                this.sessionDay = LocalDateTime.parse(day);
            } catch (Exception e) {
                this.sessionDay = LocalDateTime.parse(day.substring(0, 19));
            }
        }
    }

    @JsonProperty("taskId")
    public String getTaskIdAsString() {
        return taskId != null ? taskId.toString() : null;
    }

    @JsonProperty("taskId")
    public void setTaskIdFromString(String taskId) {
        this.taskId = taskId != null ? Long.parseLong(taskId) : null;
    }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}