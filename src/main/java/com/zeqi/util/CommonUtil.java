

/**
 * Project Name:zeqi-v2 
 * File Name:CommonUtil.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 17, 2017 10:01:54 AM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi.util;


/**
  * ClassName: CommonUtil
  * Description: TODO
  * @author clayclayclay
  * @version 
  * @see
  * @since 
  */

public class CommonUtil {
	
	public static String convertDocumentName(String name) {
		String prefix = name.split("-")[0];
		String suffix = name.split("-")[1];
		String type = suffix.split("\\.")[1];
		String documentName = prefix + type;
		return documentName;
	}
	
	
	public static String nameToKey(String name) {
		String prefix = name.split("\\.")[0];
		String suffix = name.split("\\.")[1];
		String keyPrefix = prefix + "-" + DateConvertUtil.getNowLongTime();
		return  (keyPrefix + "." + suffix);
	}
}
 
