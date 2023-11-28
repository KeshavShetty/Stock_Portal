package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The Scrip EOD Dto class
 * 
 */
public class OptionOI implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tradingSymbol;	
	private Float openInterest;
	private Date quoteTime;
	
	public OptionOI() {
		super();
	}

	public OptionOI(String tradingSymbol, Float openInterest, Date quoteTime) {
		super();
		this.tradingSymbol = tradingSymbol;
		this.openInterest = openInterest;
		this.quoteTime = quoteTime;
	}

	public String getTradingSymbol() {
		return tradingSymbol;
	}

	public void setTradingSymbol(String tradingSymbol) {
		this.tradingSymbol = tradingSymbol;
	}

	public Float getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(Float openInterest) {
		this.openInterest = openInterest;
	}

	public Date getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}
			
	
	
}