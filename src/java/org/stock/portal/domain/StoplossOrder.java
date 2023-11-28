package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the stoploss_order database table.
 * 
 */
@Entity
@Table(name="stoploss_order")
public class StoplossOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=1000)
	private String description;

	@Column(name="effective_date")
	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	@Column(length=255)
	private String name;

	@Column(name="order_rate")
	private Float orderRate;

	private Boolean status;

	@Column(name="target_date")
	@Temporal(TemporalType.DATE)
	private Date targetDate;

	@Column(name="target_rate")
	private Float targetRate;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_bse_scrip")
	private Scrip scrip;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner")
	private User user;

	//bi-directional many-to-one association to StoplossOrderTransaction
	@OneToMany(mappedBy="stoplossOrder")
	private Set<StoplossOrderTransaction> stoplossOrderTransactions;

    public StoplossOrder() {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getOrderRate() {
		return this.orderRate;
	}

	public void setOrderRate(Float orderRate) {
		this.orderRate = orderRate;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Float getTargetRate() {
		return this.targetRate;
	}

	public void setTargetRate(Float targetRate) {
		this.targetRate = targetRate;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<StoplossOrderTransaction> getStoplossOrderTransactions() {
		return this.stoplossOrderTransactions;
	}

	public void setStoplossOrderTransactions(Set<StoplossOrderTransaction> stoplossOrderTransactions) {
		this.stoplossOrderTransactions = stoplossOrderTransactions;
	}
	
}