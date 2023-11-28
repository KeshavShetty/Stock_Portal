package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="consolidated_financial_result")
public class ConsolidatedFinancialResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;
	
	@Column(name="financial_report_quarter_id")
	private Integer financiaReportQuarterId;
	
	@Column(name="revenue")
	private Float revenue;
	
	@Column(name="other_income")
	private Float otherIncome;
	
	@Column(name="total_income")
	private Float totalIncome;
	
	@Column(name="expenditure")
	private Float expenditure;
	
	@Column(name="interest")
	private Float interest;
	
	@Column(name="pbdt")
	private Float pbdt;
	
	@Column(name="depreciation")
	private Float depreciation;
	
	@Column(name="pbt")
	private Float pbt;
	
	@Column(name="tax")
	private Float tax;
	
	@Column(name="net_profit")
	private Float netProfit;
	
	@Column(name="equity")
	private Float equity;
	
	@Column(name="eps")
	private Float eps;
	
	@Column(name="ceps")
	private Float ceps;
	
	@Column(name="opm_percentage")
	private Float opmPercentage;
	
	@Column(name="npm_percentage")
	private Float npmPercentage;
	
	@Column(name="car_percentage")
	private Float carPercentage;
	
	@Column(name="qtr_close_price")
	private Float qtClosePrice;
	
	@Column(name="gross_profit_margin")
	private Float grossProfitMargin;
	
    public ConsolidatedFinancialResult() {
    }

	public Long getId() {
		return this.id;
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

	public Integer getFinanciaReportQuarterId() {
		return financiaReportQuarterId;
	}

	public void setFinanciaReportQuarterId(Integer financiaReportQuarterId) {
		this.financiaReportQuarterId = financiaReportQuarterId;
	}

	public Float getRevenue() {
		return revenue;
	}

	public void setRevenue(Float revenue) {
		this.revenue = revenue;
	}

	public Float getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Float otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Float getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Float totalIncome) {
		this.totalIncome = totalIncome;
	}

	public Float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Float expenditure) {
		this.expenditure = expenditure;
	}

	public Float getInterest() {
		return interest;
	}

	public void setInterest(Float interest) {
		this.interest = interest;
	}

	public Float getPbdt() {
		return pbdt;
	}

	public void setPbdt(Float pbdt) {
		this.pbdt = pbdt;
	}

	public Float getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(Float depreciation) {
		this.depreciation = depreciation;
	}

	public Float getPbt() {
		return pbt;
	}

	public void setPbt(Float pbt) {
		this.pbt = pbt;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public Float getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(Float netProfit) {
		this.netProfit = netProfit;
	}

	public Float getEquity() {
		return equity;
	}

	public void setEquity(Float equity) {
		this.equity = equity;
	}

	public Float getGrossProfitMargin() {
		return grossProfitMargin;
	}

	public void setGrossProfitMargin(Float grossProfitMargin) {
		this.grossProfitMargin = grossProfitMargin;
	}

	public Float getEps() {
		return eps;
	}

	public void setEps(Float eps) {
		this.eps = eps;
	}

	public Float getCeps() {
		return ceps;
	}

	public void setCeps(Float ceps) {
		this.ceps = ceps;
	}

	public Float getOpmPercentage() {
		return opmPercentage;
	}

	public void setOpmPercentage(Float opmPercentage) {
		this.opmPercentage = opmPercentage;
	}

	public Float getNpmPercentage() {
		return npmPercentage;
	}

	public void setNpmPercentage(Float npmPercentage) {
		this.npmPercentage = npmPercentage;
	}

	public Float getCarPercentage() {
		return carPercentage;
	}

	public void setCarPercentage(Float carPercentage) {
		this.carPercentage = carPercentage;
	}

	public Float getQtClosePrice() {
		return qtClosePrice;
	}

	public void setQtClosePrice(Float qtClosePrice) {
		this.qtClosePrice = qtClosePrice;
	}
	
}