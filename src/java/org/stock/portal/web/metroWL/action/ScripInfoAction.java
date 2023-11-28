package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class ScripInfoAction extends BaseAction {

	Logger log = Logger.getLogger(ScripInfoAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Scrip scrip;
    
    private List<EodData> indexScripsEod; // Index scrips in which this scrips is part of
    
    private List<WatchlistItem> watchlistItems; // Watchlists in which this scrip added in the respective used watchlist
    private List<Status> scripTwitterStatusList;
    
    private List<AutoscanResult> autoscanResultList;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
   
    public ScripInfoAction() {
        super();
    }
    
    public String leanSummary() {
    	try {    		
    		String scripId = getScripIdFromRequest();
    		System.out.println("In ScripInfoAction prepareLoad scripId="+scripId);
	    	this.scrip = scripManager.getScripById(Long.parseLong(scripId));
	    	if (scrip!=null) {
	    		autoscanResultList = autoscanManager.getSummaryAutoscanResult(scrip.getId(), 2);
		    	indexScripsEod = scripManager.getIndexScripByScripId(scrip.getId());
	    	}
    	} catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    public String prepareLoad() {
    	try {    		
    		String scripId = getScripIdFromRequest();
    		System.out.println("In ScripInfoAction prepareLoad scripId="+scripId);    		
	    	this.scrip = scripManager.getScripById(Long.parseLong(scripId));
	    	if (this.scrip.getMarketsmojoId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String mojoId = scrUtil.getIdFromMarketsmojo(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (mojoId!=null) {
	    			this.scrip.setMarketsmojoId(mojoId); 
	    			scripManager.persistMojoId(this.scrip.getId(), mojoId);
	    		}	    		
	    	} // StockAdda ID
	    	if (this.scrip.getStockaddaId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String stockaddaId = scrUtil.getIdFromSockadda(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (stockaddaId!=null) {
	    			this.scrip.setStockaddaId(stockaddaId); 
	    			scripManager.persistStockaddaId(this.scrip.getId(), stockaddaId);
	    		}	    		
	    	}
	    	if (this.scrip.getTijoriFinanceId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String tijoriFinanceId = scrUtil.getIdFromTijoriFinance(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (tijoriFinanceId!=null) {
	    			this.scrip.setTijoriFinanceId(tijoriFinanceId); 
	    			scripManager.persistTijoriFinanceId(this.scrip.getId(), tijoriFinanceId);
	    		}
	    	}
	    	if (this.scrip.getReutersId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String reutersId = scrUtil.getIdFromReuters(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (reutersId!=null) {
	    			this.scrip.setReutersId(reutersId); 
	    			scripManager.persistReutersId(this.scrip.getId(), reutersId);
	    		}
	    		System.out.println("In ScripInfoAction reutersId ="+reutersId);    		
	    	}
	    	if (this.scrip.getTrendlyneId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String trendlyne = scrUtil.getIdFromTrendlyne(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (trendlyne!=null) {
	    			this.scrip.setTrendlyneId(trendlyne); 
	    			scripManager.persistTrendlyneId(this.scrip.getId(), trendlyne);
	    		}
	    		System.out.println("In ScripInfoAction trendlyneId ="+trendlyne);    		
	    	}
	    	
	    	if (this.scrip.getTickertapeId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String tickertape = scrUtil.getIdFromTickertape(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		if (tickertape!=null) {
	    			this.scrip.setTickertapeId(tickertape); 
	    			scripManager.persistTickertapeId(this.scrip.getId(), tickertape);
	    		}
	    		System.out.println("In ScripInfoAction tickertape Id="+tickertape);    		
	    	}
	    	
	    	if (this.scrip.getSimplywallstId()==null && this.scrip.getId()>=1000L) {
	    		ScripUtil scrUtil = new ScripUtil();
	    		String simplywallst = scrUtil.getIdFromSimplywallst(this.scrip.getBseCode(), this.scrip.getNseCode());
	    		System.out.println("simplywallst="+simplywallst);
	    		if (simplywallst!=null) {
	    			this.scrip.setSimplywallstId(simplywallst); 
	    			scripManager.persistSimplywallstId(this.scrip.getId(), simplywallst);
	    		}
	    		System.out.println("In ScripInfoAction simplywallst Id="+simplywallst);    		
	    	}
	    	
	    	
	    	
	    	
	    	if (scrip!=null) {
	    		this.session.put(Constants.LAST_ACCESSED_SCRIP, scrip);
	    	}
    	} catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    public String getScripInfo() {
    	try {
    		String scripId = getScripIdFromRequest();
    		if (scripId!=null && scripId.length()>0) {
    			this.scrip = scripManager.getScripById(Long.parseLong(scripId));
    		} else {	    	
    			scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
    		}
	    	User loggedUser  = (User)this.session.get(Constants.LOGGED_IN_USER);
	    	
	    	autoscanResultList = autoscanManager.getSummaryAutoscanResult(scrip.getId(), 2);
	    	
//	    	if (loggedUser!=null) {
//	    		watchlistItems = watchlistManager.getWatchlistItemsByUserIdAndScripId(scrip.getId(), loggedUser.getId());
//	    		System.out.println("Size of watchlistItems="+watchlistItems.size());
//	    	}
	    	
	    	
    	} catch (Exception e) { 
        	log.error(e); 
        	addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    public String getScripTechnicalAnalysis() { 
    	try {
	    	String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				this.scrip = scripManager.getScripById(Long.parseLong(scripId));
				if (scrip!=null) {
		    		this.session.put(Constants.LAST_ACCESSED_SCRIP, scrip);
		    	}
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getScripDailyAchievement() { 
    	try {
	    	String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				this.scrip = scripManager.getScripById(Long.parseLong(scripId));
				if (scrip!=null) {
		    		this.session.put(Constants.LAST_ACCESSED_SCRIP, scrip);
		    	}
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getScripOptionChain() { 
    	try {
	    	String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				this.scrip = scripManager.getScripById(Long.parseLong(scripId));
				if (scrip!=null) {
		    		this.session.put(Constants.LAST_ACCESSED_SCRIP, scrip);
		    	}
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getScripIntradaySnapshot() { 
    	return SUCCESS;
    }
    
    public String getScripNewsAndFeeds() { 
    	return SUCCESS;
    }

    public String getScripPeers() { 
    	return SUCCESS;
    }
    
    public String getTwitterStatus() {
    	try {
	    	scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
	    	List<Status> twitterStatusList = new ArrayList<Status>();
	    	if (scrip!=null) {
	    		if (scrip.getName()!=null) {
	    			twitterStatusList.addAll(getTwitterByWords(scrip.getName()));
	    			System.out.println("After name search - Size of twitterStatusList="+twitterStatusList.size());
	    		}
	    		
	    		if (scrip.getBseCode()!=null) {
	    			twitterStatusList.addAll(getTwitterByWords(scrip.getBseCode()));
	    			System.out.println("After bsecode search - Size of twitterStatusList="+twitterStatusList.size());
	    		}
	    		
	    		if (scrip.getNseCode()!=null) {
	    			twitterStatusList.addAll(getTwitterByWords(scrip.getNseCode()));
	    			System.out.println("After NSE code search - Size of twitterStatusList="+twitterStatusList.size());
	    		}
	    		System.out.println("Before sort Size of twitterStatusList="+twitterStatusList.size());
	    		Collections.sort(twitterStatusList, new CreatedAtComparator());
	    		System.out.println("After sort Size of twitterStatusList="+twitterStatusList.size());
	    		scripTwitterStatusList = twitterStatusList;
	    	}
	    	// indexScripsEod = scripManager.getIndexScripByScripId(scrip.getId()); // Indices EOD data not populated properly in DataTools. Once that is fixed, revisit here.
	    	System.out.println("Size of indexScripsEod="+indexScripsEod.size());
	    	
    	} catch (Exception e) { 
        	log.error(e); 
        	addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    public String getUserWatchlists() {
    	try {
    		String scripId = getScripIdFromRequest();
    		if (scripId!=null && scripId.length()>0) {
    			this.scrip = scripManager.getScripById(Long.parseLong(scripId));
    		} else {	    	
    			scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
    		}
    		
    		User loggedUser  = (User)this.session.get(Constants.LOGGED_IN_USER);
	    	
	    	if (loggedUser!=null) {
	    		watchlistItems = watchlistManager.getWatchlistItemsByUserIdAndScripId(scrip.getId(), loggedUser.getId());
	    		System.out.println("Size of watchlistItems="+watchlistItems.size());
	    	}
	    	indexScripsEod = scripManager.getIndexScripByScripId(scrip.getId());
	    	System.out.println("Size of indexScripsEod="+indexScripsEod.size());
    	} catch (Exception e) { 
        	log.error(e); 
        	addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    private List<Status> getTwitterByWords(String searchWord) {
    	List<Status> retList = new ArrayList<Status>();
    	
    	try {
			ServletContext context = ServletActionContext.getServletContext();
			TwitterFactory tf = (TwitterFactory) context.getAttribute("TwitterHandler");
			if (tf==null) {
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
				  .setOAuthConsumerKey("CsoUCP4adeFyWx8hLzh69ak3C")
				  .setOAuthConsumerSecret("Qi9QpdogBj0cHYCw7HDFtctuZKe8OHBK7sfTwB35fXhS77bEfU")
				  .setOAuthAccessToken("77288392-3kUgIofTfH2rIW1vX5GCWgzp8oFGbRsj2BnRkftmY")
				  .setOAuthAccessTokenSecret("fWdoklqF3vaQp820oAokbOqkpvCaE7BGiSOtbmhHNykQF");
				tf = new TwitterFactory(cb.build());
				context.setAttribute("TwitterHandler", tf);
			}
			
	    	Twitter twitter = tf.getInstance();
		    Query query = new Query("\"" + searchWord +"\"");
		    QueryResult result = twitter.search(query);
		    System.out.println("Processing " + searchWord);
		    for (Status status : result.getTweets()) {
		    	retList.add(status);
		    }
    	} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
    	return retList;
    }
    
	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	public List<EodData> getIndexScripsEod() {
		return indexScripsEod;
	}

	public void setIndexScripsEod(List<EodData> indexScripsEod) {
		this.indexScripsEod = indexScripsEod;
	}
	
	private String getScripIdFromRequest() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String scripId = request.getParameter("scripId");
		if (scripId==null || scripId.length()==0) { // Try from jqIndex
			String jqIndex = request.getParameter("jqIndex");
			if (jqIndex!=null) {
				if (jqIndex.indexOf("_")>=0) scripId = jqIndex.substring(0, jqIndex.indexOf("_"));
				else scripId = jqIndex;
			}
		}
		if (scripId==null || scripId.length()==0) { // Try from id
			scripId = request.getParameter("id");
		}
		return scripId;
    }

	public List<WatchlistItem> getWatchlistItems() {
		return watchlistItems;
	}

	public void setWatchlistItems(List<WatchlistItem> watchlistItems) {
		this.watchlistItems = watchlistItems;
	}

	public List<Status> getScripTwitterStatusList() {
		return scripTwitterStatusList;
	}

	public void setScripTwitterStatusList(List<Status> scripTwitterStatusList) {
		this.scripTwitterStatusList = scripTwitterStatusList;
	}

	public List<AutoscanResult> getAutoscanResultList() {
		return autoscanResultList;
	}

	public void setAutoscanResultList(List<AutoscanResult> autoscanResultList) {
		this.autoscanResultList = autoscanResultList;
	}
}

class CreatedAtComparator implements Comparator<Status> {
    @Override
    public int compare(Status a, Status b) {
    	return a.getCreatedAt().before(b.getCreatedAt()) ? 0 : 1;
    }
}
