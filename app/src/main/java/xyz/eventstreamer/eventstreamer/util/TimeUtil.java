package xyz.eventstreamer.eventstreamer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeUtil {
    private static final String TAG = TimeUtil.class.getSimpleName();

    public static String generateCurrentTimeAndDateFromMillis(long millis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return new SimpleDateFormat("d. MMM yyyy HH:mm", Locale.getDefault())
                .format(calendar.getTime());
    }

    public static boolean calculateMillisecondsIfLessThanOneDay(long millis) {
        long calculatedMillis = System.currentTimeMillis() - millis;
        return calculatedMillis < 86400000;
    }

    public static String generateCurrentTimeFromMillis(long millis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return new SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(calendar.getTime());
    }

    public static String generateBackendDateDateFromMillis(long millis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(calendar.getTime());
    }

    public static Calendar generateCalendarFromBackendDate(String backendDateString) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        try {
            if (backendDateString == null){
                backendDateString = "0";
            }
            calendar.setTime(simpleDateFormat.parse(backendDateString));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateDateStringFromBackendDate(String backendDateString) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        try {
            if (backendDateString == null){
                backendDateString = "0";
            }
            calendar.setTime(simpleDateFormat.parse(backendDateString));
            return calendar.get(Calendar.DAY_OF_MONTH) + ". " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault() ) + " " + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateCurrentSimpleDateFromBackendDate(String backendDateString) {
        if (backendDateString == null || backendDateString.isEmpty()) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(backendDateString);
            return new SimpleDateFormat("d. m. yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("d. m. yyyy", Locale.getDefault()).format(new Date());
    }

    public static Calendar generateCalendarFromMilis(long milliseconds){
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliseconds);
        return calendar;
    }

    public static String generateTimeDateForDashboard(String backendDateString) {
        if (backendDateString == null || backendDateString.isEmpty()) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(backendDateString);
            return new SimpleDateFormat("d. MMM yyyy HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "/";
    }

    public static String generateTimeDate(String backendDateString, String outputPattern){
        if (backendDateString == null || backendDateString.isEmpty()) return "";

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(backendDateString);
            return new SimpleDateFormat(outputPattern, Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.getDefault());
        try {
            Date date = simpleDateFormat2.parse(backendDateString);
            return new SimpleDateFormat(outputPattern, Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDateFromMilisecondsWithTimezone(backendDateString, outputPattern);
    }

    public static Date getDateFromString(String backendDateString) {
        if (backendDateString == null || backendDateString.isEmpty()) return new Date();

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());
        try {
            return simpleDateFormat.parse(backendDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.getDefault());
        try {
            return simpleDateFormat2.parse(backendDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getServerTimestamp(long timestamp) {
        return "/Date("+ String.valueOf(timestamp) +")/";
    }

    public static long getMillisecondsFromDateString(String date) {
        Pattern pattern = Pattern.compile("\\/Date\\(([0-9]+)([\\+\\-]{1})[0-9]{1}([0-9]{1})[0-9]+\\)\\/"); //TODO fix regex
        Matcher matcher = pattern.matcher(date);
        long miliseconds = 0;
        String prefix = "";
        int timezone = 0;
        while (matcher.find()) {
            miliseconds = Long.parseLong(matcher.group(1));
            prefix = matcher.group(2);
            timezone = Integer.parseInt(matcher.group(3));
        }
        /*if (prefix.equals("+")) {
            miliseconds += (timezone * 3_600_000);
        } else if (prefix.equals("-")) {
            miliseconds -= (timezone * 3_600_000);
        }*/
        return miliseconds;
    }

    public static String getDateFromMilisecondsWithTimezone(String dateString, String pattern) {
        long date = getMillisecondsFromDateString(dateString);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        return simpleDateFormat.format(new Date(date));
    }

    public static String getFormattedDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }
}
