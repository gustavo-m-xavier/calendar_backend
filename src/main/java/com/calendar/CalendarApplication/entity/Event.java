package com.calendar.CalendarApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

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
