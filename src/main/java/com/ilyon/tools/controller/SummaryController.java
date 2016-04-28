package com.ilyon.tools.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ilyon.tools.service.SummaryService;
import com.ilyon.tools.vo.CategoryVo;

/**
 * @author lyon
 *
 */
@Controller
@RequestMapping(value="summary")
public class SummaryController {
	@Autowired
	private SummaryService summaryService;
	@RequestMapping("home")
	public ModelAndView index(){
		List<CategoryVo> categories = summaryService.listCategories();
		return new ModelAndView("summary","categories",categories);
	}
}
