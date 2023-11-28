package org.stock.portal.domain.metroWL.dto;

import java.io.Serializable;

public class AbstractCriteriaDTO implements Serializable {
	
	private Long id;
	private String orderBy;
	private String orderType; //Ascending/Descending
	private Integer pageNumber;
	private Integer recordPerPage;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public AbstractCriteriaDTO() {
		super();
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(Integer recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

}
