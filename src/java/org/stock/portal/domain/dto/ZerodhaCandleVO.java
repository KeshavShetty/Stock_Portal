package org.stock.portal.domain.dto;

import java.util.Date;

public class ZerodhaCandleVO {
	
	private float openPrice;
	
	private float highPrice;
	
	private float lowPrice;
	
	private float closePrice;
	
	private float volume;
	
	private Date quoteTime;

	public float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

	public float getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}

	public float getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public float getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public Date getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}
	
	public ZerodhaCandleVO() {
		super();
	}	

	public ZerodhaCandleVO(float openPrice, float highPrice, float lowPrice, float closePrice, float volume, Date quoteTime) {
		super();
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.quoteTime = quoteTime;
	}
}
