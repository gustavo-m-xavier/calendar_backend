package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(referencedColumnName = "id")
    private Event eventId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User userId;

    @Column(name = "has_seen", nullable = false)
    private boolean hasSeen;

    public Notification(String title, String description, Event eventId, User userId, boolean hasSeen) {
        this.title = title;
        this.description = description;
        this.eventId = eventId;
        this.userId = userId;
        this.hasSeen = hasSeen;
    }

    public Notification() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public boolean isHasSeen() {
        return hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }
}
