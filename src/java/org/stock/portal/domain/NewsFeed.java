package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the scrips database table.
 * 
 */
@Entity
@Table(name="feed_posts")
public class NewsFeed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique=true, nullable=false)
	private Long id;
	
	//bi-directional many-to-one association to Sector
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="feed_source")
	private NewsFeedSource newsFeedSource;
	
	@Column(name="post_title")
	private String postTitle;
	
	@Column(name="post_short_content")
	private String postShortContent;

	@Column(name="publish_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate;

	@Column(name="unique_gui_id")
	private String postURL;
			
	public NewsFeed() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NewsFeedSource getNewsFeedSource() {
		return newsFeedSource;
	}

	public void setNewsFeedSource(NewsFeedSource newsFeedSource) {
		this.newsFeedSource = newsFeedSource;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostShortContent() {
		return postShortContent;
	}

	public void setPostShortContent(String postShortContent) {
		this.postShortContent = postShortContent;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}


	
}