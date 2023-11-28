package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stoploss_order_transaction database table.
 * 
 */
@Entity
@Table(name="stoploss_order_transaction")
public class StoplossOrderTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to StoplossOrder
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_stoploss")
	private StoplossOrder stoplossOrder;

	//bi-directional many-to-one association to Transaction
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_transaction")
	private Transaction transaction;

    public StoplossOrderTransaction() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StoplossOrder getStoplossOrder() {
		return this.stoplossOrder;
	}

	public void setStoplossOrder(StoplossOrder stoplossOrder) {
		this.stoplossOrder = stoplossOrder;
	}
	
	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
}