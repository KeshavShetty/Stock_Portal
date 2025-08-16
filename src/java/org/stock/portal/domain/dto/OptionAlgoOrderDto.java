package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The Scrip EOD Dto class
 * 
 */
public class OptionAlgoOrderDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String optionName;
	private float sellPrice;
	private float buyPrice;
	private Date entryTime;
	private Date exitTime;
	private String algoname;
	
	public OptionAlgoOrderDto() {
		super();
	}

	public OptionAlgoOrderDto(Long id, String optionName, String algoname, float sellPrice, Date entryTime, Date exitTime, float buyPrice) {
		super();
		this.id = id;
		this.optionName = optionName;
		this.algoname = algoname;
		this.sellPrice = sellPrice;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.buyPrice = buyPrice;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public String getAlgoname() {
		return algoname;
	}

	public void setAlgoname(String algoname) {
		this.algoname = algoname;
	}

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

	
			
	
	
}