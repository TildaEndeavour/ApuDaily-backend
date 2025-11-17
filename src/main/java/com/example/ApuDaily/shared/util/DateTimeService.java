package com.example.ApuDaily.shared.util;

import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {
  public TimeZone getDatabaseTimezone() {
    return TimeZone.getTimeZone("UTC");
  }

  public LocalDate getCurrentDatabaseDate() {
    return LocalDate.now(getDatabaseTimezone().toZoneId());
  }

  public int getCurrentDatabaseDayOfMonth() {
    return LocalDate.now(getDatabaseTimezone().toZoneId()).getDayOfMonth();
  }

  public DayOfWeek getCurrentDatabaseDayOfWeek() {
    return LocalDate.now(getDatabaseTimezone().toZoneId()).getDayOfWeek();
  }

  public int getCurrentDatabaseMonth() {
    return LocalDate.now(getDatabaseTimezone().toZoneId()).getMonthValue();
  }

  public int getCurrentDatabaseYear() {
    return LocalDate.now(getDatabaseTimezone().toZoneId()).getYear();
  }

  public ZonedDateTime getCurrentDatabaseZonedDateTime() {
    TimeZone timeZone = getDatabaseTimezone();
    ZoneId zoneId = timeZone.toZoneId();
    Calendar calendar = new GregorianCalendar();
    calendar.setTimeZone(timeZone);
    long currentTimeMillis = calendar.getTimeInMillis();
    return ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(currentTimeMillis), zoneId);
  }

  public ZonedDateTime convertToNewZone(ZonedDateTime sourceDateTime, TimeZone targetZone) {
    return sourceDateTime.withZoneSameInstant(targetZone.toZoneId());
  }

  public LocalDate convertToDBDate(LocalDate sourceDate, String userTimezoneName) {
    ZoneId userZone = TimeZone.getTimeZone(userTimezoneName).toZoneId();
    return sourceDate
        .atTime(LocalTime.NOON)
        .atZone(userZone)
        .withZoneSameInstant(this.getDatabaseTimezone().toZoneId())
        .toLocalDate();
  }

  public LocalDate convertToUserDate(LocalDate dbSourceDate, String userTimezoneName) {
    ZoneId userZone = TimeZone.getTimeZone(userTimezoneName).toZoneId();
    return dbSourceDate
        .atTime(LocalTime.NOON)
        .atZone(this.getDatabaseTimezone().toZoneId())
        .withZoneSameInstant(userZone)
        .toLocalDate();
  }

  public LocalDateTime convertToUserDateTime(
      LocalDateTime dbSourceDateTime, String userTimezoneName) {
    ZoneId userZone = TimeZone.getTimeZone(userTimezoneName).toZoneId();
    return dbSourceDateTime
        .atZone(this.getDatabaseTimezone().toZoneId())
        .withZoneSameInstant(userZone)
        .toLocalDateTime();
  }

  public void displayTimeZone() {

    for (String timeZoneId : TimeZone.getAvailableIDs()) {

      TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
      long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
      long minutes =
          TimeUnit.MILLISECONDS.toMinutes(timeZone.getRawOffset())
              - TimeUnit.HOURS.toMinutes(hours);
      minutes = Math.abs(minutes); // avoid -4:-30 issue

      if (timeZoneId.contains("Europe")
          || timeZoneId.contains("Canada")
          || timeZoneId.contains("US")) {
        String timeZoneText = "";
        if (hours > 0) {
          timeZoneText = String.format("('%s', 'GMT+%d:%02d'),", timeZone.getID(), hours, minutes);
        } else {
          timeZoneText = String.format("('%s', 'GMT%d:%02d'),", timeZone.getID(), hours, minutes);
        }

        System.out.println(timeZoneText);
      }
    }
  }
}
