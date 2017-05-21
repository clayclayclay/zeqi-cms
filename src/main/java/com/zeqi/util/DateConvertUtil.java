

/**
 * Project Name:zeqi-v2 
 * File Name:DateConvertUtil.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 17, 2017 9:15:01 AM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
  * ClassName: DateConvertUtil
  * Description: TODO
  * @author I337739
  * @version 
  * @see
  * @since 
  */

public class DateConvertUtil {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String longToString(long longTime) {
		return sdf.format(new Date(longTime));
	}
	
	public static String getNowStrTime() {
		return sdf.format(new Date());
	}
	
	public static long getNowLongTime() {
		return new Date().getTime();
	}

}
 
