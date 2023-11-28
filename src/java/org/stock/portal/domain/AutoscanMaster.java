package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the Autoscan master table.
 * 
 */
@Entity
@Table(name="autoscan_study_library")
public class AutoscanMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="study_type")
	private Integer studyType;

	@Column(name="shortname")
	private String shortName;

	@Column(name="study_name")
	private String studyName;
	
	@Column(name="study_parameter")
	private String study_parameter;
	
	@Column(name="description")
	private String description;

	@Column(name="is_individual")
	private Integer isIndividual;
	
	@Column(name="tutorial_url")
	private String tutorialUrl;
	
	@Column(name="retrieve_order")
	private Integer retrieveOrder;
	
    public AutoscanMaster() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getStudyType() {
		return studyType;
	}

	public void setStudyType(Integer studyType) {
		this.studyType = studyType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudy_parameter() {
		return study_parameter;
	}

	public void setStudy_parameter(String studyParameter) {
		study_parameter = studyParameter;
	}

	public Integer getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(Integer isIndividual) {
		this.isIndividual = isIndividual;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTutorialUrl() {
		return tutorialUrl;
	}

	public void setTutorialUrl(String tutorialUrl) {
		this.tutorialUrl = tutorialUrl;
	}
	
	
	public String getStudyNameForGUI() {
		String retResult = "";
		if (this.studyName!=null) {
			if (this.studyName.length()<=20) {
				retResult = this.studyName;
			} else {
				retResult = this.studyName.substring(0,20)+"..";
			}
		} else {
			retResult = "";
		}
		if (this.study_parameter!=null && this.study_parameter.length()>0) {
			retResult = retResult + "(" + this.study_parameter + ")";
		}
		return retResult;
	}

	public void setStudyNameForGUI(String studyName) {
		this.studyName = studyName;
	}
	
	public String getName() {
		String retString="";
		if (this.studyName!=null) retString = retString + this.studyName;
		if (this.study_parameter!=null) retString = retString + this.study_parameter;
		return retString;
	}

	public Integer getRetrieveOrder() {
		return retrieveOrder;
	}

	public void setRetrieveOrder(Integer retrieveOrder) {
		this.retrieveOrder = retrieveOrder;
	}
	
}