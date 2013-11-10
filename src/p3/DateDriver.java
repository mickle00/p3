package p3;
import java.util.*;

/**
 *
 * @author Jens Bodal
 */
public class DateDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DateAD today;
        DateAD currentDate = new DateAD();
        today = new DateAD(); //today.DateAD();
        
        if (args.length == 0) {
            currentDate = new DateAD();
        }
        
        if (args.length == 1) {
            currentDate = new DateAD(
                    Short.parseShort(args[0]));    
        }
        if (args.length == 2) {
            currentDate = new DateAD(
                    Short.parseShort(args[0]),
                    Short.parseShort(args[1]));
        }
        if (args.length == 3) {
            currentDate = new DateAD(
                    Short.parseShort(args[0]),
                    Short.parseShort(args[1]),
                    Short.parseShort(args[2]));
        }
        
        System.out.println("Test new code");
        System.out.printf("The date is: %s%n", currentDate);
        System.out.printf("The day before is: %s%n", 
                currentDate.getYesterday(currentDate));
        System.out.printf("The day after is: %s%n", 
                currentDate.getTomorrow(currentDate));
        System.out.printf("Today is: %s%n", today);
        System.out.printf("%s is in the %s%n", currentDate, "<NOT DONE>");


       
        //test_getDayOfWeek_Literal();

    } //End main method

    private static void test_getDayOfWeek_Literal() {
        DateAD testDate = new DateAD();
        testDate = new DateAD((short)1 , (short)1, (short)2000);
        System.out.printf("1/1/2000: This should be \"Saturday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)2, (short)2000);
        System.out.printf("2/1/2000: This should be \"Tuesday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)2, (short)1900);
        System.out.printf("2/1/1900: This should be \"Thursday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)1, (short)1900);
        System.out.printf("1/1/1900: This should be \"Monday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)4 , (short)7, (short)1776);
        System.out.printf("7/4/1776: This should be \"Thursday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)28 , (short)05, (short)1986);
        System.out.printf("5/28/1986: This should be \"Wednesday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)4, (short)1991);
        System.out.printf("4/1/1991: This should be \"Monday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)1, (short)2013);
        System.out.printf("1/1/2013: This should be \"Tuesday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)5 , (short)11, (short)2013);
        System.out.printf("11/5/2013: This should be \"Tuesday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)1, (short)1800);
        System.out.printf("1/1/1800: This should be \"Wednesday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)2, (short)1800);
        System.out.printf("2/1/1800: This should be \"Saturday\" == %s%n",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)28 , (short)2, (short)1800);
        System.out.printf("%s This should be \"Friday\" == %s%n",
                "2/28/1800:", testDate.getDayOfWeek());
        
        testDate = new DateAD((short)1 , (short)3, (short)1800);
        System.out.printf("%s %s%n",
                "3/1/1800: This should be \"Saturday\" ==",
                testDate.getDayOfWeek());
        
        testDate = new DateAD((short)14 , (short)2, (short)1837);
        System.out.printf("%s %s%n",
                "2/14/1837: This should be \"Tuesday\" =="
                , testDate.getDayOfWeek());
    }

}//End DateDriver Class
