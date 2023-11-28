package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the watchlist_item Transactions table.
 * 
 */
@Entity
@Table(name="watchlist_transaction")
@NamedQueries({
	@NamedQuery(name = "WLTransactions.getTransactionsByWatchlistIdDatewise",
			query = "SELECT OBJECT(wlTrans) " +
					"FROM WatchlistTransactions wlTrans where wlTrans.watchlistItem.watchlist.id = :wlId " +
					"order by wlTrans.transactionDate desc"),
					
	@NamedQuery(name = "WLTransactions.getTransactionsByWatchlistIdScripwise",
			query = "SELECT OBJECT(wlTrans) " +
					"FROM WatchlistTransactions wlTrans where wlTrans.watchlistItem.watchlist.id = :wlId " +
					"order by wlTrans.watchlistItem.scrip.name asc, wlTrans.transactionDate desc")
					
})
public class WatchlistTransactions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="WATCHLIST_TRANSACTION_ID_GEN", allocationSize=1, sequenceName="WATCHLIST_TRANSACTION_ID_SEQ")
    @GeneratedValue(generator="WATCHLIST_TRANSACTION_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
     @Column(name="ID")
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_watchlist_item")
	private WatchlistItem watchlistItem;
	
	@Column(name="transaction_type")
	private Boolean transactionType;

	@Column(name="exchange")
	private Boolean exchange;
	
	@Column(name="transaction_date")
	@Temporal(TemporalType.DATE)
	private Date transactionDate;
	
	@Column(name="quantity")
	private Long quantity;
	
	@Column(name="rate")
	private Float rate;
	
	@Column(name="brokerage")
	private Float brokerage;
	
	@Column(name="settlement_number")
	private String settlementNumber;

    public WatchlistTransactions() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WatchlistItem getWatchlistItem() {
		return watchlistItem;
	}

	public void setWatchlistItem(WatchlistItem watchlistItem) {
		this.watchlistItem = watchlistItem;
	}

	public Boolean getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Boolean transactionType) {
		this.transactionType = transactionType;
	}

	public Boolean getExchange() {
		return exchange;
	}

	public void setExchange(Boolean exchange) {
		this.exchange = exchange;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Float getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Float brokerage) {
		this.brokerage = brokerage;
	}

	public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

	public Float getTotal() {
		return (quantity.floatValue())*rate+brokerage;
	}
	
	public Float getAmount() {
		return this.getQuantity().floatValue()*this.getRate();
	}
}