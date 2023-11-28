package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the simple_file database table.
 * 
 */
@Entity
@Table(name="simple_file")
public class SimpleFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=100)
	private String contentType;

	//private Object data;

	@Column(length=255)
	private String fileName;

	@Column(nullable=false)
	private Long size;

	//bi-directional many-to-one association to KnowledgeBase
	@OneToMany(mappedBy="simpleFile")
	private Set<KnowledgeBase> knowledgebases;

    public SimpleFile() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

//	public Object getData() {
//		return this.data;
//	}
//
//	public void setData(Object data) {
//		this.data = data;
//	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Set<KnowledgeBase> getKnowledgebases() {
		return this.knowledgebases;
	}

	public void setKnowledgebases(Set<KnowledgeBase> knowledgebases) {
		this.knowledgebases = knowledgebases;
	}
	
}