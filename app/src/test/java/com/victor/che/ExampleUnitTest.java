package com.victor.che;

import com.victor.che.util.DateUtil;
import com.victor.che.util.MathUtil;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testIncreaseRate() throws Exception {
        System.out.print(MathUtil.calcIncreaseRate(4, 3));
    }

    @Test
    public void testDays() throws Exception {
        Date createDate = DateUtil.getDateByFormat("2017-02-21 23:00:00", DateUtil.YMDHMS);
        int days = getDays(createDate.getTime(), System.currentTimeMillis());
        System.out.print(days);
    }


    public static int getDays(long t1, long t2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t1);

        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(t2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1 + 1;
    }
}