package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(referencedColumnName = "id")
    private Event eventId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User userId;

    @Column(name = "has_seen", nullable = false)
    private boolean hasSeen;

    public Notification(Event eventId, User userId, boolean hasSeen) {
        this.eventId = eventId;
        this.userId = userId;
        this.hasSeen = hasSeen;
    }

    public Notification() {}

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
