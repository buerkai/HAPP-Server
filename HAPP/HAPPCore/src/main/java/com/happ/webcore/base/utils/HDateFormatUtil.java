package com.happ.webcore.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HDateFormatUtil {

	public static String getTimeStamp(String format) {
		return getTimeStamp(new Date(), format);
	}

	public static String getTimeStamp(Date date, String format) {
		SimpleDateFormat formatObject = null;
		try {
			formatObject = new SimpleDateFormat(format);
			return formatObject.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			formatObject = null;
		}
	}

}
