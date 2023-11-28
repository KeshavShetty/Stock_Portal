package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="feed_source")
@NamedQueries({
	@NamedQuery(name = "News.getFeedSource",
			query = "SELECT OBJECT(nfs) "
			+ " FROM NewsFeedSource nfs " +
					" order by nfs.sourceName asc")
})
public class NewsFeedSource implements Serializable {
	private static final long serialVersionUID = 1L; 
	  
	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="source_name")
	private String sourceName;	

	@Column(name="feed_type")
	private String feedType;	
	
	@Column(name="feed_link")
	private String feedLink;
	
	@Column(name="feed_main_url")
	private String feedMainURL;

	public NewsFeedSource() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getFeedLink() {
		return feedLink;
	}

	public void setFeedLink(String feedLink) {
		this.feedLink = feedLink;
	}

	public String getFeedMainURL() {
		return feedMainURL;
	}

	public void setFeedMainURL(String feedMainURL) {
		this.feedMainURL = feedMainURL;
	}

	
}