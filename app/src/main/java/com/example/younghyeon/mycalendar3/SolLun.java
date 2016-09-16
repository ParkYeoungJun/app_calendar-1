package com.example.younghyeon.mycalendar3;

/**
 * Created by YOUNGHYEON on 2016-09-16.
 */
import java.util.*;
import java.text.*;

public class SolLun {
    private String kk[] = {
            "1212122322121", // 1881
            "1212121221220",
            "1121121222120",
            "2112132122122",
            "2112112121220",
            "2121211212120",
            "2212321121212",
            "2122121121210",
            "2122121212120",
            "1232122121212",
            "1212121221220", // 1891
            "1121123221222",
            "1121121212220",
            "1212112121220",
            "2121231212121",
            "2221211212120",
            "1221212121210",
            "2123221212121",
            "2121212212120",
            "1211212232212",
            "1211212122210", // 1901
            "2121121212220",
            "1212132112212",
            "2212112112210",
            "2212211212120",
            "1221412121212",

            "1212122121210",

            "2112212122120",

            "1231212122212",

            "1211212122210",

            "2121123122122", // 1911

            "2121121122120",

            "2212112112120",

            "2212231212112",

            "2122121212120",

            "1212122121210",

            "2132122122121",

            "2112121222120",

            "1211212322122",

            "1211211221220",

            "2121121121220", // 1921

            "2122132112122",

            "1221212121120",

            "2121221212110",

            "2122321221212",

            "1121212212210",

            "2112121221220",

            "1231211221222",

            "1211211212220",

            "1221123121221",

            "2221121121210", // 1931

            "2221212112120",

            "1221241212112",

            "1212212212120",

            "1121212212210",

            "2114121212221",

            "2112112122210",

            "2211211412212",

            "2211211212120",

            "2212121121210",

            "2212214112121", // 1941

            "2122122121120",

            "1212122122120",

            "1121412122122",

            "1121121222120",

            "2112112122120",

            "2231211212122",

            "2121211212120",

            "2212121321212",

            "2122121121210",

            "2122121212120", //1951

            "1212142121212",

            "1211221221220",

            "1121121221220",

            "2114112121222",

            "1212112121220",

            "2121211232122",

            "1221211212120",

            "1221212121210",

            "2121223212121",

            "2121212212120", // 1961

            "1211212212210",

            "2121321212221",

            "2121121212220",

            "1212112112210",

            "2223211211221",

            "2212211212120",

            "1221212321212",

            "1212122121210",

            "2112212122120",

            "1211232122212", // 1971

            "1211212122210",

            "2121121122210",

            "2212312112212",

            "2212112112120",

            "2212121232112",

            "2122121212110",

            "2212122121210",

            "2112124122121",

            "2112121221220",

            "1211211221220", // 1981

            "2121321122122",

            "2121121121220",

            "2122112112322",

            "1221212112120",

            "1221221212110",

            "2122123221212",

            "1121212212210",

            "2112121221220",

            "1211231212222",

            "1211211212220", // 1991

            "1221121121220",

            "1223212112121",

            "2221212112120",

            "1221221232112",

            "1212212122120",

            "1121212212210",

            "2112132212221",

            "2112112122210",

            "2211211212210",

            "2221321121212", //2001

            "2212121121210",

            "2212212112120",

            "1232212122112",

            "1212122122120",

            "1121212322122",

            "1121121222120",

            "2112112122120",

            "2211231212122",

            "2121211212120",

            "2122121121210", // 2011

            "2124212112121",

            "2122121212120",

            "1212121223212",

            "1211212221220",

            "1121121221220",

            "2112132121222",

            "1212112121220",

            "2121211212120",

            "2122321121212",

            "1221212121210", // 2021

            "2121221212120",

            "1232121221212",

            "1211212212210",

            "2121123212221",

            "2121121212220",

            "1212112112220",

            "1221231211221",

            "2212211211220",

            "1212212121210",

            "2123212212121", // 2031

            "2112122122120",

            "1211212322212",

            "1211212122210",

            "2121121122120",

            "2212114112122",

            "2212112112120",

            "2212121211210",

            "2212232121211",

            "2122122121210",

            "2112122122120", // 2041

            "1231212122212",

            "1211211221220"

    };



    private int dt[] = {

            384, 355, 354, 384, 354, 354, 384, 354, 355, 384,

            355, 384, 354, 354, 383, 355, 354, 384, 355, 384,

            354, 355, 383, 354, 355, 384, 354, 355, 384, 354,

            384, 354, 354, 384, 355, 354, 384, 355, 384, 354,

            354, 384, 354, 354, 385, 354, 355, 384, 354, 383,

            354, 355, 384, 355, 354, 384, 354, 384, 354, 354,

            384, 355, 355, 384, 354, 354, 384, 354, 384, 354,

            355, 384, 355, 354, 384, 354, 384, 354, 354, 384,

            355, 354, 384, 355, 353, 384, 355, 384, 354, 355,

            384, 354, 354, 384, 354, 384, 354, 355, 384, 355,

            354, 384, 354, 384, 354, 354, 385, 354, 355, 384,

            354, 354, 383, 355, 384, 355, 354, 384, 354, 354,

            384, 354, 355, 384, 355, 384, 354, 354, 384, 354,

            354, 384, 355, 384, 355, 354, 384, 354, 354, 384,

            354, 355, 384, 354, 384, 355, 354, 383, 355, 354,

            384, 355, 384, 354, 354, 384, 354, 354, 384, 355,

            355, 384, 354

    };



    private int day_array[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};



    private int total_day;

    private int acc_day;

    private int buff_day;

    private int SolYear;

    private int SolMonth;

    private int SolDay;

    private int LunYear;

    private int LunMonth;

    private int LunDay;

    private int i;

    private int j;

    private int m0;

    private int m1;

    private int m2;

    private int temp;

    private boolean isLeap;

    private String str;



    private int yy;

    private int n2;

    private int mm;

    private int r;





    public String SolToLun(int SolYear, int SolMonth, int SolDay) {

        if((SolYear < 1881) || (SolYear > 2043)) System.out.println("ERROR!");
        if((SolMonth < 1) || (SolMonth > 12)) System.out.println("ERROR!");
        if((SolDay < 1) || (SolDay > 31)) System.out.println("ERROR!");

        //total days
        SolYear--;
        total_day = SolYear*365 + SolYear/4 - SolYear/100 + SolYear/400;
        SolYear++;
        if(((SolYear % 4) == 0) && ((SolYear % 100) != 0) || ((SolYear % 400) == 0)) day_array[1] = 29;

        else day_array[1] = 28;



        for(i = 0; i < SolMonth-1; i++) total_day = total_day + day_array[i];

        total_day = total_day + SolDay;

        //total days until 1880

        acc_day = total_day - 686686 + 1;



        //Get Lunar Year

        buff_day = dt[0];

        for(i = 0; i <= 162; i++) {

            if(acc_day <= buff_day) break;

            buff_day = buff_day + dt[i+1];

        }

        LunYear = i + 1881;



        //Get Lunar Month

        buff_day = buff_day - dt[i];

        acc_day  = acc_day - buff_day;



        if(!kk[i].substring(12,13).equals("0")) temp = 13;

        else temp = 12;



        m2 = 0;

        for(j = 0; j < temp-1; j++) {

            if(Integer.parseInt(kk[i].substring(j,j+1)) <= 2) {

                m2++;

                m1 = Integer.parseInt(kk[i].substring(j,j+1)) + 28;

            } else {

                m1 = Integer.parseInt(kk[i].substring(j,j+1)) + 26;

            }

            if(acc_day <= m1) break;

            acc_day = acc_day - m1;

        }

        m0 = j;

        LunMonth = m2;



        //Get Lunar Day

        LunDay = acc_day;



        if((kk[LunYear - 1881].substring(12,13) != "0") && (Integer.parseInt(kk[LunYear - 1881].substring(m0,m0+1)) > 2))

            isLeap = true;

        else

            isLeap = false;



        //return date string

        str = Integer.toString(LunYear);

        if(LunMonth < 10) str += ".0"+LunMonth;

        else str += "."+LunMonth;



        if(LunDay < 10) str += ".0"+LunDay;

        else str += "."+LunDay;

        if(isLeap == true) str += "(윤달)";



        return str;

    }



    public String LunToSol(int LunYear, int LunMonth, int LunDay, boolean isLeap) {



        if((LunYear < 1881) || (LunYear > 2043)) System.out.println("ERROR!");

        if((LunMonth <1)  || (LunMonth > 12)) System.out.println("ERROR!");

        if((LunDay < 1) || (LunDay > 30)) System.out.println("ERROR!");



        yy = -1;

        acc_day = 0;

        if(LunYear != 1881) {

            yy = LunYear - 1882;

            for(i = 0; i <= yy; i++) {

                for(j = 0; j <= 12; j++)

                    acc_day = acc_day + Integer.parseInt(kk[i].substring(j,j+1));

                if(kk[i].substring(12,13).equals("0")) acc_day = acc_day + 336;

                else acc_day = acc_day + 362;

            }

        }



        yy++;

        n2 = LunMonth - 1;

        mm = -1;



        r = 2;

        while(r != 1) {

            mm++;

            if(Integer.parseInt(kk[yy].substring(mm,mm+1)) > 2) {

                acc_day = acc_day + 26 + Integer.parseInt(kk[yy].substring(mm,mm+1));

                n2++;

            } else {

                if(mm == n2) break;

                else acc_day = acc_day + 28 + Integer.parseInt(kk[yy].substring(mm,mm+1));

            }

        }



        // Leap Year

        if(isLeap == true) acc_day = acc_day + 28 + Integer.parseInt(kk[yy].substring(mm,mm+1));

        acc_day = acc_day + LunDay + 29;

        yy = 1880;

        r = 2;

        while(r != 1) {
            yy++;
            mm = 365;
            if((yy % 4) == 0 && ((yy % 100) != 0 || (yy % 400) == 0)) mm = 366;
            if(acc_day <= mm) break;
            acc_day = acc_day - mm;
        }
        SolYear = yy;
        day_array[1] = mm - 337;
        yy = 0;
        r = 2;

        while(r != 1) {
            yy++;
            if(acc_day <= day_array[yy-1]) break;
            acc_day = acc_day - day_array[yy-1];
        }
        SolMonth = yy;
        SolDay = acc_day;

        //return date string

        str = Integer.toString(SolYear);
        if(SolMonth < 10) str += "-0" + Integer.toString(SolMonth);
        else str += "-"+Integer.toString(SolMonth);

        if(SolDay < 10) str += "-0" + Integer.toString(SolDay);
        else str += "-"+ Integer.toString(SolDay);
        return str;
    }
}
