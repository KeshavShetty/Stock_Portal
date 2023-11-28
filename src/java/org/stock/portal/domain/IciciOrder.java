package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the watchlist_item database table.
 * 
 */
@Entity
@Table(name="icici_order")
public class IciciOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="ICICI_ORDER_ID_GEN", allocationSize=1, sequenceName="ICICI_ORDER_ID_SEQ")
    @GeneratedValue(generator="ICICI_ORDER_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
    @Column(name="ID")
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	@Column(name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Column(name="exchange_code")
	private Boolean exchangeCode;
	
	@Column(name="order_type")
	private Boolean orderType; // Market or Limit
	
	@Column(name="transaction_type")
	private Boolean transactionType; // Buy or sell
		
	@Column(name="quantity")
	private Long quantity;
	
	@Column(name="at_price")
	private Float atPrice;
	
	@Column(name="maximum_budget")
	private Float maximumBudget;
	
	@Column(name="order_status")
	private String orderStatus;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_placed_by")
	private User placedBy;
	
	public IciciOrder() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Scrip getScrip() {
		return this.scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Boolean getOrderType() {
		return orderType;
	}

	public void setOrderType(Boolean orderType) {
		this.orderType = orderType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Float getAtPrice() {
		return atPrice;
	}

	public void setAtPrice(Float atPrice) {
		this.atPrice = atPrice;
	}

	public Float getMaximumBudget() {
		return maximumBudget;
	}

	public void setMaximumBudget(Float maximumBudget) {
		this.maximumBudget = maximumBudget;
	}

	public User getPlacedBy() {
		return placedBy;
	}

	public void setPlacedBy(User placedBy) {
		this.placedBy = placedBy;
	}

	public Boolean getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Boolean transactionType) {
		this.transactionType = transactionType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}