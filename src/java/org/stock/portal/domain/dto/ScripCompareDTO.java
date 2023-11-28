package org.stock.portal.domain.dto;

import java.io.Serializable;


public class ScripCompareDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String exchangeCode;	
	private String name;
	
	private float pe;
	private float pb;
	
	private float qtrRevenue;
	private float qtrProfit;	
	private float qtrProfitMargin;
	
	private float sqg;
	private float roce;
	private float roceChange;
	
	private float priceByPBVsPrice;
	private float priceByPEVsPrice;
	
	
	public ScripCompareDTO(long id, String exchangeCode, String name, float pe, float pb, float qtrRevenue,
			float qtrProfit, float qtrProfitMargin, float sqg, float roce, float roceChange, float priceByPBVsPrice, float priceByPEVsPrice) {
		super();
		this.id = id;
		this.exchangeCode = exchangeCode;
		this.name = name;
		this.pe = pe;
		this.pb = pb;
		this.qtrRevenue = qtrRevenue;
		this.qtrProfit = qtrProfit;
		this.qtrProfitMargin = qtrProfitMargin;
		this.sqg = sqg;
		this.roce = roce;
		this.roceChange = roceChange;
		this.priceByPBVsPrice = priceByPBVsPrice;
		this.priceByPEVsPrice = priceByPEVsPrice;
		
	}

	public ScripCompareDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public float getPe() {
		return pe;
	}

	public void setPe(float pe) {
		this.pe = pe;
	}

	public float getPb() {
		return pb;
	}

	public void setPb(float pb) {
		this.pb = pb;
	}

	public float getQtrRevenue() {
		return qtrRevenue;
	}

	public void setQtrRevenue(float qtrRevenue) {
		this.qtrRevenue = qtrRevenue;
	}

	public float getQtrProfit() {
		return qtrProfit;
	}

	public void setQtrProfit(float qtrProfit) {
		this.qtrProfit = qtrProfit;
	}

	public float getQtrProfitMargin() {
		return qtrProfitMargin;
	}

	public void setQtrProfitMargin(float qtrProfitMargin) {
		this.qtrProfitMargin = qtrProfitMargin;
	}
	
	public float getSqg() {
		return sqg;
	}

	public void setSqg(float sqg) {
		this.sqg = sqg;
	}

	public float getRoce() {
		return roce;
	}

	public void setRoce(float roce) {
		this.roce = roce;
	}

	public float getRoceChange() {
		return roceChange;
	}

	public void setRoceChange(float roceChange) {
		this.roceChange = roceChange;
	}

	public float getPriceByPBVsPrice() {
		return priceByPBVsPrice;
	}

	public void setPriceByPBVsPrice(float priceByPBVsPrice) {
		this.priceByPBVsPrice = priceByPBVsPrice;
	}

	public float getPriceByPEVsPrice() {
		return priceByPEVsPrice;
	}

	public void setPriceByPEVsPrice(float priceByPEVsPrice) {
		this.priceByPEVsPrice = priceByPEVsPrice;
	}
	
}