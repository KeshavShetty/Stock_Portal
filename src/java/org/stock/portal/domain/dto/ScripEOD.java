package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The Scrip EOD Dto class
 * 
 */
public class ScripEOD implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long scripId;
	
	private Float closePrice;

	private Date dataDate;
	
	public ScripEOD() {
		super();
	}
			
	
	public ScripEOD(Float openPrice,  Float highPrice, Float lowPrice, Float closePrice, Long volume, Date dataDate) {
		super();
		this.closePrice = closePrice;
		this.dataDate = dataDate;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.openPrice = openPrice;
		this.volume = volume;
	}

	private Boolean exchangeCode;

	private Float highPrice;

	private Float lowPrice;

	private Long openInterest;

	private Float openPrice;

	private Float previousClose;

	private Long volume;
	
	private Float meanPrice;
	
	private Float cfWeightage;

	public Float getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Float closePrice) {
		this.closePrice = closePrice;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Float getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Float highPrice) {
		this.highPrice = highPrice;
	}

	public Float getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Long getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(Long openInterest) {
		this.openInterest = openInterest;
	}

	public Float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Float openPrice) {
		this.openPrice = openPrice;
	}

	public Float getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(Float previousClose) {
		this.previousClose = previousClose;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	public Float getMeanPrice() {
		return meanPrice;
	}

	public void setMeanPrice(Float meanPrice) {
		this.meanPrice = meanPrice;
	}

	public Float getCfWeightage() {
		return cfWeightage;
	}

	public void setCfWeightage(Float cfWeightage) {
		this.cfWeightage = cfWeightage;
	}
	
}