package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimetableStopTest {


    @Test
    public void testThatNextAndPreviousStopCorrectWithoutChangedPath() {
        Arrival arrival = mockArrival("Nauen|Berlin Ostbahnhof", null);
        Departure departure = mockDeparture("Berlin-Spandau|Stendal Hbf|Wolfsburg Hbf|Hannover Hbf|Minden(Westf)|Bad Oeynhausen|Osnabr&#252;ck Hbf|Rheine|Bad Bentheim|Hengelo|Almelo|Deventer|Apeldoorn|Amersfoort Centraal|Hilversum|Amsterdam Centraal", null);

        TimetableStop stop = new TimetableStop(null, arrival, departure, null);

        assertThat(stop.getPreviousStop()).isEqualTo("Berlin Ostbahnhof");
        assertThat(stop.getNextStop()).isEqualTo("Berlin-Spandau");
    }

    @Test
    public void testThatNextAndPreviousStopCorrectWithChangedPath() {
        Arrival arrival = mockArrival("Berlin Ostbahnhof", "Berlin Hbf");
        Departure departure = mockDeparture("Berlin-Spandau|Stendal Hbf|Wolfsburg Hbf|Hannover Hbf|Minden(Westf)|Bad Oeynhausen|Osnabr&#252;ck Hbf|Rheine|Bad Bentheim|Hengelo|Almelo|Deventer|Apeldoorn|Amersfoort Centraal|Hilversum|Amsterdam Centraal", "Berlin Ostbahnhof");

        TimetableStop stop = new TimetableStop(null, arrival, departure, null);

        assertThat(stop.getPreviousStop()).isEqualTo("Berlin Hbf");
        assertThat(stop.getNextStop()).isEqualTo("Berlin Ostbahnhof");
    }

    @Test
    public void testThatNextStopNullForEmptyArrival() {
        Arrival arrival = mockArrival("", null);

        TimetableStop stop = new TimetableStop(null, arrival, null, null);

        assertThat(stop.getPreviousStop()).isNull();
    }

    @Test
    public void testThatNextStopNullForEmptyDeparture() {
        Departure departure = mockDeparture("", null);

        TimetableStop stop = new TimetableStop(null, null, departure, null);

        assertThat(stop.getNextStop()).isNull();
    }

    @ParameterizedTest
    @MethodSource("departureDateTimes")
    public void testThatHasDepartedAfter(String departureTime, LocalDateTime afterTime, boolean expectedResult) {
        Departure departure = new Departure(departureTime, null , departureTime, null, null, null);
        TimetableStop stop = new TimetableStop(null, null, departure, null);

        assertThat(stop.hasDepartedAfter(afterTime)).isEqualTo(expectedResult);
    }

    @Test
    public void testThatIsOlderThan() {
        LocalDateTime now = LocalDateTime.now();
        Departure departure = new Departure(getDatetimeFormat(now.minusHours(24)), null , getDatetimeFormat(now.minusHours(24)), null, null, null);
        TimetableStop stop = new TimetableStop(null, null, departure, null);

        assertThat(stop.isOlderThan(LocalDateTime.now().minusHours(23))).isTrue();
    }

    private static Stream<Arguments> departureDateTimes() {
        LocalDateTime now = LocalDateTime.now();

        return Stream.of(
                Arguments.of(getDatetimeFormat(now.minusMinutes(1)), now.plusMinutes(1), false),
                Arguments.of(getDatetimeFormat(now.minusMinutes(1)), now.minusMinutes(2), true),
                Arguments.of(getDatetimeFormat(now.minusMinutes(3)), now.minusMinutes(7), true),
                Arguments.of(getDatetimeFormat(now.minusMinutes(10)), now.minusMinutes(4), false),
                Arguments.of(getDatetimeFormat(now.plusMinutes(10)), now.plusMinutes(4), false)
        );
    }

    private static String getDatetimeFormat(LocalDateTime time) {
        StringBuilder builder = new StringBuilder();
        int month = time.getMonth().getValue();
        int day = time.getDayOfMonth();
        int hour = time.getHour();
        int minute = time.getMinute();
        return builder.append(time.getYear() - 2000)
                .append(prefixWithZeroIfBelow10(month))
                .append(prefixWithZeroIfBelow10(day))
                .append(prefixWithZeroIfBelow10(hour))
                .append(prefixWithZeroIfBelow10(minute))
                .toString();
    }

    private static String prefixWithZeroIfBelow10(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }

    private static Arrival mockArrival(String plannedPath, String changedPath) {
        Arrival mockArrival = mock(Arrival.class);
        when(mockArrival.getPlannedPath()).thenReturn(plannedPath);
        when(mockArrival.getChangedPath()).thenReturn(changedPath);
        return mockArrival;
    }

    private static Departure mockDeparture(String plannedPath, String changedPath) {
        Departure mockDeparture = mock(Departure.class);
        when(mockDeparture.getPlannedPath()).thenReturn(plannedPath);
        when(mockDeparture.getChangedPath()).thenReturn(changedPath);
        return mockDeparture;
    }

}