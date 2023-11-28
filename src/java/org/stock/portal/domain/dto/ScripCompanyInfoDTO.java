package org.stock.portal.domain.dto;

import java.io.Serializable;


public class ScripCompanyInfoDTO implements Serializable {
	public ScripCompanyInfoDTO(Long id, String name, String bseCode, String nseCode, Float rank) {
		super();
		this.id = id;
		this.name = name;
		this.bseCode = bseCode;
		this.nseCode = nseCode;
		this.rank = rank;
		this.companyInfo = companyInfo;
	}

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Float rank;
	
	private String name;
	
	private String companyInfo;
	
	private String bseCode; 
	private String nseCode; 
	
	public ScripCompanyInfoDTO() {
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

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getBseCode() {
		return bseCode;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public String getNseCode() {
		return nseCode;
	}

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
	}

	public Float getRank() {
		return rank;
	}

	public void setRank(Float rank) {
		this.rank = rank;
	}	
}