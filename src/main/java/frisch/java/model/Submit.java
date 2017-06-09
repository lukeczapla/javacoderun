package frisch.java.model;

import com.fasterxml.jackson.annotation.JsonView;
import frisch.java.controller.JsonViews;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by luke on 6/4/16.
 */
@Entity
public class Submit implements IDable<Long> {

    @Id
    @GeneratedValue
    @JsonView(value = {JsonViews.Submit.class})
    private Long id;

 //   @Column(nullable=false)
 //   @JsonView(value = {JsonViews.Submit.class})
 //   private Long userId;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;

    @Column(nullable = false)
    @JsonView(value = {JsonViews.Submit.class})
    private Long assignmentId;


    @Column(length=100)
    @JsonView(value = {JsonViews.Submit.class})
    private String className;

    @Column(columnDefinition = "TEXT", nullable=false, length=65536)
    @JsonView(value = {JsonViews.Submit.class})
    private String text;

    @Column(nullable = false)
    @JsonView(value = {JsonViews.Submit.class})
    private Long score;

    @Column(nullable = false)
    @JsonView(value = {JsonViews.Submit.class})
    private boolean passed;

    @Column(nullable = false)
    @JsonView(value = {JsonViews.Submit.class})
    private Date date;


    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() { return user; }
    public String getClassName() { return className; }
    public String getText() { return text; }
    public Long getAssignmentId() { return assignmentId; }
    public boolean getPassed() { return passed; }
    public Long getScore() { return score; }
    public Date getDate() { return date; }


    public void setClassName(String s) { this.className = s; }
    public void setUser(User i) { this.user = i; }
    public void setText(String s) { this.text = s; }
    public void setAssignmentId(Long a) { this.assignmentId = a; }
    public void setPassed(boolean value) { this.passed = value; }
    public void setScore(Long score) { this.score = score; }
    public void setDate(Date date) { this.date = date; }

}
