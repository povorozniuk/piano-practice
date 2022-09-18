package com.povorozniuk.backend.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Table(name = "action_history")
@Entity
public class ActionHistory {
    @Id
    @Column(name = "action_id", nullable = false)
    private Long actionId;

    private String interactionType;

    private LocalDateTime time;

    private Integer pressure;

    private String noteValue;

    private Integer keyNumber;

}
