package org.stock.portal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="jqgrid_table_columns")
@NamedQueries({
	@NamedQuery(name = "JqGridColumn.getJqGridTableColumnsByTableId",
			query = "SELECT OBJECT(jqgc) "
			+ " FROM JqGridTableColumns jqgc where jqgc.jqGridTable.id = :tableId order by jqgc.columnFetchOrder "),
	@NamedQuery(name = "JqGridColumn.getJqGridTableColumnsByTableIdentifier",
			query = "SELECT OBJECT(jqgc) "
			+ " FROM JqGridTableColumns jqgc where jqgc.jqGridTable.tableIdentifier = :tableIdentifier order by jqgc.columnFetchOrder, jqgc.id "),
	@NamedQuery(name = "JqGridColumn.getVisibleJqGridTableColumnsByTableIdentifier",
			query = "SELECT OBJECT(jqgc) "
			+ " FROM JqGridTableColumns jqgc where jqgc.jqGridTable.tableIdentifier = :tableIdentifier and jqgc.isVisible = :isVisible order by jqgc.columnFetchOrder "),
	@NamedQuery(name = "JqGridColumn.getJqGridColumnById",
			query = "SELECT OBJECT(jqgc) "
			+ " FROM JqGridTableColumns jqgc where jqgc.id = :jqColumnId "),
	@NamedQuery(name = "JqGridColumn.getSearchableJqGridTableColumnsByTableIdentifier",
			query = "SELECT OBJECT(jqgc) "
			+ " FROM JqGridTableColumns jqgc where jqgc.jqGridTable.tableIdentifier = :tableIdentifier and jqgc.isSearchable = :isSearchable order by jqgc.columnFetchOrder ")
					
})
public class JqGridTableColumns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_jqgrid_table")
	private JqGridTable jqGridTable;

	@Column(name="display_name")
	private String displayName;
	
	@Column(name="view_to_db_identifier")
	private String viewToDbIdentifier;

	@Column(name="actual_column_name")
	private String actualColumnName;

	@Column(name="column_type")
	private String columnType;
	
	@Column(name="column_fetch_order")
	private Integer columnFetchOrder;
	
	@Column(name="data_formatter")
	private String dataFormatter;
	
	@Column(name="is_visible")
	private Boolean isVisible;	
	
	@Column(name="select_query") 
	private String selectQuery; // Used for drop downs options fetching 
	
	@Column(name="ui_width")
	private Integer uiWidth;
	
	@Column(name="search_where_clause") 
	private String searchWhereClause; // Used for search of drop downs in Many to Many relation
	
	@Column(name="is_searchable")
	private Boolean isSearchable;	
	
	@Column(name="filter_form_tab_order")
	private Integer tabOrder;
	
	@Column(name="default_minimum_filter_value") 
	private String minimumFilterValue; 
	
	@Column(name="default_maximum_filter_value") 
	private String maximumFilterValue; 
	
	@Column(name="date_gap_days") 
	private Integer dateGapDays; 
	
	@Column(name="custom_field_setter") 
	private String customFieldSetter; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JqGridTable getJqGridTable() {
		return jqGridTable;
	}

	public void setJqGridTable(JqGridTable jqGridTable) {
		this.jqGridTable = jqGridTable;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getViewToDbIdentifier() {
		return viewToDbIdentifier;
	}

	public void setViewToDbIdentifier(String viewToDbIdentifier) {
		this.viewToDbIdentifier = viewToDbIdentifier;
	}

	public String getActualColumnName() {
		return actualColumnName;
	}

	public void setActualColumnName(String actualColumnName) {
		this.actualColumnName = actualColumnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getColumnFetchOrder() {
		return columnFetchOrder;
	}

	public void setColumnFetchOrder(Integer columnFetchOrder) {
		this.columnFetchOrder = columnFetchOrder;
	}

	public String getDataFormatter() {
		return dataFormatter;
	}

	public void setDataFormatter(String dataFormatter) {
		this.dataFormatter = dataFormatter;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getSelectQuery() {
		return selectQuery;
	}

	public void setSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
	}

	public Integer getUiWidth() {
		return uiWidth;
	}

	public void setUiWidth(Integer uiWidth) {
		this.uiWidth = uiWidth;
	}

	public Boolean getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(Boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public String getSearchWhereClause() {
		return searchWhereClause;
	}

	public void setSearchWhereClause(String searchWhereClause) {
		this.searchWhereClause = searchWhereClause;
	}

	public Integer getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(Integer tabOrder) {
		this.tabOrder = tabOrder;
	}

	public String getMinimumFilterValue() {
		return minimumFilterValue;
	}

	public void setMinimumFilterValue(String minimumFilterValue) {
		this.minimumFilterValue = minimumFilterValue;
	}

	public String getMaximumFilterValue() {
		return maximumFilterValue;
	}

	public void setMaximumFilterValue(String maximumFilterValue) {
		this.maximumFilterValue = maximumFilterValue;
	}

	public Integer getDateGapDays() {
		return dateGapDays;
	}

	public void setDateGapDays(Integer dateGapDays) {
		this.dateGapDays = dateGapDays;
	}

	public String getCustomFieldSetter() {
		return customFieldSetter;
	}

	public void setCustomFieldSetter(String customFieldSetter) {
		this.customFieldSetter = customFieldSetter;
	}

}