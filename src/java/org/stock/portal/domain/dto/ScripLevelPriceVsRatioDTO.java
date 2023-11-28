package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.util.Date;


public class ScripLevelPriceVsRatioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date dataDate;
	private float cmp;
	private float pe;
	private float pb; 
	private float roce;
	private float roceChange;
	
	public ScripLevelPriceVsRatioDTO() {
		
    }

	public ScripLevelPriceVsRatioDTO(Date dataDate, float cmp, float pe, float pb, float roce, float roceChange) {
		super();
		this.cmp = cmp;
		this.dataDate = dataDate;
		this.pe = pe;
		this.pb = pb;
		this.roce = roce;
		this.roceChange = roceChange;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
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

	public float getCmp() {
		return cmp;
	}

	public void setCmp(float cmp) {
		this.cmp = cmp;
	}
	
}