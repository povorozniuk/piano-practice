package com.povorozniuk.pianomidilistener.model;

import com.povorozniuk.pianomidilistener.util.NoteUtil;
import lombok.Data;

@Data
public class Note {
    char noteName;
    boolean canBeSharp;
    boolean isSharp;
    int octave;

    public Note(char noteName, boolean isSharp, int octave){
        this.noteName = noteName;
        this.isSharp = isSharp;
        this.octave = octave;
        this.canBeSharp = setCanBeSharp();
    }

    private boolean setCanBeSharp(){
        return this.noteName != 'E' && this.noteName != 'B';
    }

    public Note getNextNote(){
        boolean isNextNoteSharp = isNextNoteSharp(this);
        char nextNoteName = this.noteName;
        if (!isNextNoteSharp){
            nextNoteName = getNextNoteName(this);
        }
        int nextNoteOctave = getNextNoteOctave(this);
        return new Note(nextNoteName, isNextNoteSharp, nextNoteOctave);
    }

    private char getNextNoteName(Note note) {
        if (isNextNoteSharp(note)){
            return note.getNoteName();
        }
        int length = NoteUtil.NOTES.length;
        for (int i = 0; i < NoteUtil.NOTES.length; i++){
            if (NoteUtil.NOTES[i] == note.noteName){
                if (i == length - 1){
                    return NoteUtil.NOTES[0];
                }else{
                    return NoteUtil.NOTES[i + 1];
                }
            }
        }
        throw new IllegalArgumentException("Note: " + note.getNoteName() + " is invalid");
    }

    private int getNextNoteOctave(Note note) {
        if (note.getNoteName() == 'B'){
            return note.getOctave() + 1;
        }else{
            return note.getOctave();
        }
    }

    private boolean isNextNoteSharp(Note currentNote){
        if (!currentNote.isCanBeSharp()){
            return false;
            //cannot be sharp -> get next
        }else if(currentNote.isCanBeSharp() && !currentNote.isSharp()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(noteName) +
                octave +
                (isSharp ? "#" : "");
    }

}
