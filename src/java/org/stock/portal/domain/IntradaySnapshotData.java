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

@Entity
@Table(name="intraday_snapshot_data")
public class IntradaySnapshotData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;
	
	@Column(name="data_date")
	@Temporal(TemporalType.DATE)
	private Date dataDate;
	
	@Column(name="exchange_code")
	private Boolean exchangeCode;
	
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

	@Column(name="mean_price")
	private Float meanPrice;
	
	@Column(name="highprice_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date highpriceTime;
	
	@Column(name="lowprice_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lowpriceTime;	

	@Column(name="total_volume")
	private Long totalVolume;

	@Column(name="volume_b4_3")
	private Long volumeBefore3;
	
	@Column(name="volume_a3_b4_315")
	private Long volumeAfter3Before315;
	
	@Column(name="cf_weightage")
	private Float cfWeightage;
	
	@Column(name="mf_aboveclose_days")
	private Integer mfAbovecloseDays;
	
	@Column(name="lowest_low_90day")
	private Boolean lowestLow;
	
	@Column(name="highest_high_90day")
	private Boolean highestHigh;
	
	@Column(name="cf_rating_90day")
	private Float cfRating;
	
	@Column(name="highest_onemin_turnover")
	private Float highestOneminTurnover;
	
	@Column(name="highest_onemin_volume")
	private Long highestOneminVolume;
	
    public IntradaySnapshotData() {
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

	public Float getOpenPrice() {
		return this.openPrice;
	}

	public void setOpenPrice(Float openPrice) {
		this.openPrice = openPrice;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Float getMeanPrice() {
		return meanPrice;
	}

	public void setMeanPrice(Float meanPrice) {
		this.meanPrice = meanPrice;
	}

	public Date getHighpriceTime() {
		return highpriceTime;
	}

	public void setHighpriceTime(Date highpriceTime) {
		this.highpriceTime = highpriceTime;
	}

	public Date getLowpriceTime() {
		return lowpriceTime;
	}

	public void setLowpriceTime(Date lowpriceTime) {
		this.lowpriceTime = lowpriceTime;
	}

	public Long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Long totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Long getVolumeBefore3() {
		return volumeBefore3;
	}

	public void setVolumeBefore3(Long volumeBefore3) {
		this.volumeBefore3 = volumeBefore3;
	}

	public Long getVolumeAfter3Before315() {
		return volumeAfter3Before315;
	}

	public void setVolumeAfter3Before315(Long volumeAfter3Before315) {
		this.volumeAfter3Before315 = volumeAfter3Before315;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	
	public Float getVolumePerformance() {
		return volumeAfter3Before315.floatValue()/volumeBefore3.floatValue();
	}

	public Float getCfWeightage() {
		return cfWeightage;
	}

	public void setCfWeightage(Float cfWeightage) {
		this.cfWeightage = cfWeightage;
	}

	public Integer getMfAbovecloseDays() {
		return mfAbovecloseDays;
	}

	public void setMfAbovecloseDays(Integer mfAbovecloseDays) {
		this.mfAbovecloseDays = mfAbovecloseDays;
	}

	public Boolean getLowestLow() {
		return lowestLow;
	}

	public void setLowestLow(Boolean lowestLow) {
		this.lowestLow = lowestLow;
	}

	public Boolean getHighestHigh() {
		return highestHigh;
	}

	public void setHighestHigh(Boolean highestHigh) {
		this.highestHigh = highestHigh;
	}

	public Float getCfRating() {
		return cfRating;
	}

	public void setCfRating(Float cfRating) {
		this.cfRating = cfRating;
	}
	
	public Float getBarSize() {
		float retVal =0;
		if (this.openPrice>0) {
			retVal = (this.closePrice - this.openPrice)*100f/this.openPrice;
		}
		return retVal;
	}

	public Float getHighestOneminTurnover() {
		return highestOneminTurnover;
	}

	public void setHighestOneminTurnover(Float highestOneminTurnover) {
		this.highestOneminTurnover = highestOneminTurnover;
	}

	public Long getHighestOneminVolume() {
		return highestOneminVolume;
	}

	public void setHighestOneminVolume(Long highestOneminVolume) {
		this.highestOneminVolume = highestOneminVolume;
	}

}