package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="btst_autoscan_result")
public class IntradayBTSTAutoscanResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	@Column(name="exchange")
	private Boolean exchangeCode;
	
	@Column(name="signal_date")
	@Temporal(TemporalType.DATE)
	private Date signalDate;
	
	@Column(name="signal_price")
	private Float signalPrice;
	
	//bi-directional many-to-one association to AutoscanMaster
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_study_master")
	private AutoscanMaster studyMaster;	

    public IntradayBTSTAutoscanResult() {
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

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Float getSignalPrice() {
		return signalPrice;
	}

	public void setSignalPrice(Float signalPrice) {
		this.signalPrice = signalPrice;
	}
	
}