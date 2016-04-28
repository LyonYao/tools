package com.ilyon.tools.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyon
 *
 */
public class CategoryVo {
	private List<FeatureVo> features=new ArrayList<FeatureVo>();
	private String name;
	private String desc;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<FeatureVo> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureVo> features) {
		this.features = features;
	}
	
}
