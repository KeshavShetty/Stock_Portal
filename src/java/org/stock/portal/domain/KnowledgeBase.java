package org.stock.portal.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the knowledgebase database table.
 * 
 */
@Entity
@Table(name="knowledgebase")
public class KnowledgeBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=4000)
	private String description;

	@Column(name="resource_date")
	@Temporal(TemporalType.DATE)
	private Date resourceDate;

	@Column(length=255)
	private String title;

	//bi-directional many-to-one association to SimpleFile
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_file")
	private SimpleFile simpleFile;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_uploader")
	private User user;

    public KnowledgeBase() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getResourceDate() {
		return this.resourceDate;
	}

	public void setResourceDate(Date resourceDate) {
		this.resourceDate = resourceDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SimpleFile getSimpleFile() {
		return this.simpleFile;
	}

	public void setSimpleFile(SimpleFile simpleFile) {
		this.simpleFile = simpleFile;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}