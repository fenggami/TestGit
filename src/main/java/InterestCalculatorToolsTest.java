import java.text.ParseException;
import java.util.Scanner;
/**
 * Created by Administrator on 2016/8/24.
 */
public class InterestCalculatorToolsTest {
    public static  void main(String args[]) throws ParseException {
        InterestCalculatorTools interestTool = new InterestCalculatorTools();
        Scanner date = new Scanner(System.in);
        String lendOutDate = date.nextLine();
        String endDate = date.nextLine();
        int overduedays = interestTool.daysBetween(lendOutDate,endDate);
        System.out.println(overduedays);
    }
}
