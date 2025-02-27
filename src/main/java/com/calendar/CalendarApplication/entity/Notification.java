package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(referencedColumnName = "id")
    private Event eventId;

    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User userId;

    @Getter
    @Setter
    @Column(name = "has_seen", nullable = false)
    private boolean hasSeen;

    public Notification(Event eventId, User userId, boolean hasSeen) {
        this.eventId = eventId;
        this.userId = userId;
        this.hasSeen = hasSeen;
    }

    public Notification() {}
}
