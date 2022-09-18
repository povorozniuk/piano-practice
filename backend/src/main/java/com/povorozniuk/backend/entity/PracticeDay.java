package com.povorozniuk.backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Table(name = "practice_day")
@Entity
public class PracticeDay {

    @Id
    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "num_of_pressed_keys", nullable = false)
    private Integer numOfPressedKeys;

    @Column(name = "practice_minutes", nullable = false)
    private Integer practiceMinutes;

    @Column(name = "practice_hours", nullable = false)
    private String practiceHours;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
