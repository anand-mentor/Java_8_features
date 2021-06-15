package com.java8.dateapi;

import static java.time.temporal.TemporalAdjusters.dayOfWeekInMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.next;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DateApiAssignments {

	public static void main(String[] args) {
//1.	Find out the day exactly after 15 days from today’s date.
findDayAfter15Days();

//2.	Find out how much time is required from now for 12 PM.
findTimeFor12PM();

//3.	Find out how many days are required to reach 29-Feb from today’s date.
timeToReach29Feb();

//4.	Let user enter month & year. Find out total number of holidays within that month. Note, all Sundays and 2nd, 4th Saturday remains holiday. (Write custom TemoralAdjuster)
findTotalHolidaysDuringMonthYear();

//5.	Print the current time in different time zones.
printCurrentTimeInDifferentTimeZones();

//6.	Print the current date with different locales.
printCurrentDateWithDifferentLocales();
	}

	private static void findDayAfter15Days() {
		LocalDate currentDate = LocalDate.now();
		LocalDate dateAfter15Days = currentDate.plusDays(15);
		System.out.println("Date after 15 days: " + dateAfter15Days);
	}

	private static void findTimeFor12PM() {
		LocalTime currentTime = LocalTime.now();
		LocalTime noon = LocalTime.of(12, 0, 0);
		Duration duration = Duration.between(currentTime, noon);
		Duration requiredDuration = null;

		if(duration.getSeconds() < 0) { //12PM already passed
			LocalTime night12AM = LocalTime.of(23, 59);
			Duration tempDuration = Duration.between(currentTime, night12AM);
			requiredDuration = tempDuration.plus(Duration.ofHours(12));
			requiredDuration = requiredDuration.plus(Duration.ofMinutes(1));
		}
		else if(duration.getSeconds() > 0) { //Current time before 12PM
			requiredDuration = duration;
		}
		else { //Current time is 12PM itself
			requiredDuration = Duration.ofSeconds(0);
		}
		System.out.println("requiredDuration = " + requiredDuration);
	}

	private static void printCurrentDateWithDifferentLocales() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		
		System.out.println("Formatted date: " + currentDate.format(formatter));
		System.out.println("French Formatted date: " + formatter.withLocale(Locale.FRANCE));
	}
	
	private static void printCurrentTimeInDifferentTimeZones() {
		Set<String> timeZonesSet = ZoneId.getAvailableZoneIds();
		timeZonesSet.forEach((String timeZone)->System.out.println(timeZone + " - " + LocalTime.now(ZoneId.of(timeZone))));
	}
	
	private static void findTotalHolidaysDuringMonthYear() {
		class NextHoliday implements TemporalAdjuster {

			@Override
			public Temporal adjustInto(Temporal temporal) {
				LocalDate localDate = LocalDate.from(temporal);
				if(localDate.equals(localDate.with(lastDayOfMonth()))) {
					return null;
				}
				if(localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					return localDate.plusDays(1);
				}
				else {
					LocalDate firstDayOfMonthDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
			        LocalDate secondSaturdayDate = firstDayOfMonthDate.with(
			                  dayOfWeekInMonth(2, DayOfWeek.SATURDAY));
			        LocalDate fourthSaturdayDate = firstDayOfMonthDate.with(
			                  dayOfWeekInMonth(4, DayOfWeek.SATURDAY));
					LocalDate saturdayDate = LocalDate.from(next(DayOfWeek.SATURDAY).adjustInto(localDate));
					if(saturdayDate.equals(secondSaturdayDate) || saturdayDate.equals(fourthSaturdayDate)) {
						LocalDate sundayDate = saturdayDate.with(next(DayOfWeek.SUNDAY));
						if(sundayDate.getMonthValue()==localDate.getMonthValue())
							return sundayDate;
						else
							return null;
					}
					else {
						return saturdayDate.getMonthValue()!=localDate.getMonthValue()?null:saturdayDate;
					}
				}
			}
			
		}
		int month = 11;
		int year = 2019;
		LocalDate myLocalDate = LocalDate.of(year, month, 1);
		List<LocalDate> holidayList = new ArrayList<>();
		if(myLocalDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
				|| myLocalDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			holidayList.add(myLocalDate);
		}
		while(myLocalDate.getMonthValue()==month){
			myLocalDate = myLocalDate.with(new NextHoliday());
			if(myLocalDate==null)
				break;
			holidayList.add(myLocalDate);
		}
		holidayList.stream().forEach(System.out::println);
	}
	
	private static void timeToReach29Feb() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonth().getValue();
		if(year%4 == 0) { //Current year is leap year
			if(month == 1) { //January
				LocalDate _29FebDate = LocalDate.of(year, 2, 29);
				System.out.println(Period.between(now, _29FebDate));
			}
		}
		else { //Current year is NOT leap year
			int leapYear = year + (4 - year%4);
			LocalDate _29FebLeapDate = LocalDate.of(leapYear, 2, 29);
			System.out.println(Period.between(now, _29FebLeapDate));
		}
	}

}
