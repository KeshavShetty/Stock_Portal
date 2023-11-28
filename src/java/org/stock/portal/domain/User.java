package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQueries({
	@NamedQuery(name = "User.getAccountByCredentials",
			query = "SELECT OBJECT(U) "
			+ "FROM User U where U.userName = :pUsername and U.password = :pPassword"),
	@NamedQuery(name = "User.getAccountById",
			query = "SELECT OBJECT(U) FROM User U where U.id = :userId")
})			
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=50)
	private String captcha;

	@Column(name="email_id", length=50)
	private String emailId;

	@Column(length=25)
	private String firstName;

	@Column(length=25)
	private String lastName;

	@Column(name="mobile_number",length=25)
	private String mobileNumber;

	@Column(name="notification_number",length=25)
	private String notificationNumber;

	@Column(length=255)
	private String password;

	@Column(length=12)
	private String userName;

	private Boolean userStatus;

	private Boolean userType;

	//bi-directional many-to-one association to AccountTransaction
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<AccountTransaction> accountTransactions;

	//bi-directional many-to-one association to KnowledgeBase
	@OneToMany(mappedBy="user")
	private Set<KnowledgeBase> knowledgebases;

	//bi-directional many-to-one association to StockInHand
	@OneToMany(mappedBy="user")
	private Set<StockInHand> stockInHands;

	//bi-directional many-to-one association to StoplossOrder
	@OneToMany(mappedBy="user")
	private Set<StoplossOrder> stoplossOrders;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="user")
	private Set<Transaction> transactions;

	//bi-directional many-to-one association to Watchlist
	@OneToMany(mappedBy="user")
	private Set<Watchlist> watchlists;

    public User() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNotificationNumber() {
		return this.notificationNumber;
	}

	public void setNotificationNumber(String notificationNumber) {
		this.notificationNumber = notificationNumber;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Boolean getUserType() {
		return this.userType;
	}

	public void setUserType(Boolean userType) {
		this.userType = userType;
	}

	public Set<AccountTransaction> getAccountTransactions() {
		return this.accountTransactions;
	}

	public void setAccountTransactions(Set<AccountTransaction> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}
	
	public Set<KnowledgeBase> getKnowledgebases() {
		return this.knowledgebases;
	}

	public void setKnowledgebases(Set<KnowledgeBase> knowledgebases) {
		this.knowledgebases = knowledgebases;
	}
	
	public Set<StockInHand> getStockInHands() {
		return this.stockInHands;
	}

	public void setStockInHands(Set<StockInHand> stockInHands) {
		this.stockInHands = stockInHands;
	}
	
	public Set<StoplossOrder> getStoplossOrders() {
		return this.stoplossOrders;
	}

	public void setStoplossOrders(Set<StoplossOrder> stoplossOrders) {
		this.stoplossOrders = stoplossOrders;
	}
	
	public Set<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Set<Watchlist> getWatchlists() {
		return this.watchlists;
	}

	public void setWatchlists(Set<Watchlist> watchlists) {
		this.watchlists = watchlists;
	}
	
}