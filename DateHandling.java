/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

/**
 *
 * @author pablo
 */
public class DateHandling {

    public static int LastDayOfMonth(int year, Integer month){
        int lastDay = 0;
        String monthS = "";
                if (month < 10) monthS = "0"+month.toString();
                else{monthS = month.toString();}
        switch(monthS){
            case "02": 
                if (LeapYear(year)){ lastDay = 29;}
                else {lastDay = 28;}        
                break;
            case "04": lastDay = 30; break;
            case "06": lastDay = 30; break;
            case "09": lastDay = 30; break;
            case "11": lastDay = 30; break;
            default: lastDay = 31; break;
        }
        return lastDay;
    }
    
    


public static boolean LeapYear(int y) {
 int theYear;
 theYear = y;
 boolean isLeapYear = false;
 if (theYear < 100) {
    if (theYear > 40) {
        theYear = theYear + 1900;
    } else {
        theYear = theYear + 2000;
    }
 }

if (theYear % 4 == 0) {
    if (theYear % 100 != 0) {
        //System.out.println("IT IS A LEAP YEAR");
        isLeapYear = true;

    } else if (theYear % 400 == 0) {
        //System.out.println("IT IS A LEAP YEAR");
        isLeapYear = true;
    } else {
       // System.out.println("IT IS NOT A LEAP YEAR");
       isLeapYear = false;
    }
  } else {
    //System.out.println("IT IS NOT A LEAP YEAR");
    isLeapYear = false;
  }
    return isLeapYear ;
}

    
    
}
