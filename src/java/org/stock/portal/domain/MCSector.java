package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the sector database table.
 * 
 */
@Entity
@Table(name="mcsector")
@NamedQueries({
	@NamedQuery(name = "Sector.getAllMCSectors",
			query = "SELECT OBJECT(scr) FROM MCSector scr order by scr.name asc")
})
public class MCSector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String name;

	//bi-directional many-to-one association to Scrip
	@OneToMany(mappedBy="mcsector")
	private Set<Scrip> scrips;

    public MCSector() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Scrip> getScrips() {
		return this.scrips;
	}

	public void setScrips(Set<Scrip> scrips) {
		this.scrips = scrips;
	}
	
}