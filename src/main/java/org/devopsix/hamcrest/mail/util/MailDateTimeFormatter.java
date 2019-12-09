package org.devopsix.hamcrest.mail.util;

import static java.time.chrono.IsoChronology.INSTANCE;
import static java.time.format.ResolverStyle.SMART;
import static java.time.format.SignStyle.NOT_NEGATIVE;
import static java.time.format.TextStyle.SHORT;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * This class supplies a date time formatter relaxed enough to read both,
 * RFC 822 (two-digit year) and RFC 2822/1123 (four-digit year) notation.
 * Furthermore, it allows a trailing time zone name in parentheses after
 * the zone offset.
 * 
 * @author devopsix
 *
 */
public final class MailDateTimeFormatter {

    private MailDateTimeFormatter() {}

    private static final Map<Long, String> dayNames() {
        Map<Long, String> dayNames = new HashMap<>();
        dayNames.put(1L, "Mon");
        dayNames.put(2L, "Tue");
        dayNames.put(3L, "Wed");
        dayNames.put(4L, "Thu");
        dayNames.put(5L, "Fri");
        dayNames.put(6L, "Sat");
        dayNames.put(7L, "Sun");
        return dayNames;
    }

    private static final Map<Long, String> monthNames() {
        Map<Long, String> monthNames = new HashMap<>();
        monthNames.put(1L, "Jan");
        monthNames.put(2L, "Feb");
        monthNames.put(3L, "Mar");
        monthNames.put(4L, "Apr");
        monthNames.put(5L, "May");
        monthNames.put(6L, "Jun");
        monthNames.put(7L, "Jul");
        monthNames.put(8L, "Aug");
        monthNames.put(9L, "Sep");
        monthNames.put(10L, "Oct");
        monthNames.put(11L, "Nov");
        monthNames.put(12L, "Dec");
        return monthNames;
    }
    
    // We don't want these names to depend in the user/app locale.
    // English locale always applies to date headers in mails.
    private static final Map<Long, String> DAY_NAMES = dayNames();
    private static final Map<Long, String> MONTH_NAMES = monthNames();
    
    public static final DateTimeFormatter MAIL_DATE_TIME = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .parseLenient()
        .optionalStart()
        .appendText(DAY_OF_WEEK, DAY_NAMES)
        .appendLiteral(", ")
        .optionalEnd()
        .appendValue(DAY_OF_MONTH, 1, 2, NOT_NEGATIVE)
        .appendLiteral(' ')
        .appendText(MONTH_OF_YEAR, MONTH_NAMES)
        .appendLiteral(' ')
        .appendValueReduced(YEAR, 2, 4, LocalDate.of(1900, 01, 01))
        .appendLiteral(' ')
        .appendValue(HOUR_OF_DAY, 2)
        .appendLiteral(':')
        .appendValue(MINUTE_OF_HOUR, 2)
        .optionalStart()
        .appendLiteral(':')
        .appendValue(SECOND_OF_MINUTE, 2)
        .optionalEnd()
        .appendLiteral(' ')
        .appendOffset("+HHMM", "GMT")
        .optionalStart()
        .appendLiteral(' ')
        .appendLiteral('(')
        .appendZoneText(SHORT)
        .appendLiteral(')')
        .optionalEnd()
        .toFormatter()
        .withResolverStyle(SMART)
        .withChronology(INSTANCE);
}
