package ru.bolgov.restart.schedule;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Test class {@link Schedule}
     */
    @Test
    public void shouldAnswerWithTrue(){
        BigDecimal creditAmount = new BigDecimal(1500000);
        BigDecimal percentRate = new BigDecimal(15);
        int term = 120;
        Schedule schedule = new Schedule(creditAmount, term, percentRate, true);
        System.out.println(schedule.calculateTotalPercent());
        System.out.println(schedule);
    }
}
