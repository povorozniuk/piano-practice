package com.povorozniuk.backend.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Table(name = "practice_month")
@Entity
public class PracticeMonth {

    @Id
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @Column(name = "num_of_pressed_keys", nullable = false)
    private Integer numOfPressedKeys;

    @Column(name = "practice_minutes", nullable = false)
    private Integer practiceMinutes;

    @Column(name = "practice_hours", nullable = false)
    private String practiceHours;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
