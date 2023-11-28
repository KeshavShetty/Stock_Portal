package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the watchlist_item database table.
 * 
 */
@Entity
@Table(name="autoscan_study_result")
public class AutoscanResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	@Column(name="signal_date")
	@Temporal(TemporalType.DATE)
	private Date signalDate;
	
	//bi-directional many-to-one association to AutoscanMaster
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_study_master")
	private AutoscanMaster studyMaster;	
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="signal_code")
	private Float signalCode;
	
	@Column(name="previous_close")
	private Float previousClose;
	
	@Column(name="exchange_code")
	private Boolean exchangeCode;

	@Column(name="signal_path")
	private String signalPath;
	
    public AutoscanResult() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Date getSignalDate() {
		return signalDate;
	}

	public void setSignalDate(Date signalDate) {
		this.signalDate = signalDate;
	}

	public AutoscanMaster getStudyMaster() {
		return studyMaster;
	}

	public void setStudyMaster(AutoscanMaster studyMaster) {
		this.studyMaster = studyMaster;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Float getSignalCode() {
		return signalCode;
	}

	public void setSignalCode(Float signalCode) {
		this.signalCode = signalCode;
	}

	public Float getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(Float previousClose) {
		this.previousClose = previousClose;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getSignalPath() {
		return signalPath;
	}

	public void setSignalPath(String signalPath) {
		this.signalPath = signalPath;
	}
	
}