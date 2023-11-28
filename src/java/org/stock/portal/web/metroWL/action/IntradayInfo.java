package org.stock.portal.web.metroWL.action;

import java.io.Serializable;
import java.util.Date;

public class IntradayInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private float currentTickPrice;
	private float lastReadTickPrice = 0f; // Previous minute
	private Date tickTime;
	
	public float getCurrentTickPrice() {
		return currentTickPrice;
	}
	public void setCurrentTickPrice(float currentTickPrice) {
		this.currentTickPrice = currentTickPrice;
	}
	public float getLastReadTickPrice() {
		return lastReadTickPrice;
	}
	public void setLastReadTickPrice(float lastReadTickPrice) {
		this.lastReadTickPrice = lastReadTickPrice;
	}
	public Date getTickTime() {
		return tickTime;
	}
	public void setTickTime(Date tickTime) {
		this.tickTime = tickTime;
	}
	

}
