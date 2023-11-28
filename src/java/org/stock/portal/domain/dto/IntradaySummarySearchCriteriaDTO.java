package org.stock.portal.domain.dto;

import java.util.Date;

import org.stock.portal.domain.BSEIntraSummaryData;

public class IntradaySummarySearchCriteriaDTO extends BSEIntraSummaryData {

	private Date dataDate;	
	private Long watchlistId;
	
	private String orderBy;	
	private String orderType; //Ascending/Descending
	
	private Integer percentage52wHigh;
	
	private Integer numberOfRecords;
	
	private Long averageVolume;
	
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

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Long getWatchlistId() {
		return watchlistId;
	}

	public void setWatchlistId(Long watchlistId) {
		this.watchlistId = watchlistId;
	}

	public Integer getPercentage52wHigh() {
		return this.percentage52wHigh;
	}

	public void setPercentage52wHigh(Integer percentage52wHigh) {
		this.percentage52wHigh = percentage52wHigh;
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

	public IntradaySummarySearchCriteriaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
