package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
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
}
