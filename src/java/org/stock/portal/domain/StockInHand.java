package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the stock_in_hand database table.
 * 
 */
@Entity
@Table(name="stock_in_hand")
public class StockInHand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="average_buy_rate")
	private Float averageBuyRate;

	@Column(name="modify_date")
	@Temporal(TemporalType.DATE)
	private Date modifyDate;

	private Integer quantity;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_owner")
	private User user;

    public StockInHand() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getAverageBuyRate() {
		return this.averageBuyRate;
	}

	public void setAverageBuyRate(Float averageBuyRate) {
		this.averageBuyRate = averageBuyRate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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