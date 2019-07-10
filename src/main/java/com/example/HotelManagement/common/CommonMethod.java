package com.example.HotelManagement.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class CommonMethod {
	protected static Logger log = LoggerFactory.getLogger(CommonMethod.class);

	public static final int EXPIRATION = 60 * 24;

	public static Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}


}
