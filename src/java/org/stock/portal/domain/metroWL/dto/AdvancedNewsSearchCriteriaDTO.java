package org.stock.portal.domain.metroWL.dto;



public class AdvancedNewsSearchCriteriaDTO extends NewsSearchCriteriaDTO {

	private static final long serialVersionUID = 1L;
	
	private Long selectedSource;
	
	private String searchString; 
	
	private String scripName;
    private String bseCode;
    private String nseCode;
    
    private Float minCmp;
    private Float maxCmp;
    
    private Float minEps;
    private Float maxEps;
    
    private Float minPe;
    private Float maxPe;
    
    private Long averageVolume;
    
	public Long getSelectedSource() {
		return selectedSource;
	}
	public void setSelectedSource(Long selectedSource) {
		this.selectedSource = selectedSource;
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
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	

}
