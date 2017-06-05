

/**
 * Project Name:zeqi-v2 
 * File Name:ScheduledTasks.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * 
 * @author max
 * @date Jun 1, 2017 8:07:12 PM
 * @version 
 * @see
 * @since 
 */

package com.zeqi.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zeqi.service.CommonService;

/**
  * ClassName: ScheduledTasks
  * Description: TODO
  * @author I337739
  * @version 
  * @see
  * @since 
  */

@Component
public class ScheduledTasks {
	
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    private CommonService commonService;
    
    @Scheduled(cron = "0 59 23 * * * ")
    public void updateActivityRecord() {
    	if (commonService.updateRecord()) {
    		log.info("The time is now {} update record", dateFormat.format(new Date()));
    	}
    }
}
 
