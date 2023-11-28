package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class AutoscanSearchCriteriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date fromDate;	
	private Date toDate;
	
	private Long watchlistId;	
	private Long scanCodeId;
	
	private Integer taTypeId;
	
	private String orderBy;	
	private String orderType; //Ascending/Descending
	
	private Integer numberOfRecords;
	
	private Long averageVolume;
	
	private Boolean exchangeCodeId;
	
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

	public Long getWatchlistId() {
		return watchlistId;
	}

	public void setWatchlistId(Long watchlistId) {
		this.watchlistId = watchlistId;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getScanCodeId() {
		return scanCodeId;
	}

	public void setScanCodeId(Long scanCodeId) {
		this.scanCodeId = scanCodeId;
	}

	public Integer getTaTypeId() {
		return taTypeId;
	}

	public void setTaTypeId(Integer taTypeId) {
		this.taTypeId = taTypeId;
	}

	public Boolean getExchangeCodeId() {
		return exchangeCodeId;
	}

	public void setExchangeCodeId(Boolean exchangeCodeId) {
		this.exchangeCodeId = exchangeCodeId;
	}

	public AutoscanSearchCriteriaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
