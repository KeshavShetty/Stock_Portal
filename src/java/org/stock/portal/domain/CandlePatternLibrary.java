package org.stock.portal.domain;

import java.io.Serializable;

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


/**
 * The persistent class for the watchlist_item database table.
 * 
 */
@Entity
@Table(name="candle_pattern_library")
public class CandlePatternLibrary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="CANDLE_PATTERN_ID_GEN", allocationSize=1, sequenceName="CANDLE_PATTERN_LIBRARY_ID_SEQ")
    @GeneratedValue(generator="CANDLE_PATTERN_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
    @Column(name="ID")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_parent_id")
	private CandlePatternLibrary parentPattern;

	@Column(name="is_day_or_level")
	private Boolean isDayOrLevel;
	
	@Column(name="day_number")
	private Integer dayNumber;
	
	@Column(name="patternid")
	private Integer patternId;
	
	@Column(name="up_percent")
	private Float upPercent;
	
	@Column(name="down_percent")
	private Float downPercent;
	
	@Column(name="up_count")
	private Integer upCount;
	
	@Column(name="down_count")
	private Integer downCount;
	
	@Column(name="count_rank")
	private Float countRank;
	
	@Column(name="roi_rank")
	private Float roiRank;
	
	@Column(name="up_percent_3day")
	private Float upPercent3Day;
	
	@Column(name="down_percent_3day")
	private Float downPercent3Day;
	
	@Column(name="up_count_3day")
	private Integer upCount3Day;
	
	@Column(name="down_count_3day")
	private Integer downCount3Day;
	
	@Column(name="count_rank_3day")
	private Float countRank3Day;
	
	@Column(name="roi_rank_3day")
	private Float roiRank3Day;
	
    public CandlePatternLibrary() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandlePatternLibrary getParentPattern() {
		return parentPattern;
	}

	public void setParentPattern(CandlePatternLibrary parentPattern) {
		this.parentPattern = parentPattern;
	}
	

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Integer getPatternId() {
		return patternId;
	}

	public void setPatternId(Integer patternId) {
		this.patternId = patternId;
	}

	public Float getUpPercent() {
		return upPercent;
	}

	public void setUpPercent(Float upPercent) {
		this.upPercent = upPercent;
	}

	public Float getDownPercent() {
		return downPercent;
	}

	public void setDownPercent(Float downPercent) {
		this.downPercent = downPercent;
	}

	public Integer getUpCount() {
		return upCount;
	}

	public void setUpCount(Integer upCount) {
		this.upCount = upCount;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Float getCountRank() {
		return countRank;
	}

	public void setCountRank(Float countRank) {
		this.countRank = countRank;
	}

	public Float getRoiRank() {
		return roiRank;
	}

	public void setRoiRank(Float roiRank) {
		this.roiRank = roiRank;
	}

	public Boolean getIsDayOrLevel() {
		return isDayOrLevel;
	}

	public void setIsDayOrLevel(Boolean isDayOrLevel) {
		this.isDayOrLevel = isDayOrLevel;
	}
	
	public String getPatternName() {	
		String retString = "";
		if (patternId!=null) {
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

	public Float getUpPercent3Day() {
		return upPercent3Day;
	}

	public void setUpPercent3Day(Float upPercent3Day) {
		this.upPercent3Day = upPercent3Day;
	}

	public Float getDownPercent3Day() {
		return downPercent3Day;
	}

	public void setDownPercent3Day(Float downPercent3Day) {
		this.downPercent3Day = downPercent3Day;
	}

	public Integer getUpCount3Day() {
		return upCount3Day;
	}

	public void setUpCount3Day(Integer upCount3Day) {
		this.upCount3Day = upCount3Day;
	}

	public Integer getDownCount3Day() {
		return downCount3Day;
	}

	public void setDownCount3Day(Integer downCount3Day) {
		this.downCount3Day = downCount3Day;
	}

	public Float getCountRank3Day() {
		return countRank3Day;
	}

	public void setCountRank3Day(Float countRank3Day) {
		this.countRank3Day = countRank3Day;
	}

	public Float getRoiRank3Day() {
		return roiRank3Day;
	}

	public void setRoiRank3Day(Float roiRank3Day) {
		this.roiRank3Day = roiRank3Day;
	}

}