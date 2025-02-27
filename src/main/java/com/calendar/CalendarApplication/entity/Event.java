package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @Getter
    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Column(name = "date", nullable = false)
    private Date date;

    public Event(User user, String title, String description, Date date) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Event() {}
}
