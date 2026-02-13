package app.web.mobile.cloud.my_study_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String subject;

    @Column(name = "task_day")
    private LocalDateTime taskDay;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status;

    @Column
    private Boolean completed = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column
    private Integer duration;

    @Column
    private String time;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // GETTER E SETTER STANDARD

    @JsonProperty("_id")
    public String getIdAsString() {
        return id != null ? id.toString() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDateTime getTaskDay() { return taskDay; }
    public void setTaskDay(LocalDateTime taskDay) { this.taskDay = taskDay; }

    @JsonProperty("day")
    public String getDayAsString() {
        return taskDay != null ? taskDay.toString() : null;
    }

    @JsonProperty("day")
    public void setDayFromString(String day) {
        if (day != null && !day.isEmpty()) {
            try {
                this.taskDay = LocalDateTime.parse(day);
            } catch (Exception e) {
                this.taskDay = LocalDateTime.parse(day.substring(0, 19));
            }
        }
    }

    public String getPriority() { 
        if ("HIGH".equalsIgnoreCase(priority)) return "alta";
        if ("LOW".equalsIgnoreCase(priority)) return "bassa";
        return "media";
    }
    
    @JsonProperty("priority")
    public void setPriorityFromFrontend(String priority) {
        if (priority == null) {
            this.priority = "MEDIUM";
            return;
        }
        switch (priority.toLowerCase()) {
            case "alta":
            case "high":
                this.priority = "HIGH";
                break;
            case "bassa":
            case "low":
                this.priority = "LOW";
                break;
            case "media":
            case "medium":
            default:
                this.priority = "MEDIUM";
                break;
        }
    }

    public Boolean getCompleted() { 
        return "COMPLETED".equalsIgnoreCase(status) || (completed != null && completed);
    }
    
    @JsonProperty("completed")
    public void setCompleted(Boolean completed) {
        this.completed = completed;
        this.status = completed ? "COMPLETED" : "TODO";
        if (completed) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // Getter e setter per status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}