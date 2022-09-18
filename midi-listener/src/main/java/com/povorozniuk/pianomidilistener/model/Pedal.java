package com.povorozniuk.pianomidilistener.model;

import lombok.Data;

@Data
public class Pedal {

    private InteractionType interactionType = InteractionType.pedal;
    private Integer status;
    private Integer pressure;

    public Pedal(Integer status, Integer pressure) {
        this.status = status;
        this.pressure = pressure;
    }
}
