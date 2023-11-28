package org.stock.portal.domain.dto;

import java.io.Serializable;


/**
 * The Scrip EOD Dto class
 * 
 */
public class ScripPerformanceDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Float firstPrice;
	private Float lastPrice;	
	private Float performance;
	
	private Long firstVolume;	
	private Long lastVolume;
	
	private Long avgVolume;
	
	private Float volumePerformance;
	
	private Float volumeVsAvgVolume;
	
	private Float volumeVsLastDay1MVol;
	
	private Float turnover;
	
	private String bseCode;
	private String nseCode;
	
	private Float upGap;
	
	private Float downGap;
	
	private Float barSizeOfTheDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Float firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Float getPerformance() {
		return performance;
	}

	public void setPerformance(Float performance) {
		this.performance = performance;
	}

	public String getBseCode() {
		return bseCode;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public String getNseCode() {
		return nseCode;
	}

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
	}

	public Long getFirstVolume() {
		return firstVolume;
	}

	public void setFirstVolume(Long firstVolume) {
		this.firstVolume = firstVolume;
	}

	public Long getLastVolume() {
		return lastVolume;
	}

	public void setLastVolume(Long lastVolume) {
		this.lastVolume = lastVolume;
	}

	public Float getVolumePerformance() {
		return volumePerformance;
	}

	public void setVolumePerformance(Float volumePerformance) {
		this.volumePerformance = volumePerformance;
	}

	public Long getAvgVolume() {
		return avgVolume;
	}

	public void setAvgVolume(Long avgVolume) {
		this.avgVolume = avgVolume;
	}

	public Float getVolumeVsAvgVolume() {
		return volumeVsAvgVolume;
	}

	public void setVolumeVsAvgVolume(Float volumeVsAvgVolume) {
		this.volumeVsAvgVolume = volumeVsAvgVolume;
	}

	public Float getTurnover() {
		return turnover;
	}

	public void setTurnover(Float turnover) {
		this.turnover = turnover;
	}	
	
	public int getTurnoverInCr() {
		return (int) (turnover/10000000);
	}

	public Float getUpGap() {
		return upGap;
	}

	public void setUpGap(Float upGap) {
		this.upGap = upGap;
	}

	public Float getDownGap() {
		return downGap;
	}

	public void setDownGap(Float downGap) {
		this.downGap = downGap;
	}

	public Float getBarSizeOfTheDay() {
		return barSizeOfTheDay;
	}

	public void setBarSizeOfTheDay(Float barSizeOfTheDay) {
		this.barSizeOfTheDay = barSizeOfTheDay;
	}

	public Float getVolumeVsLastDay1MVol() {
		return volumeVsLastDay1MVol;
	}

	public void setVolumeVsLastDay1MVol(Float volumeVsLastDay1MVol) {
		this.volumeVsLastDay1MVol = volumeVsLastDay1MVol;
	}	
}