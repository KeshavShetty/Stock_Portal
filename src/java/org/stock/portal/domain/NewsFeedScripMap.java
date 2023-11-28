package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the scrips database table.
 * 
 */
@Entity
@Table(name="feed_scrip_map")
public class NewsFeedScripMap implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique=true, nullable=false)
	private Long id;
	
	//bi-directional many-to-one association to Sector
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="feed_post")
	private NewsFeed newsFeedPost;
	
	//bi-directional many-to-one association to Sector
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="scrip")
	private Scrip scrip;
	
			
	public NewsFeedScripMap() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NewsFeed getNewsFeedPost() {
		return newsFeedPost;
	}

	public void setNewsFeedPost(NewsFeed newsFeedPost) {
		this.newsFeedPost = newsFeedPost;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}	
}