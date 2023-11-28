package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.net.URLEncoder;

public class WLCriteriaMatchDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean matched;	
	private String wlCriteria;
	private String actualValue;
	
	public WLCriteriaMatchDto() {
		super();
	}

	public WLCriteriaMatchDto(Boolean matched, String wlCriteria) {
		super();
		this.matched = matched;
		this.wlCriteria = wlCriteria;
	}
	
	public WLCriteriaMatchDto(String wlCriteria) {
		super();
		this.wlCriteria = wlCriteria;
	}

	public Boolean getMatched() {
		return matched;
	}

	public void setMatched(Boolean matched) {
		this.matched = matched;
	}

	public String getWlCriteria() {
		return wlCriteria;
	}

	public void setWlCriteria(String wlCriteria) {
		this.wlCriteria = wlCriteria;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	
}
