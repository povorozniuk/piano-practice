package com.povorozniuk.pianomidilistener.util;


import com.povorozniuk.pianomidilistener.model.Note;

import java.util.HashMap;
import java.util.Map;

public class NoteUtil {
    public static final char[] NOTES = {'C','D','E','F','G','A','B'};
    private static Map<Integer,String> keyToNoteMapping;

    public static Map<Integer, String> get(){
        return keyToNoteMapping;
    }

    public static String getNoteValue(Integer keyNumber){
        if (keyNumber < 21 || keyNumber > 108){
            return null;
        }
        return keyToNoteMapping.get(keyNumber);
    }

    public static void setKeyToNoteMapping(Map<Integer,String> mapping){
        if (keyToNoteMapping == null){
            keyToNoteMapping = mapping;
        }
    }

    /**
     *
     * @param keyNumberStart first key number on the keyboard. Kawai ES-110 starts with A0. Key number passed from the piano is 21
     * @param keyNumberEnd last key number on the keyboard. Kawai ES-110 ends with C8. Key number passed from the piano is 108
     * @param firstNoteName upper case note value e.g. A
     * @param octaveStart e.g. 0
     * @param isSharp true/false
     * @return key to note mapping e.g: 1:A0 2:A0# 3:B0 4:C1 5:C1# etc
     */
    public static Map<Integer, String> generateKeyToNoteMapping(int keyNumberStart, int keyNumberEnd, char firstNoteName, int octaveStart, boolean isSharp){
        Map<Integer, String> keyToNoteMapping = new HashMap<>();
        Note note = new Note(firstNoteName,isSharp,octaveStart);
        for (int i = keyNumberStart; i <= keyNumberEnd; i++){
            if (i == keyNumberStart){
                keyToNoteMapping.put(i, note.toString());
            } else{
                note = note.getNextNote();
                keyToNoteMapping.put(i, note.toString());
            }
        }
        return keyToNoteMapping;
    }

}
