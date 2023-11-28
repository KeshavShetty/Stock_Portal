package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the analysis_history database table.
 * 
 */
@Entity
@Table(name="analysis_history")
public class AnalysisHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="analysis_type")
	private Integer analysisType;

	@Column(name="buy_description", length=3000)
	private String buyDescription;

	@Column(name="buy_indicators")
	private Integer buyIndicators;

	private Float cmp;

	@Column(name="data_date")
	@Temporal(TemporalType.DATE)
	private Date dataDate;

	@Column(name="exchange_code")
	private Boolean exchangeCode;

	@Column(name="sell_description", length=3000)
	private String selDescription;

	@Column(name="sell_indicators")
	private Integer sellIndicators;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="scrip_id")
	private Scrip scrip;

    public AnalysisHistory() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAnalysisType() {
		return this.analysisType;
	}

	public void setAnalysisType(Integer analysisType) {
		this.analysisType = analysisType;
	}

	public String getBuyDescription() {
		return this.buyDescription;
	}

	public void setBuyDescription(String buyDescription) {
		this.buyDescription = buyDescription;
	}

	public Integer getBuyIndicators() {
		return this.buyIndicators;
	}

	public void setBuyIndicators(Integer buyIndicators) {
		this.buyIndicators = buyIndicators;
	}

	public Float getCmp() {
		return this.cmp;
	}

	public void setCmp(Float cmp) {
		this.cmp = cmp;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Boolean getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getSelDescription() {
		return this.selDescription;
	}

	public void setSelDescription(String selDescription) {
		this.selDescription = selDescription;
	}

	public Integer getSellIndicators() {
		return this.sellIndicators;
	}

	public void setSellIndicators(Integer sellIndicators) {
		this.sellIndicators = sellIndicators;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}
	
}