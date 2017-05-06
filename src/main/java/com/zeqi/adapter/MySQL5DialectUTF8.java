
/**
 * Project Name:zeqi-v2 
 * File Name:MySQL5DialectUTF8.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 5, 2017 9:57:02 AM
 * @version 
 * @see
 * @since 
 */

package com.zeqi.adapter;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * ClassName: MySQL5DialectUTF8 Description: TODO
 * 
 * @author I337739
 * @version
 * @see
 * @since
 */

public class MySQL5DialectUTF8 extends MySQL5Dialect {

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

}
