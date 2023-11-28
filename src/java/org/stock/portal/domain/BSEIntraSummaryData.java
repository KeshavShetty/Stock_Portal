package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the eod_data database table.
 * 
 */
@Entity
@Table(name="bse_intraday_summary_data")
@NamedQueries({
	@NamedQuery(name = "BSEIntraSummaryData.getDataByBseCode",
			query = "SELECT OBJECT(bseIntraData) "
			+ "FROM BSEIntraSummaryData bseIntraData where bseIntraData.scrip.bseName = :bseCode order by bseIntraData.dataDate asc")
})
public class BSEIntraSummaryData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;
	
	@Column(name="data_date")
	@Temporal(TemporalType.DATE)
	private Date dataDate;
	
	@Column(name="open_price")
	private Float openPrice;
	
	@Column(name="high_price")
	private Float highPrice;
	
	@Column(name="low_price")
	private Float lowPrice;
	
	@Column(name="close_price")
	private Float closePrice;

	@Column(name="last_price")
	private Float lastPrice;

	@Column(name="total_volume")
	private Long totalVolume;

	@Column(name="highprice_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date highPriceTime;
	
	@Column(name="lowprice_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lowPriceTime;	

	@Column(name="previousday_close_price")
	private Float previousdayClosePrice;
	
	@Column(name="previousday_volume")
	private Long previousdayVolume;
	
    public BSEIntraSummaryData() {
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Date getDataDate() {
		return dataDate;
	}


	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Float openPrice) {
		this.openPrice = openPrice;
	}

	public Float getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Float highPrice) {
		this.highPrice = highPrice;
	}

	public Float getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Float getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Float closePrice) {
		this.closePrice = closePrice;
	}

	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Long totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Date getHighPriceTime() {
		return highPriceTime;
	}

	public void setHighPriceTime(Date highPriceTime) {
		this.highPriceTime = highPriceTime;
	}

	public Date getLowPriceTime() {
		return lowPriceTime;
	}

	public void setLowPriceTime(Date lowPriceTime) {
		this.lowPriceTime = lowPriceTime;
	}

	public Float getPreviousdayClosePrice() {
		return previousdayClosePrice;
	}

	public void setPreviousdayClosePrice(Float previousdayClosePrice) {
		this.previousdayClosePrice = previousdayClosePrice;
	}

	public Long getPreviousdayVolume() {
		return previousdayVolume;
	}

	public void setPreviousdayVolume(Long previousdayVolume) {
		this.previousdayVolume = previousdayVolume;
	}	
}