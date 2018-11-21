package com.projector.management.server.model;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test the Duration class.
 * @author Qi Wang
 */
public class DurationTest {

    /**
     * test the function that checks if there is an overlap between two intervals
     */
    @Test
    public void checkDurationOverlap() {
        Duration testDuration1 = DurationTestUtil.getTestDuration("2018-11-20 08:00:00", "2018-11-20 12:00:00");
        Duration testDuration2 = DurationTestUtil.getTestDuration("2018-11-20 10:00:00", "2018-11-20 12:00:00");
        assertTrue(testDuration1.checkDurationOverlap(testDuration2));

        Duration testDuration3 = DurationTestUtil.getTestDuration("2018-11-20 12:00:00", "2018-11-20 14:00:00");
        assertFalse(testDuration1.checkDurationOverlap(testDuration3));
    }

    /**
     * test the function that merge two list of durations
     */
    @Test
    public void mergeDurationLists() {
        Duration testDuration1 = DurationTestUtil.getTestDuration("2018-11-20 08:00:00", "2018-11-20 11:00:00");
        Duration testDuration2 = DurationTestUtil.getTestDuration("2018-11-20 10:00:00", "2018-11-20 12:00:00");

        List<Duration> testList1 = new ArrayList<>();
        testList1.add(testDuration1);
        List<Duration> testList2 = new ArrayList<>();
        testList2.add(testDuration2);

        List<Duration> res = Duration.mergeDurationLists(testList1, testList2);
        assertEquals(res.get(0).getStartTime(), testDuration1.getStartTime());
        assertEquals(res.get(0).getEndTime(), testDuration2.getEndTime());
    }
}