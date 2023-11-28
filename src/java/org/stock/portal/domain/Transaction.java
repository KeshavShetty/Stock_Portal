package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@Table(name="transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	private Float brokerage;

	@Column(length=255)
	private String comment;

	@Column(name="exchange_code")
	private Boolean exchangeCode;

	private Integer quantity;

	private Float rate;

	@Column(name="transaction_date")
	private Timestamp transactionDate;

	@Column(name="transaction_type")
	private Boolean transactionType;

	//bi-directional many-to-one association to StoplossOrderTransaction
	@OneToMany(mappedBy="transaction")
	private Set<StoplossOrderTransaction> stoplossOrderTransactions;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_owner")
	private User user;

    public Transaction() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(Float brokerage) {
		this.brokerage = brokerage;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return this.rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Timestamp getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Boolean getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(Boolean transactionType) {
		this.transactionType = transactionType;
	}

	public Set<StoplossOrderTransaction> getStoplossOrderTransactions() {
		return this.stoplossOrderTransactions;
	}

	public void setStoplossOrderTransactions(Set<StoplossOrderTransaction> stoplossOrderTransactions) {
		this.stoplossOrderTransactions = stoplossOrderTransactions;
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
	
}