package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="candle_pattern_scrip_result")
public class CandlePatternScripMatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="CANDLE_PATTERN_SCRIP_ID_GEN", allocationSize=1, sequenceName="CANDLE_PATTERN_SCRIP_RESULT_ID_SEQ")
    @GeneratedValue(generator="CANDLE_PATTERN_SCRIP_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
    @Column(name="ID")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip_id")
	private Scrip scrip;
	
	@Column(name="pattern_date")
	@Temporal(TemporalType.DATE)
	private Date patternDate;
	
	@Column(name="exchange_code")
	private Boolean exchangeCode;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_5day_patternid")
	private CandlePatternLibrary fiveDayPattern;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_4day_patternid")
	private CandlePatternLibrary fourDayPattern;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_3day_patternid")
	private CandlePatternLibrary threeDayPattern;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_2day_patternid")
	private CandlePatternLibrary twoDayPattern;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_1day_patternid")
	private CandlePatternLibrary oneDayPattern;
	
	@Column(name="library_status")
	private boolean libraryStatus;
	
	@Column(name="price_at_signal")
	private Float priceAtSignal;
	
    public CandlePatternScripMatch() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public String getPatternName() {	
		String retString = "";
		if (fiveDayPattern.getPatternId()!=null) {
			Integer patternId = fiveDayPattern.getPatternId();
			if (patternId==57) retString = retString +  "*ONLY STICK*";
			else {
				if (patternId>28) {
					retString = retString +  "SHORT";
					patternId = patternId-28;
				} else {
					retString = retString +  "LONG";
				}
				if (patternId>14) {
					retString = retString +  "-DARK";
					patternId = patternId-14;
				} else {
					retString = retString +  "-WHITE";
				}
				if (patternId == 1) retString = retString +  "-CAPITAL_T";
				else if (patternId == 2) retString = retString +  "-UPPER_T";
				else if (patternId == 3) retString = retString +  "-CENTER_T";
				else if (patternId == 4) retString = retString +  "-LOWER_T";
				else if (patternId == 5) retString = retString +  "-REVERSE_T";
	
				else if (patternId == 6) retString = retString +  "-HAMMER";
				else if (patternId == 7) retString = retString +  "-UPPER_HAMMER";
				else if (patternId == 8) retString = retString +  "-CENTER_HAMMER";
				else if (patternId == 9) retString = retString +  "-LOWER_HAMMER";
				else if (patternId == 10) retString = retString +  "-REVERSE_HAMMER";
				
				else if (patternId == 11) retString = retString +  "-UPPER_BAT";	
				else if (patternId == 12) retString = retString +  "-LOWER_BAT";
				else if (patternId == 13) retString = retString +  "-CENTER_BAT";
				else if (patternId == 14) retString = retString +  "-FULL_BAT";			
			}
		}
		return retString;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Date getPatternDate() {
		return patternDate;
	}

	public void setPatternDate(Date patternDate) {
		this.patternDate = patternDate;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public CandlePatternLibrary getFiveDayPattern() {
		return fiveDayPattern;
	}

	public void setFiveDayPattern(CandlePatternLibrary fiveDayPattern) {
		this.fiveDayPattern = fiveDayPattern;
	}

	public CandlePatternLibrary getFourDayPattern() {
		return fourDayPattern;
	}

	public void setFourDayPattern(CandlePatternLibrary fourDayPattern) {
		this.fourDayPattern = fourDayPattern;
	}

	public CandlePatternLibrary getThreeDayPattern() {
		return threeDayPattern;
	}

	public void setThreeDayPattern(CandlePatternLibrary threeDayPattern) {
		this.threeDayPattern = threeDayPattern;
	}

	public CandlePatternLibrary getTwoDayPattern() {
		return twoDayPattern;
	}

	public void setTwoDayPattern(CandlePatternLibrary twoDayPattern) {
		this.twoDayPattern = twoDayPattern;
	}

	public CandlePatternLibrary getOneDayPattern() {
		return oneDayPattern;
	}

	public void setOneDayPattern(CandlePatternLibrary oneDayPattern) {
		this.oneDayPattern = oneDayPattern;
	}

	public boolean isLibraryStatus() {
		return libraryStatus;
	}

	public void setLibraryStatus(boolean libraryStatus) {
		this.libraryStatus = libraryStatus;
	}

	public Float getPriceAtSignal() {
		return priceAtSignal;
	}

	public void setPriceAtSignal(Float priceAtSignal) {
		this.priceAtSignal = priceAtSignal;
	}

}