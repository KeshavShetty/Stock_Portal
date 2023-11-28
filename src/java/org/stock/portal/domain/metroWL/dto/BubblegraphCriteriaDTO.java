package org.stock.portal.domain.metroWL.dto;

import java.util.Date;



public class BubblegraphCriteriaDTO extends AbstractCriteriaDTO {

	private static final long serialVersionUID = 1L;
	
	private Date fromDate;
	private Date toDate;
	
	private String bseCode;
	private String nseCode;
	
	private Long selectedWatchlist;
	private Long selectedSector;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}
	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}
	public Long getSelectedSector() {
		return selectedSector;
	}
	public void setSelectedSector(Long selectedSector) {
		this.selectedSector = selectedSector;
	}
	
	
}
