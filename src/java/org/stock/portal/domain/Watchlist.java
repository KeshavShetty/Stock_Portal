package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the watchlist database table.
 * 
 */
@Entity
@Table(name="watchlist")
@NamedQueries({
	@NamedQuery(name = "WL.getWatchlistByUserId",
			query = "SELECT OBJECT(wl) " +
					"FROM Watchlist wl where wl.user.id = :userId " +
					"order by wl.name asc"),
	
	@NamedQuery(name = "WL.getWlByWLIdAndUserId",
					query = "SELECT OBJECT(watchlist) " +
							"FROM Watchlist watchlist where watchlist.user.id = :userId " +
							"AND watchlist.id =:wlId " )
})
public class Watchlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="WATCHLIST_ID_GEN", allocationSize=1, sequenceName="WATCHLIST_ID_SEQ")
    @GeneratedValue(generator="WATCHLIST_ID_GEN", strategy=GenerationType.SEQUENCE)
    @Basic(optional=false)
    @Column(name="ID")
	private Long id;

	@Column(name="default_watchlist")
	private Boolean defaultWatchlist;

	@Column(length=1000)
	private String description;

	@Column(name="wl_name",length=255)
	private String name;

	private Boolean shared;
	
	@Column(name="max_capital_used")
	private Float maxCapitalUsed;
	
	@Column(name="available_capital")
	private Float availableCapital;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="F_owner")
	private User user;

	//bi-directional many-to-one association to WatchlistItem
	@OneToMany(mappedBy="watchlist")
	private Set<WatchlistItem> watchlistItems;

	@Column(name="watchlist_type")
	private String watchlistType;
	
	@Column(name="scrip_fecth_sql")
	private String scripFecthSql;
	
    public Watchlist() {
    }
    
    public Watchlist(Long id, String name) {
    	this.id = id;
    	this.name=name;
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDefaultWatchlist() {
		return this.defaultWatchlist;
	}

	public void setDefaultWatchlist(Boolean defaultWatchlist) {
		this.defaultWatchlist = defaultWatchlist;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getShared() {
		return this.shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<WatchlistItem> getWatchlistItems() {
		return this.watchlistItems;
	}

	public void setWatchlistItems(Set<WatchlistItem> watchlistItems) {
		this.watchlistItems = watchlistItems;
	}

	public String getWatchlistType() {
		return watchlistType;
	}

	public void setWatchlistType(String watchlistType) {
		this.watchlistType = watchlistType;
	}

	public String getScripFecthSql() {
		return scripFecthSql;
	}

	public void setScripFecthSql(String scripFecthSql) {
		this.scripFecthSql = scripFecthSql;
	}

	public Float getMaxCapitalUsed() {
		return maxCapitalUsed;
	}

	public void setMaxCapitalUsed(Float maxCapitalUsed) {
		this.maxCapitalUsed = maxCapitalUsed;
	}

	public Float getAvailableCapital() {
		return availableCapital;
	}

	public void setAvailableCapital(Float availableCapital) {
		this.availableCapital = availableCapital;
	}
	
}