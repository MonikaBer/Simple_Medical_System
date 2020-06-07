package project;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Helper {

    public static void showWarningDialog(JFrame window, String str) {
        JOptionPane.showMessageDialog(window, str, "Uwaga", JOptionPane.WARNING_MESSAGE);
    }

    public static int showConfirmDialog(String str) {
        return JOptionPane.showConfirmDialog(null, str, "Pytanie", JOptionPane.YES_NO_OPTION);
    }

    public static String dateToString(Date date) {
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (day < 10)
            stringBuilder.append("0");
        stringBuilder.append(day);
        stringBuilder.append(".");
        if (month < 10)
            stringBuilder.append("0");
        stringBuilder.append(month);
        stringBuilder.append(".");
        stringBuilder.append(year);
        return stringBuilder.toString();
    }

    public static boolean ifDatesOrderIsCorrect(Date from, Date to) {
        Calendar calendarFrom = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        calendarFrom.setTime(from);
        Calendar calendarTo = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        calendarTo.setTime(to);
        return calendarFrom.before(calendarTo);
    }
}
