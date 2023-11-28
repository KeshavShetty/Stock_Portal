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
@Table(name="tree_performance")
public class TreePerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_source_scrip")
	private Scrip sourceScrip;
	
	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_target_scrip")
	private Scrip targetScrip;
	
	@Column(name="joint_increment")
	private Integer joinIncrement;
	
	@Column(name="total_source_gain")
	private Float totalSourceGain;

	@Column(name="total_target_gain")
	private Float totalTargetGain;
	
    public TreePerformance() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Scrip getSourceScrip() {
		return sourceScrip;
	}

	public void setSourceScrip(Scrip sourceScrip) {
		this.sourceScrip = sourceScrip;
	}

	public Scrip getTargetScrip() {
		return targetScrip;
	}

	public void setTargetScrip(Scrip targetScrip) {
		this.targetScrip = targetScrip;
	}

	public Integer getJoinIncrement() {
		return joinIncrement;
	}

	public void setJoinIncrement(Integer joinIncrement) {
		this.joinIncrement = joinIncrement;
	}

	public Float getTotalSourceGain() {
		return totalSourceGain;
	}

	public void setTotalSourceGain(Float totalSourceGain) {
		this.totalSourceGain = totalSourceGain;
	}

	public Float getTotalTargetGain() {
		return totalTargetGain;
	}

	public void setTotalTargetGain(Float totalTargetGain) {
		this.totalTargetGain = totalTargetGain;
	}
	
}