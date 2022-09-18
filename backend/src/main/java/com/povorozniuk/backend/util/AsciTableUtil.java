package com.povorozniuk.backend.util;

import com.povorozniuk.backend.entity.PracticeDay;
import com.povorozniuk.backend.entity.PracticeMonth;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AsciTableUtil {

    private static final String leftAlignFormat = "| %-10s | %-13s |%n";
    private static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final String DIVIDER = "+------------+---------------+%n";

    public static String generateAsciTable(final Object o){
        if (o instanceof PracticeDay){
            PracticeDay practiceDay = (PracticeDay) o;
            return String.format(DIVIDER) +
                    String.format("| Day        | Practice Time |%n") +
                    String.format(DIVIDER) +
                    String.format(leftAlignFormat, practiceDay.getDay(), practiceDay.getPracticeHours()) +
                    String.format(DIVIDER);
        }else if (o instanceof PracticeMonth){
            PracticeMonth practiceMonth = (PracticeMonth) o;
            return String.format(DIVIDER) +
                    String.format("| Month      | Practice Time |%n") +
                    String.format(DIVIDER) +
                    String.format(leftAlignFormat, practiceMonth.getMonth().format(monthFormat), practiceMonth.getPracticeHours()) +
                    String.format(DIVIDER);
        }else{
            throw new IllegalArgumentException("Class " + o.getClass() + " is not supported");
        }
    }

    public static String generateAsciTable(final List<?> list){
        if (list.isEmpty()) return "";
        Class<?> clazz = list.get(0).getClass();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(DIVIDER));
        if (clazz == PracticeDay.class){
            sb.append(String.format("| Day        | Practice Time |%n"));
        }else if (clazz == PracticeMonth.class){
            sb.append(String.format("| Month      | Practice Time |%n"));
        }else{
            throw new IllegalArgumentException("Class " + clazz + " is not supported");
        }
        sb.append(String.format(DIVIDER));

        int minutes = 0;
        for (Object o : list){
            if (o instanceof PracticeDay){
                PracticeDay practiceDay = (PracticeDay)o;
                minutes += practiceDay.getPracticeMinutes();
                sb.append(String.format(leftAlignFormat, practiceDay.getDay(), practiceDay.getPracticeHours()));
            }else if(o instanceof PracticeMonth){
                PracticeMonth practiceMonth = (PracticeMonth)o;
                minutes += practiceMonth.getPracticeMinutes();
                sb.append(String.format(leftAlignFormat, practiceMonth.getMonth().format(monthFormat), practiceMonth.getPracticeHours()));
            }else{
                throw new IllegalArgumentException("Class " + o.getClass() + " is not supported");
            }
        }
        sb.append(String.format(DIVIDER));
        int hr = minutes / 60;
        int min = minutes % 60;
        sb.append(String.format(leftAlignFormat, "Total", (hr < 10 ? "0" + hr : hr) + ":" + (min < 10 ? "0" + min : min)));
        sb.append(String.format(DIVIDER));
        return sb.toString();
    }
}
