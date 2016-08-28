import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/8/24.
 */
public class InterestCalculatorTest {
    @Test
    public void testdaysBetween() throws ParseException {
        InterestCalculatorTools interestCal = new InterestCalculatorTools();
        Scanner date = new Scanner(System.in);
        String lendOutDate = date.nextLine();
        String endDate = date.nextLine();
        Date today = new Date();
        int days = interestCal.daysBetween(lendOutDate,endDate);
        System.out.println(days);
    }
}
