package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="jqgrid_table_view")
@NamedQueries({
	@NamedQuery(name = "JqGrid.getJqGridTableByIdentifier",
			query = "SELECT OBJECT(jqg) "
			+ " FROM JqGridTable jqg where jqg.tableIdentifier like :tableIdentifier ")
})
public class JqGridTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="table_identifier")
	private String tableIdentifier;	

	@Column(name="joining_query")
	private String joiningQuery;
	
	@Column(name="display_header")
	private String displayHeader;

	@Column(name="default_filter_json")
	private String defaultFilter;
	
	@Column(name="default_order_by")
	private String defaultOrderBy;
	
	@Column(name="grid_height")
	private Integer gridHeight;
	
	@Column(name="on_select_row_function")
	private String onSelectRowFunction;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableIdentifier() {
		return tableIdentifier;
	}

	public void setTableIdentifier(String tableIdentifier) {
		this.tableIdentifier = tableIdentifier;
	}

	public String getJoiningQuery() {
		return joiningQuery;
	}

	public void setJoiningQuery(String joiningQuery) {
		this.joiningQuery = joiningQuery;
	}

	public String getDisplayHeader() {
		return displayHeader;
	}

	public void setDisplayHeader(String displayHeader) {
		this.displayHeader = displayHeader;
	}

	public String getDefaultFilter() {
		return defaultFilter;
	}

	public void setDefaultFilter(String defaultFilter) {
		this.defaultFilter = defaultFilter;
	}

	public String getDefaultOrderBy() {
		return defaultOrderBy;
	}

	public void setDefaultOrderBy(String defaultOrderBy) {
		this.defaultOrderBy = defaultOrderBy;
	}

	public Integer getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(Integer gridHeight) {
		this.gridHeight = gridHeight;
	}

	public String getOnSelectRowFunction() {
		return onSelectRowFunction;
	}

	public void setOnSelectRowFunction(String onSelectRowFunction) {
		this.onSelectRowFunction = onSelectRowFunction;
	}	
}