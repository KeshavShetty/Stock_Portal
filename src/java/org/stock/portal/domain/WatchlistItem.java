package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the watchlist_item database table.
 * 
 */
@Entity
@Table(name="watchlist_item")
@NamedQueries({
	@NamedQuery(name = "WLItem.getSIHByUserId",
			query = "SELECT OBJECT(wlItem) " +
					"FROM WatchlistItem wlItem where wlItem.watchlist.user.id = :userId " +
					"AND wlItem.stockInHand > 0" +
					"order by wlItem.scrip.bseName asc"),
					
	@NamedQuery(name = "WLItem.getWlItemByWLIdAndUserId",
			query = "SELECT OBJECT(wlItem) " +
					"FROM WatchlistItem wlItem where wlItem.watchlist.user.id = :userId " +
					"AND wlItem.watchlist.id =:wlId " +
					"order by wlItem.scrip.bseName asc")
					
})
public class WatchlistItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	//bi-directional many-to-one association to Scrip
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_scrip")
	private Scrip scrip;

	@Column(name="average_buy_rate")
	private Float averageBuyRate;
	
	@Column(name="sih_quantity")
	private Long stockInHand;	
	
	//bi-directional many-to-one association to Watchlist
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="f_watchlist")
	private Watchlist watchlist;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="watchlistItem")
	@OrderBy("transactionDate ASC")
    private List<WatchlistTransactions> wlTransactions;

	@Column(name="last_updated")
	@Temporal(TemporalType.DATE)
	private Date lastUpdated;
	
	@Column(name="hold_suggestion")
	private String holdSuggestion;	
	
    public WatchlistItem() {
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
	
	public Watchlist getWatchlist() {
		return this.watchlist;
	}

	public void setWatchlist(Watchlist watchlist) {
		this.watchlist = watchlist;
	}

	public Float getAverageBuyRate() {
		return averageBuyRate;
	}

	public void setAverageBuyRate(Float averageBuyRate) {
		this.averageBuyRate = averageBuyRate;
	}

	public Long getStockInHand() {
		return stockInHand;
	}

	public void setStockInHand(Long stockInHand) {
		this.stockInHand = stockInHand;
	}

	public List<WatchlistTransactions> getWlTransactions() {
		return wlTransactions;
	}

	public void setWlTransactions(List<WatchlistTransactions> wlTransactions) {
		this.wlTransactions = wlTransactions;
	}
	
	public Float getTotalMoneyflow() {
		Float retVal = 0f;
		if (wlTransactions!=null && wlTransactions.size()>0) {
			for(int i=0;i<wlTransactions.size();i++) {
				WatchlistTransactions aTrans = wlTransactions.get(i);
				if (aTrans.getTransactionType()==false) { //Buy transaction
					retVal = retVal - (aTrans.getQuantity().floatValue()*aTrans.getRate() + aTrans.getBrokerage());
				} else { //Sell transaction
					retVal = retVal + (aTrans.getQuantity().floatValue()*aTrans.getRate()) - aTrans.getBrokerage();
				}
			}
		}
		return retVal;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getHoldSuggestion() {
		return holdSuggestion;
	}

	public void setHoldSuggestion(String holdSuggestion) {
		this.holdSuggestion = holdSuggestion;
	}	
}