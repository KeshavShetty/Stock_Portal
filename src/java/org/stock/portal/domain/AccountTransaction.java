package org.stock.portal.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the account_transaction database table.
 * 
 */
@Entity
@Table(name="account_transaction")
public class AccountTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACCOUNT_TRANSACTION_ID_GENERATOR", sequenceName="account_transaction_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACCOUNT_TRANSACTION_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	private Float amount;

	@Column(length=255)
	private String description;

	@Column(name="transaction_date")
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Column(name="transaction_type")
	private Integer transactionType;

	//bi-directional many-to-one association to User
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="f_owner")
	private User user;

    public AccountTransaction() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}