package org.stock.portal.domain.dto;

import org.stock.portal.domain.Scrip;

public class ScripSearchCriteriaDTO extends Scrip {

	private String sectorName;
	
	private String orderBy;
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	private String orderType; //Ascending/Descending
	
	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public ScripSearchCriteriaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
