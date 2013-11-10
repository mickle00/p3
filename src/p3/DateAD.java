/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p3;
import java.util.*;

/**
 *
 * @author Jens Bodal
 */
public class DateAD {
    //CONSTANTS
    public static final short MIN_YEAR = 1752;
    public static final short MONTHS_YEAR = 12;
    public static final short DAYS_WEEK = 7;
    
    //TO MOVE TO BOTTOM
    private short month;
    private short year;
    private short dayOfMonth;
    private short dayOfYear; //Need to implement Zeller's, also need to implement as assigned by class
    private short dayOfWeek_Number; //0 = Sunday
    private String dayOfWeek_Literal; //Sunday, Monday, etc.
    private String month_Literal;
    private short daysInYear = 365;
    private boolean isLeapYear;
    
    
    //private static short countLeaps -- takes in a year and returns the number of leap years from the base year to it.  ??????

    //-- returns a true if this day comes before the DateAD passed in as an argument; false otherwise.
    public boolean lessThan(DateAD inputDate) {
        return false;
    } 

    //returns a true if this day is the same day as the DateAD passed in as an argument; false otherwise.
    public boolean equals(DateAD inputDate) {
        return false;
    } 
    
    public DateAD dateFromDayOfYear(short dayOfYear, short year){
        short newMonth = 0;
        if (dayOfYear > 366 || dayOfYear < 1) {return new DateAD();}
        
        if (dayOfYear == 366 && isLeapYear(year)) {
            if (isLeapYear(year)) {
                dayOfYear = 31;
                newMonth = 12;
            }
            else {
                return new DateAD();
            }
        }

        else {
            //Start look up code
            for (MONTHNAMES M : MONTHNAMES.values()) {
                if (dayOfYear - M.month_NumberOfDays <= 0) {
                    newMonth = (short)(M.month_Number + 1);
                    break;
                }
                else {
                    dayOfYear -= M.month_NumberOfDays;
                }
            }
        }
        return new DateAD(dayOfYear, newMonth, year);
    }
    
    public DateAD getTomorrow(DateAD inputDate) {
        short inputDayOfYear = inputDate.dayOfYear;
        short inputYear = inputDate.year;
        
        if (inputDayOfYear == 365 || inputDayOfYear == 366) {
            inputYear += 1;
            inputDayOfYear = 1;
        }
        else {
            inputDayOfYear += 1;
        }
        System.out.println(inputDayOfYear);
        System.out.println(isLeapYear(inputYear));
        return dateFromDayOfYear(inputDayOfYear, inputYear);
    }
    
    public DateAD getYesterday(DateAD inputDate) {
        short inputDayOfYear = inputDate.dayOfYear;
        short inputYear = inputDate.year;
        
        if (inputDayOfYear == 1) {
            inputYear -= 1;
            if (!isLeapYear(inputYear)) {
                inputDayOfYear = 365;
            }
            else {
                inputDayOfYear = 366;
            }
        }
        else {
            inputDayOfYear -= 1;
        }
        
        return dateFromDayOfYear(inputDayOfYear, inputYear);
    }
    /* Need to implement 4 constructors --  one default, and ones with 1 (month),
     * 2 (month, year), and 3 (dayOfMonth, month, year) parameters.  
     * All four constructors should begin by calling setCurrentDate(). 
     * Any out-of-range parameter will cause the object to remain as the current date. 
     */
    
    public DateAD() {
        setCurrentDate();
    }
    
    public DateAD(short dayOfMonth) {
        setCurrentDate();
        setDayOfMonth(dayOfMonth);
    }
    
    public DateAD(short dayOfMonth, short month) {
        setCurrentDate();
        setMonth(month);
        setDayOfMonth(dayOfMonth);
    }

    public DateAD(short dayOfMonth, short month, short year) {
        setCurrentDate();
        setMonth(month);
        setYear(year);
        setDayOfMonth(dayOfMonth);
    }
    
    public short getDayOfMonth(){
        return dayOfMonth;
    }
    
    public short getMonth(){
        return month;
    }
    
    public short getYear(){
        return year;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek_Literal;
    }
    
    public short getDayOfYear() {
        return dayOfYear;
    }
    
    private void setDayOfMonth(short dayOfMonth) {
        isLeapYear = isLeapYear(this.year);
        if (isLeapYear) {
            MONTHNAMES.FEBRUARY.month_NumberOfDays += 1;
            daysInYear += 1;
        }
        
        if (dayOfMonth > 0 && dayOfMonth <= daysInMonth(month)) {
            this.dayOfMonth = dayOfMonth;
        }
        else {
            setCurrentDate();
        }
        
        setDayOfYear(this.dayOfMonth, month);
        setDayOfWeek(this.dayOfMonth, month, year);
    }

    private void setMonth(short month) {
        if (month > 0 && month <= MONTHS_YEAR) {
            this.month = month;
        }
        else {
            setCurrentDate();
        }
    }
    
    private void setYear(short year){
        if (year < MIN_YEAR) {
            System.out.printf("Year cannot be before %d%n" + 
                    "Setting Year to Current Year%n", MIN_YEAR);
            setCurrentDate();
        }

        this.year = year;
    }

    private short daysInMonth(short month) {
        for (MONTHNAMES M : MONTHNAMES.values()) {
            if (month - 1 == M.month_Number) {
                month_Literal = M.month_Literal;
                return M.month_NumberOfDays;
            }
        }
        return daysInMonth(this.month);
    }
    
    private void setDayOfYear(short dayOfMonth, short month) {
     short dayCount = 0;
     for (MONTHNAMES M : MONTHNAMES.values()) {
          if (month - 1 > M.month_Number) {dayCount+= M.month_NumberOfDays;}
     }
     dayCount += dayOfMonth;
     dayOfYear = dayCount;
}

    
    /**
     * Based on the following formula:<br/>
     * Zeller's Congruence: en.wikipedia.org/wiki/Zeller's_congruence<br />
     * h = (d + [13(m+1)/5] + y + [y/4] + [c/4] + 5c + 1) mod 7<br />
     * <ul>
     * <li>Brackets denote the value without the remainder, i.e. FLOOR</li>
     * <li>h is day of week, 0 = Saturday</li>
     * <li>d is day of month</li>
     * <li>m is month number, January is 13 and February is 14</li>
     * <li><b>If the month number is January (13) or February (14) then
     * the year = year - 1</b></li>
     * <li>y is year of century, e.g. for 2013 d = 13, 2000 = 99</li>
     * <li>c is century, e.g. for 2013 c = 20, 2000 = 19</li><br />
     * </ul>
     * 
     * @return the day of the week in String format
     */
    private String setDayOfWeek(
            short dayOfMonth, short month, short year) {
        
        //Ignore warning, it's wrong.
        short monthValue = 0;
       
        //Calculate monthValue
        switch (month) {
            case 1:     monthValue = 13;
                        break;
            case 2:     monthValue = 14;
                        break;
            default:    monthValue = month;
        }
        
        if (monthValue == 13 || monthValue == 14) {year -= 1;}
        short twoDigitYear = (short)(year % 100);
        short century = (short)(year / 100);
        // d
        short dayCalc = dayOfMonth;
        // [13(m+1)/5]
        short monthCalc = (short)(13 * (monthValue+1) / 5);
        // y + [y/4]
        short twoDigitYearCalc = (short)(twoDigitYear + (twoDigitYear / 4));
        // [c/4] + 5c
        short centuryCalc = (short)((century / 4) + (century * 5));
        // h
        short numericalDayOfWeek = (short)
              ((dayCalc + monthCalc + twoDigitYearCalc + centuryCalc - 1) % 7);

        /** For Debugging
        System.out.printf("Q: %d%n M: %d%n K: %d%n C: %d%n",
                dayCalc, monthCalc, twoDigitYearCalc, centuryCalc);
        **/

        switch (numericalDayOfWeek) {
            case 0:
                dayOfWeek_Literal = DAYNAMES.SUNDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.SUNDAY.dayOfWeek_Number;
                break;
            case 1:
                dayOfWeek_Literal = DAYNAMES.MONDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.MONDAY.dayOfWeek_Number;
                break;
            case 2:
                dayOfWeek_Literal = DAYNAMES.TUESDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.TUESDAY.dayOfWeek_Number;
                break;
            case 3:
                dayOfWeek_Literal = DAYNAMES.WEDNESDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.WEDNESDAY.dayOfWeek_Number;
                break;
            case 4:
                dayOfWeek_Literal = DAYNAMES.THURSDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.THURSDAY.dayOfWeek_Number;
                break;
            case 5:
                dayOfWeek_Literal = DAYNAMES.FRIDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.FRIDAY.dayOfWeek_Number;
                break;
            case 6:
                dayOfWeek_Literal = DAYNAMES.SATURDAY.dayOfWeek_Literal;
                dayOfWeek_Number = DAYNAMES.SATURDAY.dayOfWeek_Number;
                break;
        }
        return dayOfWeek_Literal;
    }
    
    
    /**
     * isLeapYear takes input as a short and determines if it is 
     * a leap year. <br /> <br />
     * <i>A year is a leap year if it is divisible by 4 unless it divisible by 
     * 100 except if it is also divisible by 400.</i>
     * @param year as short
     * @return boolean: {@code true} if leap year, {@code false} if otherwise
     */
    public static boolean isLeapYear(short inputYear) {
        if (inputYear % 4 == 0) {
            if (inputYear % 100 == 0) {
                if (inputYear % 400 == 0) {
                    return true; //Divisible by 4, 100, and 400
                }
                else {
                    return false; //Divisible by 4, 100, but not 400
                }
            }
            else {
                return true; //Divisible by 4 but not 100
            }
        }
        else {
            return false; //Not divisible by 4
        }
    }
    
    public final void setCurrentDate() {
        GregorianCalendar cal = new GregorianCalendar();
        setYear((short)cal.get(GregorianCalendar.YEAR));
        setMonth(((short)(cal.get(GregorianCalendar.MONTH)+1)));
        setDayOfMonth((short)cal.get(GregorianCalendar.DAY_OF_MONTH));
    }
    
    private static enum MONTHNAMES {
        //month_Number, month_Literal, month_NumberofDays
        JANUARY ((short)0, "January", (short)31),
        FEBRUARY ((short)1, "February", (short)28),
        MARCH ((short)2, "March", (short)31),
        APRIL ((short)3, "April", (short)30),
        MAY ((short)4, "May", (short)31),
        JUNE ((short)5, "June", (short)30),
        JULY ((short)6, "July", (short)31),
        AUGUST ((short)7, "August", (short)31),
        SEPTEMBER ((short)8, "September", (short)30),
        OCTOBER ((short)9, "October", (short)31),
        NOVEMBER ((short)10, "November", (short)30),
        DECEMBER ((short)11, "December", (short)31);
        
        private short month_Number;
        private String month_Literal;
        private short month_NumberOfDays; 
        
        MONTHNAMES(
                short month_Number, 
                String month_Literal, 
                short month_NumberOfDays) {
            this.month_Literal = month_Literal;
            this.month_Number = month_Number;
            this.month_NumberOfDays = month_NumberOfDays;
        } //End MONTHNAMES constructor
    } //End MONTHNAMES enum
    
    private static enum DAYNAMES {
        SUNDAY ("Sunday", (short)0),
        MONDAY ("Monday", (short)1),
        TUESDAY ("Tuesday", (short)2),
        WEDNESDAY ("Wednesday", (short)3),
        THURSDAY ("Thursday", (short)4),
        FRIDAY ("Friday", (short)5),
        SATURDAY ("Saturday", (short)6);

        private String dayOfWeek_Literal;
        private short dayOfWeek_Number;
        
        DAYNAMES(String dayOfWeek_Literal, short dayOfWeek_Number) {
            this.dayOfWeek_Literal = dayOfWeek_Literal;
            this.dayOfWeek_Number = dayOfWeek_Number;
        }
    }

    /**
    @return the day-of-week, day-of-month, month, year (as text)
    * e.g., "Tuesday, 2 January, 2007"
    */
    @Override
    public String toString() {
        return String.format("%s, %d %s, %d", 
                dayOfWeek_Literal,
                dayOfMonth,
                month_Literal,
                year);
    }    
} //End DateAD class
