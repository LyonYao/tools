package com.ilyon.tools.service;

import java.util.List;
import java.util.Set;

import com.ilyon.tools.vo.CategoryVo;

/**
 * @author lyon
 *
 */
public interface SummaryService {

	List<CategoryVo> listCategories();

	Set<String> listCodeFiles(String url);

	String getClassContent(String clsName);

}
