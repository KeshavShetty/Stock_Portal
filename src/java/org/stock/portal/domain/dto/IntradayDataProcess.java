package org.stock.portal.domain.dto;

import java.io.Serializable;

import org.stock.portal.domain.Scrip;

public class IntradayDataProcess implements Serializable{
	
	private Scrip scrip;
	private String exchangeCode;
	
	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	
	
	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public IntradayDataProcess() {
		super();
	}

	
}