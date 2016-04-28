package com.ilyon.tools.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ilyon.tools.annotation.Category;
import com.ilyon.tools.annotation.Feature;

/**
 * @author lyon
 *
 */
@Controller
@Category(name="Cron  Job  工具",desc="提供Cron Job 分析功能")
@RequestMapping("/estimate")
public class CronJobController {
	@Feature(name="预算cron 字符串将会执行的时间",url="estimate/estimate",desc="")
	@RequestMapping(value="/estimate",method=RequestMethod.GET)
	public String estimateExecuteTime(){
		return "/cronjob/estimate";
	}
	@RequestMapping(value="/estimate",method=RequestMethod.POST)
	public ModelAndView estimateExecuteTime(String cronstr){
		List<String> result=new ArrayList<String>();
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
	       try {
			cronTriggerImpl.setCronExpression(cronstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	       Calendar calendar = Calendar.getInstance();  
	       Date now = calendar.getTime();  
	       calendar.add(Calendar.MONTH, 15);
	       List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, now, calendar.getTime());  
	       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	       for(int i =0;i < dates.size();i ++){  
	    	   result.add(dateFormat.format(dates.get(i)));  
	       }  
		return new ModelAndView("/cronjob/estimate","result",result);
	}
}
