package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the scrips database table.
 * 
 */
@Entity
@Table(name="scrips")
@NamedQueries({
	@NamedQuery(name = "Scrip.getScripsBySearchString",
			query = "SELECT OBJECT(scr) "
			+ " FROM Scrip scr where scr.bseCode like :bseCode " +
					" or scr.nseCode like :nseCode " +
					" or scr.bseName like :bseName " +
					" or UPPER(scr.name) like :scripName " +
					" order by scr.name asc"),
	@NamedQuery(name = "Scrip.getScripsById",
			query = "SELECT OBJECT(scr) "
			+ " FROM Scrip scr where scr.id = :scripId")				
})
public class Scrip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="bse_code", length=25)
	private String bseCode;	

	@Column(name="icici_code", length=25)
	private String iciciCode;
	
	@Column(length=100)
	private String name;
	
	@Column(name="nse_code", length=25)
	private String nseCode;

	//bi-directional many-to-one association to Sector
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_sector")
	private Sector sector;
	
	//bi-directional many-to-one association to Sector
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_mcsector")
	private MCSector mcsector;

	@Column(name="bse_cmp")
	private Float bseCmp;
	
	@Column(name="bse_name", length=25)
	private String bseName;
	
	@Column(name="bse_todays_gain")
	private Float bseTodaysGain;
	
	@Column(name="bse_previous_close")
	private Float bsePreviousClose;

	@Column(name="icici_name", length=100)
	private String icicName;

	@Column(name="strong_buy_rec")
	private Integer strongBuyRec;

	@Column(name="strong_sell_rec")
	private Integer strongSellRec;

	@Column(name="symbol_type", length=5)
	private String scripType;

	@Column(name="series_type", length=5)
	private String seriesType;

	@Column(name="status", length=25)
	private String status;

	@Column(name="mc_code", length=25)
	private String mcCode;

	@Column(name="et_code", length=25)
	private String etCode;

	@Column(name="isin_code", length=25)
	private String isinCode;

	@Column(name="bse_group", length=25)
	private String bseGroup;

	@Column(name="bse_index", length=25)
	private String bseIndex;	

	@Column(name="face_value")
	private Float faceValue;

	@Column(name="date_added")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAdded;
			
	@Column(name="nse_cmp")
	private Float nseCmp;
	
	@Column(name="nse_todays_gain")
	private Float nseTodaysGain;
	
	@Column(name="nse_previous_close")
	private Float nsePreviousClose;
	
	@Column(name="bse_52week_high")
	private Float bse52weekHigh;
	
	@Column(name="bse_52week_high_date")
	@Temporal(TemporalType.DATE)
	private Date bse52weekHighDate;
	
	@Column(name="bse_52week_low")
	private Float bse52weekLow;
	
	@Column(name="bse_52week_low_date")
	@Temporal(TemporalType.DATE)
	private Date bse52weekLowDate;
	
	@Column(name="last_updated")
	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;
	
	@Column(name="eps_ttm")
	private Float eps;
	
	@Column(name="pe")
	private Float pe;
	
	@Column(name="bse_avg_volume")
	private Long bseAverageVolume;
	
	@Column(name="bse_previous_volume")
	private Long previousvolume;
	
	@Column(name="bse_todays_volume") // (Todo:Exchange specific volume)
	private Long todaysVolume;
	
	@Column(name="book_value")
	private Float bookValue;
	
	@Column(name="dividend_yield_percent")
	private Float dividendYield;
	
	@Column(name="nse_avg_volume")
	private Long nseAverageVolume;
	
	@Column(name="nse_todays_volume")
	private Long nseTodaysVolume;
	
	@Column(name="result_date")
	@Temporal(TemporalType.DATE)
	private Date resultDate;
	
	@Column(name="change_in_netprofit")
	private Float changeProfit;
	
	@Column(name="raising_profit_qtr_count")
	private Float raisingProfitQtrCount;
	
	@Column(name="average_four_qtr_netprofit")
	private Float averageFourQtrNetprofit;
	
	@Column(name="profit_margin_percentage")
	private Float profitMarginPercentage;
	
	@Column(name="avg_volume")
	private Long averageVolume;
	
	@Column(name="average_turnover")
	private Float averageTurnover;
	
	@Column(name="cmp")
	private Float cmp;
	
	@Column(name="todays_gain")
	private Float todaysGain;
	
	@Column(name="price_oneday_before")
	private Float priceOnedayBefore;
	
	@Column(name="price_fiveday_before")
	private Float priceFivedayBefore;
	
	@Column(name="price_onemonth_before")
	private Float priceOnemonthBefore;
	
	@Column(name="price_threemonth_before")
	private Float priceThreemonthBefore;
	
	@Column(name="raising_closeprice_count")
	private Float raisingClosepriceCount;
	
	@Column(name="avg_qtr_net_return_by_closeprice")
	private Float avgQtrNetReturnByCloseprice; 
		
	
	@Column(name="last_qtr_closeprice")
	private Float lastQtrCloseprice;
	
	
	@Column(name="number_day_close_above30dema")
	private Float countOfDayCloseAbove30demaIn52W;
	
	@Column(name="number_day_close_below30dema")
	private Float countOfDayCloseBelow30demaIn52W;
	
	@Column(name="stochastic_value")
	private Float stochasticValue;
	
	@Column(name="is_raising_stochastic")
	private Boolean raising_stochastic;
	
	
	@Column(name="ema100day")
	private Float ema100day;
	
	@Column(name="day_bar_size_percent")
	private Float dayBarSizePercent;
	
	@Column(name="fixed_assets_turnover")
	private Float fixedAssetsTurnover;
	
	@Column(name="return_on_equity")
	private Float returnOnEquity;
	
	@Column(name="cash_ratio")
	private Float cashRatio;
	
	@Column(name="operating_profit_margin")
	private Float operatingProfitMargin;
	
	@Column(name="net_profit_margin")
	private Float netProfitMargin;
	
	@Column(name="return_on_capital_employed")
	private Float returnOnCapitalEmployed;
	
	@Column(name="gross_profit_margin")
	private Float grossProfitMargin;
	
	@Column(name="debt_equity_ratio")
	private Float debtEquityRatio;
	
	@Column(name="return_on_assets")
	private Float returnOnAssets;
	
	@Column(name="cash_flows_to_long_term_debt")
	private Float cashFlowsToLongTermDebt;
	
	@Column(name="quick_ratio")
	private Float quick_ratio;
	
	@Column(name="current_ratio")
	private Float current_ratio;
	
	@Column(name="nse_volume_vs_avg_volume")
	private Float nseVolumeVsAvgVolumeRatio;
	
	
	@Column(name="raisingfourqtrprofitcount")
	private Float raisingfourqtrprofitcount;
	
	@Column(name="raisingfourqtrclosepricecount")
	private Float raisingfourqtrclosepricecount;
		
	@Column(name="last_same_qtr_growth_percentage")
	private Float lastSameQtrGrowthPercentage;
	
	@Column(name="raisingSameQtrCount")
	private Float raisingSameQtrCount;
	
	@Column(name="averageFourSameQuarterNetprofit")
	private Float averageFourSameQuarterNetprofit;
	
	@Column(name="avg_volume_onemonth")
	private Long averageVolume1M;
	
	@Column(name="support_price_3m")
	private Float supportPrice3M;
	
	@Column(name="support_volume_leftover_3m")
	private Long supportVolume3M;
	
	@Column(name="ema15day")
	private Float ema15day;
	
	@Column(name="ema30day")
	private Float ema30day;
	
	@Column(name="ema50day")
	private Float ema50day;
	
	@Column(name="latest_financial_report_quarter_id")
	private Integer latestFinancialReportQuarterId;
	
	@Column(name="marketcap")
	private Float marketcap;
	
	@Column(name="revenuechangepercent")
	private Float revenuechangepercent;
	
	@Column(name="revenuesameqtrchangepercent")
	private Float revenuesameqtrchangepercent;
	
	@Column(name="wlcount")
	private Integer wlCount;
	
	@Column(name="wl_roi_score")
	private Integer wlScore;
	
	@Column(name="wl_score_rank")
	private Integer wlScoreRank;
	
	@Column(name="growth_promise_percentage")
	private Integer standlaoneGrowthPromisePercentage;
	
	@Column(name="growth_promise_percentage_consolidated")
	private Integer consolidatedGrowthPromisePercentage;
	
	@Column(name="growth_rank")
	private Integer growthRank;
	
	@Column(name="next_result_date")
	@Temporal(TemporalType.DATE)
	private Date nextResultDate;
	
	@Column(name="lastqtrnetprofitamount")
	private Float lastQtrNetprofitAmount;
	
	@Column(name="oneyearnetprofitamount")
	private Float oneyearNetprofitAmount;
	
	@Column(name="lastqtrinterestamount")
	private Float lastQtrInterestAmount;
	
	@Column(name="oneyearinterestamount")
	private Float oneyearInterestAmount;
	
	@Column(name="estimated_next_qtr_sqg")
	private Float estimatedNextQtrSqg;
	
	@Column(name="mc_industry_pe")
	private Float industryPe;
	
	@Column(name="profitmarginchangepercent")
	private Float profitmarginchangepercent;
	
	@Column(name="profitmarginSameQtrchangepercent")
	private Float profitmarginSameQtrchangepercent;
	
	@Column(name="future_lot_size")
	private Integer lotSize;
		
	@Column(name="marketsmojo_id", length=25)
	private String marketsmojoId;
	
	@Column(name="instrument_token", length=25)
	private String instrumentToken;
	
	@Column(name="stockadda_id", length=25)
	private String stockaddaId;
	
	@Column(name="tijorifinance_id", length=50)
	private String tijoriFinanceId;
	
	@Column(name="reuters_id", length=50)
	private String reutersId;
	
	@Column(name="trendlyne_id", length=50)
	private String trendlyneId;
	
	@Column(name="tickertape_id", length=50)
	private String tickertapeId;
	
	
	@Column(name="simplywallst_id", length=50)
	private String simplywallstId;
	
	
	@Column(name="profitmarginpbtchangepercent")
	private Float profitmarginPbtchangepercent;
	
	@Column(name="profitmarginpbtsameqtrchangepercent")
	private Float profitmarginPbtsameqtrchangepercent;
	
	@Column(name="targetpricebypb")
	private Float targetpricebypb;
	
	@Column(name="targetpricebype")
	private Float targetpricebype;
	

	@Column(name="change_in_roe")
	private Float changeInRoe;
	
	@Column(name="change_in_roce")
	private Float changeInRoce;
	
	@Column(name="entry_wl_score")
	private Double entryWlScore;
	
	public Scrip() {
    }

	public Float getBseCmp() {
		return this.bseCmp;
	}

	public String getBseCode() {
		return this.bseCode;
	}

	public String getBseGroup() {
		return bseGroup;
	}

	public String getBseIndex() {
		return bseIndex;
	}

	public String getBseName() {
		return this.bseName;
	}

	public Float getBsePreviousClose() {
		return bsePreviousClose;
	}

	public Float getBseTodaysGain() {
		return bseTodaysGain;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public String getEtCode() {
		return etCode;
	}

	public Float getFaceValue() {
		return faceValue;
	}

	public String getIciciCode() {
		return this.iciciCode;
	}

	public String getIcicName() {
		return this.icicName;
	}

	public Long getId() {
		return this.id;
	}

	public String getIsinCode() {
		return isinCode;
	}
	
	public String getMcCode() {
		return mcCode;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Float getNseCmp() {
		return nseCmp;
	}
	
	public String getNseCode() {
		return this.nseCode;
	}
	
	public Float getNsePreviousClose() {
		return nsePreviousClose;
	}

	public Float getNseTodaysGain() {
		return nseTodaysGain;
	}
	
	
	public String getScripType() {
		return this.scripType;
	}
	
	public Sector getSector() {
		return sector;
	}
	
	public MCSector getMcsector() {
		return mcsector;
	}

	public void setMcsector(MCSector mcsector) {
		this.mcsector = mcsector;
	}

	public String getSeriesType() {
		return this.seriesType;
	}
	
    public String getStatus() {
		return status;
	}

	public Integer getStrongBuyRec() {
		return this.strongBuyRec;
	}

	public Integer getStrongSellRec() {
		return this.strongSellRec;
	}

	public String getTijoriFinanceId() {
		return tijoriFinanceId;
	}

	public void setTijoriFinanceId(String tijoriFinanceId) {
		this.tijoriFinanceId = tijoriFinanceId;
	}

	public void setBseCmp(Float bseCmp) {
		this.bseCmp = bseCmp;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public void setBseGroup(String bseGroup) {
		this.bseGroup = bseGroup;
	}

	public void setBseIndex(String bseIndex) {
		this.bseIndex = bseIndex;
	}

	public void setBseName(String bseName) {
		this.bseName = bseName;
	}

	public void setBsePreviousClose(Float bsePreviousClose) {
		this.bsePreviousClose = bsePreviousClose;
	}

	public void setBseTodaysGain(Float bseTodaysGain) {
		this.bseTodaysGain = bseTodaysGain;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setEtCode(String etCode) {
		this.etCode = etCode;
	}
	
	public void setFaceValue(Float faceValue) {
		this.faceValue = faceValue;
	}

	public void setIciciCode(String iciciCode) {
		this.iciciCode = iciciCode;
	}

	public void setIcicName(String icicName) {
		this.icicName = icicName;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public void setMcCode(String mcCode) {
		this.mcCode = mcCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNseCmp(Float nseCmp) {
		this.nseCmp = nseCmp;
	}
	
	

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
	}

	public void setNsePreviousClose(Float nsePreviousClose) {
		this.nsePreviousClose = nsePreviousClose;
	}

	public void setNseTodaysGain(Float nseTodaysGain) {
		this.nseTodaysGain = nseTodaysGain;
	}

	public void setScripType(String scripType) {
		this.scripType = scripType;
	}

	
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStrongBuyRec(Integer strongBuyRec) {
		this.strongBuyRec = strongBuyRec;
	}

	public void setStrongSellRec(Integer strongSellRec) {
		this.strongSellRec = strongSellRec;
	}

	public Float getBse52weekHigh() {
		return bse52weekHigh;
	}

	public void setBse52weekHigh(Float bse52weekHigh) {
		this.bse52weekHigh = bse52weekHigh;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Float getEps() {
		return eps;
	}

	public void setEps(Float eps) {
		this.eps = eps;
	}

	public Float getPe() {
		return pe;
	}

	public void setPe(Float pe) {
		this.pe = pe;
	}

	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
	}

	public Long getPreviousvolume() {
		return previousvolume;
	}

	public void setPreviousvolume(Long previousvolume) {
		this.previousvolume = previousvolume;
	}

	public Long getTodaysVolume() {
		return todaysVolume;
	}

	public void setTodaysVolume(Long todaysVolume) {
		this.todaysVolume = todaysVolume;
	}

	public Date getBse52weekHighDate() {
		return bse52weekHighDate;
	}

	public void setBse52weekHighDate(Date bse52weekHighDate) {
		this.bse52weekHighDate = bse52weekHighDate;
	}

	public Float getBookValue() {
		return bookValue;
	}

	public void setBookValue(Float bookValue) {
		this.bookValue = bookValue;
	}

	public Float getDividendYield() {
		return dividendYield;
	}

	public void setDividendYield(Float dividendYield) {
		this.dividendYield = dividendYield;
	}

	public Float getBse52weekLow() {
		return bse52weekLow;
	}

	public void setBse52weekLow(Float bse52weekLow) {
		this.bse52weekLow = bse52weekLow;
	}

	public Date getBse52weekLowDate() {
		return bse52weekLowDate;
	}

	public void setBse52weekLowDate(Date bse52weekLowDate) {
		this.bse52weekLowDate = bse52weekLowDate;
	}

	public Long getNseAverageVolume() {
		return nseAverageVolume;
	}

	public void setNseAverageVolume(Long nseAverageVolume) {
		this.nseAverageVolume = nseAverageVolume;
	}

	public Float getBookPerEps() { //Dummy method
		Float retVal = 0f;
		if (eps!=null && eps>0 && bookValue!=null && bookValue>0) {
			retVal = bookValue/eps;
		}
		return retVal;
	}
	
	public Float getEpsToBookPercentage() { //Dummy method
		Float retVal = 0f;
		if (eps!=null && eps>0 && bookValue!=null && bookValue>0) {
			retVal = (eps/bookValue)*100;
		}
		return retVal;
	}
	
	public Float getPbRatio() { //Dummy method
		Float retVal = 0f;
		Float cmpToUse = 0f ;
		if (this.nseCmp!=null) cmpToUse = this.nseCmp;
		else if (this.bseCmp!=null) cmpToUse = this.bseCmp;
		if (bookValue!=null && bookValue!=0) {
			retVal = cmpToUse/bookValue;
		}
		return retVal;
	}
	
	public Float getGrahamScoreVsCmp() { // "sqrt(abs((22.5*scr.eps_ttm*scr.book_value)))/NULLIF(scr.cmp,0) as GrahamNumberVsCMP"
		Float retVal = -0.01f;
		Float cmpToUse = 0f ;
		if (this.nseCmp!=null) cmpToUse = this.nseCmp;
		else if (this.bseCmp!=null) cmpToUse = this.bseCmp;
		if (bookValue!=null && bookValue>0 && eps!=null && eps>0) {
			retVal = (float) (Math.sqrt(22.5f*eps*bookValue)/cmpToUse);
		}
		return retVal;
	}
	
	public String getShortName() {
		String retString = "";
		if (this.name!=null && this.name.length()>0) {
			if (this.name.length()>25) retString = this.name.substring(0,25)+"..";
			else retString = this.name;
		}
		return retString;
	}
	
	

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public Float getChangeProfit() {
		return changeProfit;
	}

	public void setChangeProfit(Float changeProfit) {
		this.changeProfit = changeProfit;
	}

	public Float getRaisingProfitQtrCount() {
		return raisingProfitQtrCount;
	}

	public void setRaisingProfitQtrCount(Float raisingProfitQtrCount) {
		this.raisingProfitQtrCount = raisingProfitQtrCount;
	}

	public Float getAverageFourQtrNetprofit() {
		return averageFourQtrNetprofit;
	}

	public void setAverageFourQtrNetprofit(Float averageFourQtrNetprofit) {
		this.averageFourQtrNetprofit = averageFourQtrNetprofit;
	}

	public Float getProfitMarginPercentage() {
		return profitMarginPercentage;
	}

	public void setProfitMarginPercentage(Float profitMarginPercentage) {
		this.profitMarginPercentage = profitMarginPercentage;
	}

	public Long getBseAverageVolume() {
		return bseAverageVolume;
	}

	public void setBseAverageVolume(Long bseAverageVolume) {
		this.bseAverageVolume = bseAverageVolume;
	}

	public Float getAverageTurnover() {
		return averageTurnover;
	}

	public void setAverageTurnover(Float averageTurnover) {
		this.averageTurnover = averageTurnover;
	}

	public Float getCmp() {
		return cmp;
	}

	public void setCmp(Float cmp) {
		this.cmp = cmp;
	}

	public Float getTodaysGain() {
		return todaysGain;
	}

	public void setTodaysGain(Float todaysGain) {
		this.todaysGain = todaysGain;
	}

	public Float getPriceOnedayBefore() {
		return priceOnedayBefore;
	}

	public void setPriceOnedayBefore(Float priceOnedayBefore) {
		this.priceOnedayBefore = priceOnedayBefore;
	}

	public Float getPriceFivedayBefore() {
		return priceFivedayBefore;
	}

	public void setPriceFivedayBefore(Float priceFivedayBefore) {
		this.priceFivedayBefore = priceFivedayBefore;
	}

	public Float getPriceOnemonthBefore() {
		return priceOnemonthBefore;
	}

	public void setPriceOnemonthBefore(Float priceOnemonthBefore) {
		this.priceOnemonthBefore = priceOnemonthBefore;
	}

	public Float getPriceThreemonthBefore() {
		return priceThreemonthBefore;
	}

	public void setPriceThreemonthBefore(Float priceThreemonthBefore) {
		this.priceThreemonthBefore = priceThreemonthBefore;
	}

	public Float getRaisingClosepriceCount() {
		return raisingClosepriceCount;
	}

	public void setRaisingClosepriceCount(Float raisingClosepriceCount) {
		this.raisingClosepriceCount = raisingClosepriceCount;
	}

	public Float getAvgQtrNetReturnByCloseprice() {
		return avgQtrNetReturnByCloseprice;
	}

	public void setAvgQtrNetReturnByCloseprice(Float avgQtrNetReturnByCloseprice) {
		this.avgQtrNetReturnByCloseprice = avgQtrNetReturnByCloseprice;
	}

	public Float getLastQtrCloseprice() {
		return lastQtrCloseprice;
	}

	public void setLastQtrCloseprice(Float lastQtrCloseprice) {
		this.lastQtrCloseprice = lastQtrCloseprice;
	}

	public Float getCountOfDayCloseAbove30demaIn52W() {
		return countOfDayCloseAbove30demaIn52W;
	}

	public void setCountOfDayCloseAbove30demaIn52W(
			Float countOfDayCloseAbove30demaIn52W) {
		this.countOfDayCloseAbove30demaIn52W = countOfDayCloseAbove30demaIn52W;
	}

	public Float getCountOfDayCloseBelow30demaIn52W() {
		return countOfDayCloseBelow30demaIn52W;
	}

	public void setCountOfDayCloseBelow30demaIn52W(
			Float countOfDayCloseBelow30demaIn52W) {
		this.countOfDayCloseBelow30demaIn52W = countOfDayCloseBelow30demaIn52W;
	}
	
	public Float getRatioOf52WCloseAboveBelow30DEMA() {
		if (countOfDayCloseBelow30demaIn52W!=0) {
			return countOfDayCloseAbove30demaIn52W/countOfDayCloseBelow30demaIn52W;
		} else {
			return 0f;
		}
	}

	public Float getStochasticValue() {
		return stochasticValue;
	}

	public void setStochasticValue(Float stochasticValue) {
		this.stochasticValue = stochasticValue;
	}

	public Boolean getRaising_stochastic() {
		return raising_stochastic;
	}

	public void setRaising_stochastic(Boolean raising_stochastic) {
		this.raising_stochastic = raising_stochastic;
	}

	public Float getEma100day() {
		return ema100day;
	}

	public void setEma100day(Float ema100day) {
		this.ema100day = ema100day;
	}

	public Float getDayBarSizePercent() {
		return dayBarSizePercent;
	}

	public void setDayBarSizePercent(Float dayBarSizePercent) {
		this.dayBarSizePercent = dayBarSizePercent;
	}

	public Float getFixedAssetsTurnover() {
		return fixedAssetsTurnover;
	}

	public void setFixedAssetsTurnover(Float fixedAssetsTurnover) {
		this.fixedAssetsTurnover = fixedAssetsTurnover;
	}

	public Float getReturnOnEquity() {
		return returnOnEquity;
	}

	public void setReturnOnEquity(Float returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	public Float getCashRatio() {
		return cashRatio;
	}

	public void setCashRatio(Float cashRatio) {
		this.cashRatio = cashRatio;
	}

	public Float getOperatingProfitMargin() {
		return operatingProfitMargin;
	}

	public void setOperatingProfitMargin(Float operatingProfitMargin) {
		this.operatingProfitMargin = operatingProfitMargin;
	}

	public Float getNetProfitMargin() {
		return netProfitMargin;
	}

	public void setNetProfitMargin(Float netProfitMargin) {
		this.netProfitMargin = netProfitMargin;
	}

	public Float getReturnOnCapitalEmployed() {
		return returnOnCapitalEmployed;
	}

	public void setReturnOnCapitalEmployed(Float returnOnCapitalEmployed) {
		this.returnOnCapitalEmployed = returnOnCapitalEmployed;
	}

	public Float getGrossProfitMargin() {
		return grossProfitMargin;
	}

	public void setGrossProfitMargin(Float grossProfitMargin) {
		this.grossProfitMargin = grossProfitMargin;
	}

	public Float getDebtEquityRatio() {
		return debtEquityRatio;
	}

	public void setDebtEquityRatio(Float debtEquityRatio) {
		this.debtEquityRatio = debtEquityRatio;
	}

	public Float getReturnOnAssets() {
		return returnOnAssets;
	}

	public void setReturnOnAssets(Float returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	public Float getCashFlowsToLongTermDebt() {
		return cashFlowsToLongTermDebt;
	}

	public void setCashFlowsToLongTermDebt(Float cashFlowsToLongTermDebt) {
		this.cashFlowsToLongTermDebt = cashFlowsToLongTermDebt;
	}

	public Float getQuick_ratio() {
		return quick_ratio;
	}

	public void setQuick_ratio(Float quick_ratio) {
		this.quick_ratio = quick_ratio;
	}

	public Float getCurrent_ratio() {
		return current_ratio;
	}

	public void setCurrent_ratio(Float current_ratio) {
		this.current_ratio = current_ratio;
	}

	public Float getNseVolumeVsAvgVolumeRatio() {
		return nseVolumeVsAvgVolumeRatio;
	}

	public void setNseVolumeVsAvgVolumeRatio(Float nseVolumeVsAvgVolumeRatio) {
		this.nseVolumeVsAvgVolumeRatio = nseVolumeVsAvgVolumeRatio;
	}

	public Float getNseVolumeVsAvgVolumeAmount() {
		return (nseTodaysVolume-nseAverageVolume)*nseCmp/(10000000f);
	}
	

	public Float getRaisingfourqtrprofitcount() {
		return raisingfourqtrprofitcount;
	}

	public void setRaisingfourqtrprofitcount(Float raisingfourqtrprofitcount) {
		this.raisingfourqtrprofitcount = raisingfourqtrprofitcount;
	}

	public Float getRaisingfourqtrclosepricecount() {
		return raisingfourqtrclosepricecount;
	}

	public void setRaisingfourqtrclosepricecount(Float raisingfourqtrclosepricecount) {
		this.raisingfourqtrclosepricecount = raisingfourqtrclosepricecount;
	}

	public Float getLastSameQtrGrowthPercentage() {
		return lastSameQtrGrowthPercentage;
	}

	public void setLastSameQtrGrowthPercentage(Float lastSameQtrGrowthPercentage) {
		this.lastSameQtrGrowthPercentage = lastSameQtrGrowthPercentage;
	}

	public Float getRaisingSameQtrCount() {
		return raisingSameQtrCount;
	}

	public void setRaisingSameQtrCount(Float raisingSameQtrCount) {
		this.raisingSameQtrCount = raisingSameQtrCount;
	}

	public Float getAverageFourSameQuarterNetprofit() {
		return averageFourSameQuarterNetprofit;
	}

	public void setAverageFourSameQuarterNetprofit(
			Float averageFourSameQuarterNetprofit) {
		this.averageFourSameQuarterNetprofit = averageFourSameQuarterNetprofit;
	}

	public Long getAverageVolume1M() {
		return averageVolume1M;
	}

	public void setAverageVolume1M(Long averageVolume1M) {
		this.averageVolume1M = averageVolume1M;
	}

	public Float getSupportPrice3M() {
		return supportPrice3M;
	}

	public void setSupportPrice3M(Float supportPrice3M) {
		this.supportPrice3M = supportPrice3M;
	}

	public Long getSupportVolume3M() {
		return supportVolume3M;
	}

	public void setSupportVolume3M(Long supportVolume3M) {
		this.supportVolume3M = supportVolume3M;
	}

	public Float getEma15day() {
		return ema15day;
	}

	public void setEma15day(Float ema15day) {
		this.ema15day = ema15day;
	}

	public Float getEma30day() {
		return ema30day;
	}

	public void setEma30day(Float ema30day) {
		this.ema30day = ema30day;
	}

	public Float getEma50day() {
		return ema50day;
	}

	public void setEma50day(Float ema50day) {
		this.ema50day = ema50day;
	}

	public Integer getLatestFinancialReportQuarterId() {
		return latestFinancialReportQuarterId;
	}

	public void setLatestFinancialReportQuarterId(
			Integer latestFinancialReportQuarterId) {
		this.latestFinancialReportQuarterId = latestFinancialReportQuarterId;
	}

	public Float getMarketcap() {
		return marketcap;
	}

	public void setMarketcap(Float marketcap) {
		this.marketcap = marketcap;
	}

	public Float getRevenuechangepercent() {
		return revenuechangepercent;
	}

	public void setRevenuechangepercent(Float revenuechangepercent) {
		this.revenuechangepercent = revenuechangepercent;
	}

	public Float getRevenuesameqtrchangepercent() {
		return revenuesameqtrchangepercent;
	}

	public void setRevenuesameqtrchangepercent(Float revenuesameqtrchangepercent) {
		this.revenuesameqtrchangepercent = revenuesameqtrchangepercent;
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

	public Integer getWlScoreRank() {
		return wlScoreRank;
	}

	public void setWlScoreRank(Integer wlScoreRank) {
		this.wlScoreRank = wlScoreRank;
	}

	public Integer getStandlaoneGrowthPromisePercentage() {
		return standlaoneGrowthPromisePercentage;
	}

	public void setStandlaoneGrowthPromisePercentage(Integer standlaoneGrowthPromisePercentage) {
		this.standlaoneGrowthPromisePercentage = standlaoneGrowthPromisePercentage;
	}

	public Integer getConsolidatedGrowthPromisePercentage() {
		return consolidatedGrowthPromisePercentage;
	}

	public void setConsolidatedGrowthPromisePercentage(Integer consolidatedGrowthPromisePercentage) {
		this.consolidatedGrowthPromisePercentage = consolidatedGrowthPromisePercentage;
	}

	public Date getNextResultDate() {
		return nextResultDate;
	}

	public void setNextResultDate(Date nextResultDate) {
		this.nextResultDate = nextResultDate;
	}

	public Integer getGrowthRank() {
		return growthRank;
	}

	public void setGrowthRank(Integer growthRank) {
		this.growthRank = growthRank;
	}

	public Float getLastQtrNetprofitAmount() {
		return lastQtrNetprofitAmount;
	}

	public void setLastQtrNetprofitAmount(Float lastQtrNetprofitAmount) {
		this.lastQtrNetprofitAmount = lastQtrNetprofitAmount;
	}

	public Float getOneyearNetprofitAmount() {
		return oneyearNetprofitAmount;
	}

	public void setOneyearNetprofitAmount(Float oneyearNetprofitAmount) {
		this.oneyearNetprofitAmount = oneyearNetprofitAmount;
	}

	public Float getLastQtrInterestAmount() {
		return lastQtrInterestAmount;
	}

	public void setLastQtrInterestAmount(Float lastQtrInterestAmount) {
		this.lastQtrInterestAmount = lastQtrInterestAmount;
	}

	public Float getOneyearInterestAmount() {
		return oneyearInterestAmount;
	}

	public void setOneyearInterestAmount(Float oneyearInterestAmount) {
		this.oneyearInterestAmount = oneyearInterestAmount;
	}

	public Float getEstimatedNextQtrSqg() {
		return estimatedNextQtrSqg;
	}

	public void setEstimatedNextQtrSqg(Float estimatedNextQtrSqg) {
		this.estimatedNextQtrSqg = estimatedNextQtrSqg;
	}

	public Float getIndustryPe() {
		return industryPe;
	}

	public void setIndustryPe(Float industryPe) {
		this.industryPe = industryPe;
	}

	public Float getProfitmarginchangepercent() {
		return profitmarginchangepercent;
	}

	public void setProfitmarginchangepercent(Float profitmarginchangepercent) {
		this.profitmarginchangepercent = profitmarginchangepercent;
	}

	public Float getProfitmarginSameQtrchangepercent() {
		return profitmarginSameQtrchangepercent;
	}

	public void setProfitmarginSameQtrchangepercent(Float profitmarginSameQtrchangepercent) {
		this.profitmarginSameQtrchangepercent = profitmarginSameQtrchangepercent;
	}

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}

	public String getMarketsmojoId() {
		return marketsmojoId;
	}

	public void setMarketsmojoId(String marketsmojoId) {
		this.marketsmojoId = marketsmojoId;
	}

	public String getInstrumentToken() {
		return instrumentToken;
	}

	public void setInstrumentToken(String instrumentToken) {
		this.instrumentToken = instrumentToken;
	}

	public String getStockaddaId() {
		return stockaddaId;
	}

	public void setStockaddaId(String stockaddaId) {
		this.stockaddaId = stockaddaId;
	}

	public String getReutersId() {
		return reutersId;
	}

	public void setReutersId(String reutersId) {
		this.reutersId = reutersId;
	}

	public String getTrendlyneId() {
		return trendlyneId;
	}

	public void setTrendlyneId(String trendlyneId) {
		this.trendlyneId = trendlyneId;
	}

	public String getTickertapeId() {
		return tickertapeId;
	}

	public void setTickertapeId(String tickertapeId) {
		this.tickertapeId = tickertapeId;
	}

	public Float getProfitmarginPbtchangepercent() {
		return profitmarginPbtchangepercent;
	}

	public void setProfitmarginPbtchangepercent(Float profitmarginPbtchangepercent) {
		this.profitmarginPbtchangepercent = profitmarginPbtchangepercent;
	}

	public Float getProfitmarginPbtsameqtrchangepercent() {
		return profitmarginPbtsameqtrchangepercent;
	}

	public void setProfitmarginPbtsameqtrchangepercent(Float profitmarginPbtsameqtrchangepercent) {
		this.profitmarginPbtsameqtrchangepercent = profitmarginPbtsameqtrchangepercent;
	}

	public Float getTargetpricebypb() {
		return targetpricebypb;
	}

	public void setTargetpricebypb(Float targetpricebypb) {
		this.targetpricebypb = targetpricebypb;
	}

	public Float getTargetpricebype() {
		return targetpricebype;
	}

	public void setTargetpricebype(Float targetpricebype) {
		this.targetpricebype = targetpricebype;
	}

	public Float getChangeInRoe() {
		return changeInRoe;
	}

	public void setChangeInRoe(Float changeInRoe) {
		this.changeInRoe = changeInRoe;
	}

	public Float getChangeInRoce() {
		return changeInRoce;
	}

	public void setChangeInRoce(Float changeInRoce) {
		this.changeInRoce = changeInRoce;
	}

	public Long getNseTodaysVolume() {
		return nseTodaysVolume;
	}

	public void setNseTodaysVolume(Long nseTodaysVolume) {
		this.nseTodaysVolume = nseTodaysVolume;
	}

	public String getSimplywallstId() {
		return simplywallstId;
	}

	public void setSimplywallstId(String simplywallstId) {
		this.simplywallstId = simplywallstId;
	}

	public Double getEntryWlScore() {
		return entryWlScore;
	}

	public void setEntryWlScore(Double entryWlScore) {
		this.entryWlScore = entryWlScore;
	}
	
}