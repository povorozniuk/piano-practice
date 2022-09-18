package com.povorozniuk.pianomidilistener.model;


import com.povorozniuk.pianomidilistener.util.NoteUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Key {

    private InteractionType interactionType = InteractionType.key;
    private String timeStamp;
    private Integer status;
    private Integer keyNumber;
    private Integer pressure;
    private String noteValue;

    public Key(Integer status, Integer keyNumber, Integer pressure, String timeStamp){
        this.status = status;
        this.keyNumber = keyNumber;
        this.pressure = pressure;
        this.noteValue = NoteUtil.getNoteValue(keyNumber);
        this.timeStamp = timeStamp;
    }

}
