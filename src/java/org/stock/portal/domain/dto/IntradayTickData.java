package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class IntradayTickData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date tickDatastamp;
	
	private Float cureentPrice;
	
	private Long currentVolume;
	
	private Long totalVolume;
	
	public IntradayTickData() {
		super();
	}

	public IntradayTickData(Date tickDatastamp, Float cureentPrice, Long currentVolume, Long totalVolume) {
		super();
		this.tickDatastamp = tickDatastamp;
		this.cureentPrice = cureentPrice;
		this.currentVolume = currentVolume;
		this.totalVolume = totalVolume;
	}

	public Date getTickDatastamp() {
		return tickDatastamp;
	}

	public void setTickDatastamp(Date tickDatastamp) {
		this.tickDatastamp = tickDatastamp;
	}

	public Float getCureentPrice() {
		return cureentPrice;
	}

	public void setCureentPrice(Float cureentPrice) {
		this.cureentPrice = cureentPrice;
	}

	public Long getCurrentVolume() {
		return currentVolume;
	}

	public void setCurrentVolume(Long currentVolume) {
		this.currentVolume = currentVolume;
	}

	public Long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Long totalVolume) {
		this.totalVolume = totalVolume;
	}
	
}