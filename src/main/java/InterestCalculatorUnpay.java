/**
 * @author fenggami
 * 功能：为了便于校验诉讼后台的利息计算以及逾期管理费等数据是否正确
 *       截止至今日仍未还款的
 * @param overdueFees 基础逾期管理费
 * @param specialOverdueFees  特殊逾期管理费
 * @param overdueInterest  逾期罚息
 * @param totalAmount 应还总额（本金+利息+罚息）
 * @totalOverdueFees =overdueFees+specialOverdueFees 总逾期管理费
 */
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
public class InterestCalculatorUnpay {
    public static void main(String args[]) throws ParseException {
        InterestCalculatorTools tools = new InterestCalculatorTools();
        Scanner date = new Scanner(System.in);
        String lendOutDate = date.nextLine();
        String endDate = date.nextLine();
        Double principal = date.nextDouble();
        Double interestRate = date.nextDouble();
        //借款天数
        int lenddays = tools.daysBetween(lendOutDate,endDate)+1;
        Double interest = Double.parseDouble(String.format("%.2f",tools.Calculator(principal,interestRate,lenddays)-0.005));
        System.out.println("利息为："+interest);

        SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" );
        String today = sdf.format(new Date());
        //逾期天数 overduedays
        int overduedays = tools.daysBetween(endDate,today)-1;
        Double overdueInterest =Double.parseDouble(String.format("%.2f",tools.OverdueInterestCalculator(principal,overduedays)-0.005));
        System.out.println("逾期罚息："+overdueInterest);
        System.out.println("逾期天数："+overduedays);
        //应还总额（本金+利息+罚息）
        Double totalAmount = interest + overdueInterest + principal;
        System.out.println("应还总额（本金+利息+罚息）："+totalAmount);

        tools.AllOverdueFeesCalculator(principal,interestRate,overduedays,lendOutDate,endDate);

    }

}