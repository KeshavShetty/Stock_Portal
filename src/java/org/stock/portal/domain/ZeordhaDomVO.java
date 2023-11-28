package org.stock.portal.domain;

import java.util.Date;

public class ZeordhaDomVO {
	
	
	private Float ltp;
	private Float volumeBuySellDiff;
	private Date tickQuoteTime;
	private Float buyCV;
	private Float buyMean;
	private Float buyStddev;
	private Float buyVvwap;
	private Float sellCV;
	private Float sellMean;
	private Float sellStddev;
	private Float sellVvwap;
	
	private Float buyTotal;
	private Float sellTotal;
	
	private Float volumeTradedToday;
	
	private float lastTradedQty;
	
	public Float getLtp() {
		return ltp;
	}
	public void setLtp(Float ltp) {
		this.ltp = ltp;
	}
	public Float getVolumeBuySellDiff() {
		return volumeBuySellDiff;
	}
	public void setVolumeBuySellDiff(Float volumeBuySellDiff) {
		this.volumeBuySellDiff = volumeBuySellDiff;
	}
	public Date getTickQuoteTime() {
		return tickQuoteTime;
	}
	public void setTickQuoteTime(Date tickQuoteTime) {
		this.tickQuoteTime = tickQuoteTime;
	}
	public Float getBuyCV() {
		return buyCV;
	}
	public void setBuyCV(Float buyCV) {
		this.buyCV = buyCV;
	}
	public Float getBuyMean() {
		return buyMean;
	}
	public void setBuyMean(Float buyMean) {
		this.buyMean = buyMean;
	}
	public Float getBuyStddev() {
		return buyStddev;
	}
	public void setBuyStddev(Float buyStddev) {
		this.buyStddev = buyStddev;
	}
	public Float getBuyVvwap() {
		return buyVvwap;
	}
	public void setBuyVvwap(Float buyVvwap) {
		this.buyVvwap = buyVvwap;
	}
	public Float getSellMean() {
		return sellMean;
	}
	public void setSellMean(Float sellMean) {
		this.sellMean = sellMean;
	}
	public Float getSellStddev() {
		return sellStddev;
	}
	public void setSellStddev(Float sellStddev) {
		this.sellStddev = sellStddev;
	}
	public Float getSellVvwap() {
		return sellVvwap;
	}
	public void setSellVvwap(Float sellVvwap) {
		this.sellVvwap = sellVvwap;
	}
	public Float getSellCV() {
		return sellCV;
	}
	public void setSellCV(Float sellCV) {
		this.sellCV = sellCV;
	}
	public float getLastTradedQty() {
		return lastTradedQty;
	}
	public void setLastTradedQty(float lastTradedQty) {
		this.lastTradedQty = lastTradedQty;
	}
	public Float getBuyTotal() {
		return buyTotal;
	}
	public void setBuyTotal(Float buyTotal) {
		this.buyTotal = buyTotal;
	}
	public Float getSellTotal() {
		return sellTotal;
	}
	public void setSellTotal(Float sellTotal) {
		this.sellTotal = sellTotal;
	}
	public Float getVolumeTradedToday() {
		return volumeTradedToday;
	}
	public void setVolumeTradedToday(Float volumeTradedToday) {
		this.volumeTradedToday = volumeTradedToday;
	}

}
