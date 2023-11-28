package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the eod_data database table.
 * 
 */
@Entity
@Table(name="bse_eq_eod_data")
@NamedQueries({
	@NamedQuery(name = "BSEEodData.getEodDataByBseCode",
			query = "SELECT OBJECT(eodData) "
			+ "FROM BSEEodData eodData where eodData.scrip.bseName = :bseCode order by eodData.dataDate asc"),
			
    @NamedQuery(name = "BSEEodData.getBSEEodDataByBseCodeAndDateRange",
    		query = "SELECT OBJECT(eodData) " + 
    		" FROM BSEEodData eodData join fetch eodData.scrip where eodData.dataDate BETWEEN :fromDate AND :toDate " + 
			" AND (eodData.scrip.bseName = :bseName or eodData.scrip.bseCode = :bseCode)" + 
    		" order by eodData.dataDate asc")
})
public class BSEEodData implements EodData,Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="close_price")
	private Float closePrice;

	@Column(name="data_date")
	@Temporal(TemporalType.DATE)
	private Date dataDate;

	@Column(name="high_price")
	private Float highPrice;

	@Column(name="low_price")
	private Float lowPrice;

	@Column(name="open_interest")
	private Long openInterest;

	@Column(name="open_price")
	private Float openPrice;

	@Column(name="previous_close")
	private Float previousClose;

	private Long volume;
	
	@Column(name="previous_volume")
	private Long previousVolume;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	@Column(name="stochastic_value")
	private Float stochasticValue;
	
	@Column(name="price_move_trend")
	private Boolean priceMoveTrend;
	
    public BSEEodData() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getClosePrice() {
		return this.closePrice;
	}

	public void setClosePrice(Float closePrice) {
		this.closePrice = closePrice;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	
	public Float getHighPrice() {
		return this.highPrice;
	}

	public void setHighPrice(Float highPrice) {
		this.highPrice = highPrice;
	}

	public Float getLowPrice() {
		return this.lowPrice;
	}

	public void setLowPrice(Float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Long getOpenInterest() {
		return this.openInterest;
	}

	public void setOpenInterest(Long openInterest) {
		this.openInterest = openInterest;
	}

	public Float getOpenPrice() {
		return this.openPrice;
	}

	public void setOpenPrice(Float openPrice) {
		this.openPrice = openPrice;
	}

	public Float getPreviousClose() {
		return this.previousClose;
	}

	public void setPreviousClose(Float previousClose) {
		this.previousClose = previousClose;
	}

	public Long getVolume() {
		return this.volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Long getPreviousVolume() {
		return previousVolume;
	}

	public void setPreviousVolume(Long previousVolume) {
		this.previousVolume = previousVolume;
	}

	public Float getStochasticValue() {
		return stochasticValue;
	}

	public void setStochasticValue(Float stochasticValue) {
		this.stochasticValue = stochasticValue;
	}

	public Boolean getPriceMoveTrend() {
		return priceMoveTrend;
	}

	public void setPriceMoveTrend(Boolean priceMoveTrend) {
		this.priceMoveTrend = priceMoveTrend;
	}
	
}