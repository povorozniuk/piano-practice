package com.povorozniuk.pianomidilistener.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class NoteUtilTest {
    @Test
    public void test_generateKeyToNoteMapping_88_notes(){
        NoteUtil noteUtil = new NoteUtil();
        Map<Integer, String> map = noteUtil.generateKeyToNoteMapping(21, 108, 'G', 0, false);
        Assertions.assertThat(map).hasSize(88);
        Assertions.assertThat(map.get(21)).isEqualTo("G0");
        Assertions.assertThat(map.get(73)).isEqualTo("B4");
        Assertions.assertThat(map.get(108)).isEqualTo("A7#");
        System.out.println(JsonUtil.serialize(map));
    }

    @Test
    public void test_generateKeyToNoteMapping_15_notes(){
        NoteUtil noteUtil = new NoteUtil();
        Map<Integer, String> map = noteUtil.generateKeyToNoteMapping(1, 15, 'A', 1, true);
        Assertions.assertThat(map).hasSize(15);
        Assertions.assertThat(map.get(1)).isEqualTo("A1#");
        Assertions.assertThat(map.get(2)).isEqualTo("B1");
        Assertions.assertThat(map.get(3)).isEqualTo("C2");
        Assertions.assertThat(map.get(4)).isEqualTo("C2#");
        Assertions.assertThat(map.get(5)).isEqualTo("D2");
        Assertions.assertThat(map.get(6)).isEqualTo("D2#");
        Assertions.assertThat(map.get(7)).isEqualTo("E2");
        Assertions.assertThat(map.get(8)).isEqualTo("F2");
        Assertions.assertThat(map.get(9)).isEqualTo("F2#");
        Assertions.assertThat(map.get(10)).isEqualTo("G2");
        Assertions.assertThat(map.get(11)).isEqualTo("G2#");
        Assertions.assertThat(map.get(12)).isEqualTo("A2");
        Assertions.assertThat(map.get(13)).isEqualTo("A2#");
        Assertions.assertThat(map.get(14)).isEqualTo("B2");
        Assertions.assertThat(map.get(15)).isEqualTo("C3");
        System.out.println(JsonUtil.serialize(map));
    }
}
