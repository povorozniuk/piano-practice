package com.povorozniuk.backend.model.games.util;

import com.povorozniuk.backend.entity.PracticeDay;
import com.povorozniuk.backend.entity.PracticeMonth;
import com.povorozniuk.backend.util.AsciTableUtil;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsciTableUtilTest {

    private final Random random = new Random();
    @Test
    public void practiceDay(){
        PracticeDay day = new PracticeDay();
        ReflectionTestUtils.setField(day, "practiceHours", "01:13");
        ReflectionTestUtils.setField(day, "day", LocalDate.now().minusDays(1));
        ReflectionTestUtils.setField(day,"practiceMinutes", 73);

        String result = AsciTableUtil.generateAsciTable(day);
        System.out.println(result);
    }

    @Test
    public void practiceDayList(){
        List<PracticeDay> list = new ArrayList<>();

        for (int i=7;i>0;i--){
            PracticeDay day = new PracticeDay();
            ReflectionTestUtils.setField(day, "practiceHours", "01:0" + i);
            ReflectionTestUtils.setField(day, "day", LocalDate.now().minusDays(i));
            list.add(day);
            ReflectionTestUtils.setField(day,"practiceMinutes", 60 + (i-1));
        }
        String result = AsciTableUtil.generateAsciTable(list);
        System.out.println(result);
    }

    @Test
    public void practiceMonth(){
        PracticeMonth month = new PracticeMonth();
        ReflectionTestUtils.setField(month, "practiceHours", "01:00");
        ReflectionTestUtils.setField(month, "month", LocalDate.now().withDayOfMonth(1));
        ReflectionTestUtils.setField(month,"practiceMinutes", 60);
        String result = AsciTableUtil.generateAsciTable(month);
        System.out.println(result);
    }

    @Test
    public void practiceMonthList(){
        List<PracticeMonth> list = new ArrayList<>();
        for (int i=10;i>0;i--){
            PracticeMonth month = new PracticeMonth();
            ReflectionTestUtils.setField(month, "practiceHours", "01:0" + (i-1));
            ReflectionTestUtils.setField(month, "month", LocalDate.now().withDayOfMonth(1).minusMonths(i));
            ReflectionTestUtils.setField(month,"practiceMinutes", 60 + (i-1));
            list.add(month);
        }
        String result = AsciTableUtil.generateAsciTable(list);
        System.out.println(result);
    }

}
