package org.stock.portal.domain.metroWL.dto;

public class CandlePatternSearchCriteriaDTO extends AbstractCriteriaDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer minUp;
    private Integer minDown;    
    private Float minCountRank;
    
	public Integer getMinUp() {
		return minUp;
	}
	public void setMinUp(Integer minUp) {
		this.minUp = minUp;
	}
	public Integer getMinDown() {
		return minDown;
	}
	public void setMinDown(Integer minDown) {
		this.minDown = minDown;
	}
	public Float getMinCountRank() {
		return minCountRank;
	}
	public void setMinCountRank(Float minCountRank) {
		this.minCountRank = minCountRank;
	}
	
	

}
