

/**
 * Project Name:zeqi-v2 
 * File Name:TimeZoneTime.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date Jun 1, 2017 8:42:27 AM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
  * ClassName: TimeZoneTime
  * Description: TODO
  * @author I337739
  * @version 
  * @see
  * @since 
  */

public class TimeZoneTime {
	
	
//	@Test
	public void timeTest() {
		Date date = new Date();
		DateTime dt = new DateTime(date);
		DateTimeZone dtZone = DateTimeZone.forID("America/New_York");
		DateTime dtus = dt.withZone(dtZone);
		Date dateInAmerica = dtus.toLocalDateTime().toDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd : hh:mm:ss");
		System.out.print(sdf.format(dateInAmerica));
	}
	
	@Test
	public void timeTest02() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		System.out.println(day);
	}

}
 
