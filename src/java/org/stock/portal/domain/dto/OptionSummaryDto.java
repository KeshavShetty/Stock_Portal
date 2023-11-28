package org.stock.portal.domain.dto;

import java.util.Date;

public class OptionSummaryDto {
	
	private Long id;
	private Date recordDate;
	private String indexName;
	
	private int totalCEOI;
	private int totalPEOI;
	
	private int totalChangeInCEOI;
	private int totalChangeInPEOI;
	
	private float changeInCEOIFromPreviousTick;
	private float changeInPEOIFromPreviousTick;
	
	private float changeInCEOIFrom920Tick;
	private float changeInPEOIFrom920Tick;

	private float pcr;
	
	private float minCEWithHighestOI;
	private float maxPEWithHighestOI;
	
	private float underlyingValue;
	
	private float optionSellerMeanValue;
	
	private float optionSellerMeanValueByWorth;
	
	private float bestCostEffectiveStrike;
	private float bestCostEffectiveStrikeByWorth;
	
	
	private float optionSellerMeanValueFrom5;
	private float bestCostEffectiveStrikeFrom5;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public int getTotalCEOI() {
		return totalCEOI;
	}

	public void setTotalCEOI(int totalCEOI) {
		this.totalCEOI = totalCEOI;
	}

	public int getTotalPEOI() {
		return totalPEOI;
	}

	public void setTotalPEOI(int totalPEOI) {
		this.totalPEOI = totalPEOI;
	}

	public int getTotalChangeInCEOI() {
		return totalChangeInCEOI;
	}

	public void setTotalChangeInCEOI(int totalChangeInCEOI) {
		this.totalChangeInCEOI = totalChangeInCEOI;
	}

	public int getTotalChangeInPEOI() {
		return totalChangeInPEOI;
	}

	public void setTotalChangeInPEOI(int totalChangeInPEOI) {
		this.totalChangeInPEOI = totalChangeInPEOI;
	}

	public float getChangeInCEOIFromPreviousTick() {
		return changeInCEOIFromPreviousTick;
	}

	public void setChangeInCEOIFromPreviousTick(float changeInCEOIFromPreviousTick) {
		this.changeInCEOIFromPreviousTick = changeInCEOIFromPreviousTick;
	}

	public float getChangeInPEOIFromPreviousTick() {
		return changeInPEOIFromPreviousTick;
	}

	public void setChangeInPEOIFromPreviousTick(float changeInPEOIFromPreviousTick) {
		this.changeInPEOIFromPreviousTick = changeInPEOIFromPreviousTick;
	}

	public float getChangeInCEOIFrom920Tick() {
		return changeInCEOIFrom920Tick;
	}

	public void setChangeInCEOIFrom920Tick(float changeInCEOIFrom920Tick) {
		this.changeInCEOIFrom920Tick = changeInCEOIFrom920Tick;
	}

	public float getChangeInPEOIFrom920Tick() {
		return changeInPEOIFrom920Tick;
	}

	public void setChangeInPEOIFrom920Tick(float changeInPEOIFrom920Tick) {
		this.changeInPEOIFrom920Tick = changeInPEOIFrom920Tick;
	}

	public float getPcr() {
		return pcr;
	}

	public void setPcr(float pcr) {
		this.pcr = pcr;
	}

	public float getMinCEWithHighestOI() {
		return minCEWithHighestOI;
	}

	public void setMinCEWithHighestOI(float minCEWithHighestOI) {
		this.minCEWithHighestOI = minCEWithHighestOI;
	}

	public float getMaxPEWithHighestOI() {
		return maxPEWithHighestOI;
	}

	public void setMaxPEWithHighestOI(float maxPEWithHighestOI) {
		this.maxPEWithHighestOI = maxPEWithHighestOI;
	}

	public float getUnderlyingValue() {
		return underlyingValue;
	}

	public void setUnderlyingValue(float underlyingValue) {
		this.underlyingValue = underlyingValue;
	}

	public float getOptionSellerMeanValue() {
		return optionSellerMeanValue;
	}

	public void setOptionSellerMeanValue(float optionSellerMeanValue) {
		this.optionSellerMeanValue = optionSellerMeanValue;
	}

	public float getOptionSellerMeanValueByWorth() {
		return optionSellerMeanValueByWorth;
	}

	public void setOptionSellerMeanValueByWorth(float optionSellerMeanValueByWorth) {
		this.optionSellerMeanValueByWorth = optionSellerMeanValueByWorth;
	}

	public float getBestCostEffectiveStrike() {
		return bestCostEffectiveStrike;
	}

	public void setBestCostEffectiveStrike(float bestCostEffectiveStrike) {
		this.bestCostEffectiveStrike = bestCostEffectiveStrike;
	}

	public float getBestCostEffectiveStrikeByWorth() {
		return bestCostEffectiveStrikeByWorth;
	}

	public void setBestCostEffectiveStrikeByWorth(float bestCostEffectiveStrikeByWorth) {
		this.bestCostEffectiveStrikeByWorth = bestCostEffectiveStrikeByWorth;
	}

	public float getOptionSellerMeanValueFrom5() {
		return optionSellerMeanValueFrom5;
	}

	public void setOptionSellerMeanValueFrom5(float optionSellerMeanValueFrom5) {
		this.optionSellerMeanValueFrom5 = optionSellerMeanValueFrom5;
	}

	public float getBestCostEffectiveStrikeFrom5() {
		return bestCostEffectiveStrikeFrom5;
	}

	public void setBestCostEffectiveStrikeFrom5(float bestCostEffectiveStrikeFrom5) {
		this.bestCostEffectiveStrikeFrom5 = bestCostEffectiveStrikeFrom5;
	}
	
}
