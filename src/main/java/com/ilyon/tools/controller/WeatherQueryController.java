package com.ilyon.tools.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ilyon.tools.annotation.Category;
import com.ilyon.tools.annotation.Feature;
import com.ilyon.tools.utils.MaskTool;

@Controller
@RequestMapping("/weather-query")
@Category(name="查询指定地方天气",desc="")
public class WeatherQueryController {
	
	private static final String API_KEY = MaskTool.extract("[MASK]neumZNhUwS38ZQBoUCrsqfZGIeW0LRwX[/MASK]");
	private static final String HTTP_APIS_WEATHERSERVICE = "http://api.map.baidu.com/telematics/v3/weather";
	@Feature(name="查询天气",url="weather-query/query",desc="")
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public String estimateExecuteTime(){
		return "/weather_query/query";
	}
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public ModelAndView query(String city){
		String httpUrl = HTTP_APIS_WEATHERSERVICE;
		BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = HTTP_APIS_WEATHERSERVICE + "?location=" + city+"&output=json&ak="+API_KEY;
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	    } catch (Exception e) {
	    	return new ModelAndView("/weather_query/query","result",e.getMessage());
	    }
		return new ModelAndView("/weather_query/query","result",sbf.toString());
	}
}
