package org.stock.portal.domain.metroWL.dto;

import java.util.Date;

public class IntradaySnapshotSearchCriteriaDTO extends AbstractCriteriaDTO {

	private static final long serialVersionUID = 1L;
	
	private String scripName;
    private String bseCode;
    private String nseCode;
    
	private Date dataDate;
	
	private String exchange;
	
	private Float minCmp;
    private Float maxCmp;
    
    private Float minEps;
    private Float maxEps;
    
    private Float minPe;
    private Float maxPe;
    
    private Long averageVolume;
    
    private Float minPerformance;
    private Float maxPerformance;
    
    private Float minVolumePerformance;
    private Float maxVolumePerformance;
    
    private Long selectedWatchlist;
    
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public Float getMinCmp() {
		return minCmp;
	}
	public void setMinCmp(Float minCmp) {
		this.minCmp = minCmp;
	}
	public Float getMaxCmp() {
		return maxCmp;
	}
	public void setMaxCmp(Float maxCmp) {
		this.maxCmp = maxCmp;
	}
	public Float getMinEps() {
		return minEps;
	}
	public void setMinEps(Float minEps) {
		this.minEps = minEps;
	}
	public Float getMaxEps() {
		return maxEps;
	}
	public void setMaxEps(Float maxEps) {
		this.maxEps = maxEps;
	}
	public Float getMinPe() {
		return minPe;
	}
	public void setMinPe(Float minPe) {
		this.minPe = minPe;
	}
	public Float getMaxPe() {
		return maxPe;
	}
	public void setMaxPe(Float maxPe) {
		this.maxPe = maxPe;
	}
	public Long getAverageVolume() {
		return averageVolume;
	}
	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
	}
	public Float getMinPerformance() {
		return minPerformance;
	}
	public void setMinPerformance(Float minPerformance) {
		this.minPerformance = minPerformance;
	}
	public Float getMaxPerformance() {
		return maxPerformance;
	}
	public void setMaxPerformance(Float maxPerformance) {
		this.maxPerformance = maxPerformance;
	}
	public Float getMinVolumePerformance() {
		return minVolumePerformance;
	}
	public void setMinVolumePerformance(Float minVolumePerformance) {
		this.minVolumePerformance = minVolumePerformance;
	}
	public Float getMaxVolumePerformance() {
		return maxVolumePerformance;
	}
	public void setMaxVolumePerformance(Float maxVolumePerformance) {
		this.maxVolumePerformance = maxVolumePerformance;
	}
	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}
	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	public String getScripName() {
		return scripName;
	}
	public void setScripName(String scripName) {
		this.scripName = scripName;
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

}
