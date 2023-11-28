package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class EODForPastPerformanceCriteriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date forDate;	
	
	private Long watchlistId;
	
	private String orderBy;	
	private String orderType; //Ascending/Descending
	
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

	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	public EODForPastPerformanceCriteriaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
