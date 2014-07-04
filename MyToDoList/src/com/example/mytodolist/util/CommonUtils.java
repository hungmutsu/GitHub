package com.example.mytodolist.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

public class CommonUtils {
  /**
   * Return date in specified format.
   * @param milliSeconds Date in milliseconds
   * @param dateFormat Date format 
   * @return String representing date in specified format
   */
  public static String getDate(long milliSeconds, String dateFormat)
  {
      // Create a DateFormatter object for displaying date in specified format.
      DateFormat formatter = new SimpleDateFormat(dateFormat);

      // Create a calendar object that will convert the date and time value in milliseconds to date. 
       Calendar calendar = Calendar.getInstance();
       calendar.setTimeInMillis(milliSeconds);
       Log.d("CommonUtils", formatter.format(calendar.getTime()));
       return formatter.format(calendar.getTime());
  }
  
}
