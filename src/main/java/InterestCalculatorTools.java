/**
 * @author fenggami
 * 功能：为了便于校验诉讼后台的利息计算以及逾期管理费等数据是否正确
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 功能：计算利息公式（注意：逾期时，借款天数t+1，需要加上宽限期）
 * @return interest 利息
 */
public class InterestCalculatorTools {
    public static double Calculator(double m, double i, int t) {
        double interest;
        if (m == 0) return 0.005;
        if (t <= 365) {
            interest = m * i * t / 36500 + m-m;
        } else {
            interest = m * Math.pow((i / 100 + 1), t / 365.0)-m;
        }
        return interest;
    }

    /**
     * 功能：计算逾期罚息
     * @param overdueDays 逾期天数
     * @return  overdueInterest  逾期罚息 （overdueInterest=逾期天数*未偿还本金*24%/365)
     */
    public static double OverdueInterestCalculator(double m,int overdueDays){
        double overdueInterest;
        if (m == 0) return 0;
        double pInterest=Double.parseDouble(String.format("%.2f", m*0.24/365-0.005));
        overdueInterest =Double.parseDouble(String.format("%.2f", overdueDays*pInterest));
        return overdueInterest;
    }

    /**
     * 功能：计算逾期罚息
     * @param overdueDays 逾期天数
     * @return  totalAmount 应还总额
     * @return  overdueFees 基础逾期管理费 ＝ 0.1% *（截至当天的未还本、利息、罚息）
     * @return  specialOverdueFees  特殊逾期管理费 = 20% * （截至当天的未还本、利息、罚息、基础管理费（包括当天新增））
     * @return totalOverdueFees =overdueFees+specialOverdueFees 总逾期管理费
     */
    public static void AllOverdueFeesCalculator(double m,double rate,int overdueDays,String lendOutDate,String endDate) throws ParseException {
        double overdueFees,specialOverdueFees,totalOverdueFees;
        overdueFees = Double.parseDouble(String.format("%.2f",OverdueFeesCalculator( m,rate,overdueDays,lendOutDate,endDate)));
        if (overdueDays<0) {
            overdueFees = 0 ;
            specialOverdueFees =0 ;
            totalOverdueFees = 0;
            System.out.println("基础逾期管理费："+overdueFees);
            System.out.println("特殊逾期管理费："+specialOverdueFees);
            System.out.println("总逾期管理费："+totalOverdueFees);
        } else {
            if(overdueDays<16){
                specialOverdueFees =0 ;
                totalOverdueFees = overdueFees +specialOverdueFees;
                System.out.println("基础逾期管理费："+overdueFees);
                System.out.println("特殊逾期管理费："+specialOverdueFees);
                System.out.println("总逾期管理费："+totalOverdueFees);

            } else if(overdueDays<76){
                specialOverdueFees = Double.parseDouble(String.format("%.2f",TotalFeesCalculator( m,rate,15,lendOutDate,endDate)*0.2-0.005));
                totalOverdueFees = overdueFees +specialOverdueFees;
                System.out.println("基础逾期管理费："+overdueFees);
                System.out.println("第一次特殊逾期管理费："+specialOverdueFees);
                System.out.println("总逾期管理费："+totalOverdueFees);
            }else {
                specialOverdueFees = Double.parseDouble(String.format("%.2f",TotalFeesCalculator( m,rate,76,lendOutDate,endDate)*0.3-0.005));
                totalOverdueFees = overdueFees +specialOverdueFees;
                System.out.println("基础逾期管理费："+overdueFees);
                System.out.println("第二次特殊逾期管理费："+specialOverdueFees);
                System.out.println("总逾期管理费："+totalOverdueFees);
            }
        }
    }

    public static double OverdueFeesCalculator(double m,double rate,int overdueDays,String lendOutDate,String endDate) throws ParseException {
        double overdueFees =0;
        double totalAmount,overdueInterest;
        int lenddays = daysBetween(lendOutDate,endDate)+1;
        int k = 0;
        double interest = Double.parseDouble(String.format("%.2f",(Calculator(m,rate,lenddays)-0.005)));
        if (overdueDays<0) {
            overdueFees =0;
        }
        while (overdueDays>0 ){
                k ++;
                overdueDays--;
                overdueInterest = OverdueInterestCalculator(m,k);
                totalAmount = interest + m +overdueInterest;
                String temp = String.format("%.2f",(0.001*totalAmount-0.005));
                overdueFees = overdueFees+Double.parseDouble(temp);
            }
        return overdueFees;
    }

    public static double TotalFeesCalculator(double m,double rate,int overdueDays,String lendOutDate,String endDate) throws ParseException {
        double overdueFees =0;
        double totalAmount,overdueInterest;
        double totalFees = 0;
        int lenddays = daysBetween(lendOutDate,endDate)+1;
        int k = 0;
        double interest = Double.parseDouble(String.format("%.2f",(Calculator(m,rate,lenddays)-0.005)));
        if (overdueDays<0) {
            overdueFees =0;
        }
        while (overdueDays>0 ){
            k ++;
            overdueDays--;
            overdueInterest = OverdueInterestCalculator(m,k);
            totalAmount = interest + m +overdueInterest;
            overdueFees = overdueFees + Double.parseDouble(String.format("%.2f",(0.001*totalAmount-0.005)));
            totalFees = totalAmount + overdueFees;
        }
        return totalFees;
    }

    /**
     * 字符串的日期格式的计算
     *
     * @param smdate 开始时间 格式："2016-08-22"
     * @param bdate  截止时间 格式："2016-08-22"
     * @return 相差天数
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

}
