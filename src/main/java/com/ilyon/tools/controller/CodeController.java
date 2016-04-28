package com.ilyon.tools.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ilyon.tools.annotation.Category;
import com.ilyon.tools.annotation.Feature;
import com.ilyon.tools.service.SummaryService;

/**
 * @author lyon
 *
 */
@Controller
@RequestMapping("/code")
@Category(name="系统代码查看",desc="")
public class CodeController {
	@Autowired
	private SummaryService summaryService;
	@Feature(name="查看功能url的代码列表",url="code/list-relative-code?url=",desc="")
	@RequestMapping("/list-relative-code")
	public ModelAndView listCodeFiles(@RequestParam("url") String url){
		return new ModelAndView("code/code-file-list","classSet",summaryService.listCodeFiles(url));
		
	}
	@RequestMapping("/view-class")
	public ModelAndView viewClass(@RequestParam("class") String clsName){
		
		return new ModelAndView("code/view-class","classContent",StringEscapeUtils.escapeHtml4(summaryService.getClassContent(clsName)));
		
	}
	
}
