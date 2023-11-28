package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;


public class ScripBubbleGraphDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String bseCode;	
	private String nseCode;
	private Date dataDate;
	
	private Integer wlCount;
	private Integer wlScore;
	private Integer growthRank;
	private Integer wlScoreRank;
	
	private Float sqgRevenue;
	private Float sqgProfit;
	private Float sqgProfitMargin; 
	
	private Float actualProfitMarginLastQtr;
	
	public ScripBubbleGraphDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBseCode() {
		return bseCode;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNseCode() {
		return nseCode;
	}

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Integer getWlCount() {
		return wlCount;
	}

	public void setWlCount(Integer wlCount) {
		this.wlCount = wlCount;
	}

	public Integer getWlScore() {
		return wlScore;
	}

	public void setWlScore(Integer wlScore) {
		this.wlScore = wlScore;
	}

	public Integer getGrowthRank() {
		return growthRank;
	}

	public void setGrowthRank(Integer growthRank) {
		this.growthRank = growthRank;
	}

	public Integer getWlScoreRank() {
		return wlScoreRank;
	}

	public void setWlScoreRank(Integer wlScoreRank) {
		this.wlScoreRank = wlScoreRank;
	}

	public Float getSqgRevenue() {
		return sqgRevenue;
	}

	public void setSqgRevenue(Float sqgRevenue) {
		this.sqgRevenue = sqgRevenue;
	}

	public Float getSqgProfit() {
		return sqgProfit;
	}

	public void setSqgProfit(Float sqgProfit) {
		this.sqgProfit = sqgProfit;
	}

	public Float getSqgProfitMargin() {
		return sqgProfitMargin;
	}

	public void setSqgProfitMargin(Float sqgProfitMargin) {
		this.sqgProfitMargin = sqgProfitMargin;
	}

	public Float getActualProfitMarginLastQtr() {
		return actualProfitMarginLastQtr;
	}

	public void setActualProfitMarginLastQtr(Float actualProfitMarginLastQtr) {
		this.actualProfitMarginLastQtr = actualProfitMarginLastQtr;
	}

	
	

	
}