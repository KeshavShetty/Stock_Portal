package org.stock.portal.domain.dto;

import java.util.Date;

public class ZerodhaOptionCandleVO extends ZerodhaCandleVO {
	
	Long id=-1L;
	
	float openInterest = 0f;
	
	float underlyingIndexValue = 0f;
	float impliedVolatility = 0f;
	float delta = 0f;
	float vega = 0f;
	float theta = 0f;
	float gamma = 0f;
	float timeValue = 0f;
	
	public ZerodhaOptionCandleVO() {
		super();
	}	

	public ZerodhaOptionCandleVO(float openPrice, float highPrice, float lowPrice, float closePrice, float volume, Date quoteTime) {
		super(openPrice, highPrice, lowPrice, closePrice, volume, quoteTime);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(float openInterest) {
		this.openInterest = openInterest;
	}

	public float getUnderlyingIndexValue() {
		return underlyingIndexValue;
	}

	public void setUnderlyingIndexValue(float underlyingIndexValue) {
		this.underlyingIndexValue = underlyingIndexValue;
	}

	public float getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(float impliedVolatility) {
		this.impliedVolatility = impliedVolatility;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public float getVega() {
		return vega;
	}

	public void setVega(float vega) {
		this.vega = vega;
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

	public float getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(float timeValue) {
		this.timeValue = timeValue;
	}
	
}
