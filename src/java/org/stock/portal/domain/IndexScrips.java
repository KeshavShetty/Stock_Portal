package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="index_scrips")
public class IndexScrips implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="INDEX_SCRIPS_ID_GEN", allocationSize=1, sequenceName="INDEX_SCRIPS_ID_SEQ")
    @GeneratedValue(generator="INDEX_SCRIPS_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
    @Column(name="ID")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_index_scrip")
	private Scrip indexScrip;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;
		
    public IndexScrips() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Scrip getIndexScrip() {
		return indexScrip;
	}

	public void setIndexScrip(Scrip indexScrip) {
		this.indexScrip = indexScrip;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}	
	
}