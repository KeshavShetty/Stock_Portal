package org.stock.portal.service.jqGrid;



import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.WatchlistDao;
import org.stock.portal.domain.JqGridTable;
import org.stock.portal.domain.JqGridTableColumns;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.dto.KeyValueDTO;
import org.stock.portal.web.util.Constants;

@Stateless(name="JqGridManager", mappedName="org.stock.portal.service.jqGrid.JqGridManager")
public class JqGridManagerBean implements JqGridManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(JqGridManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public Map<String, Object> getJqGridTableDetails(String tableIdentifier, String loggedUserId) throws BusinessException {
    	log.debug("In JqGridManagerBean getJqGridTableDetails tableIdentifier="+tableIdentifier);
    	Map<String, Object> retMap = new HashMap<String, Object>(); 
    	try {	    	
	    	JqGridTable jqGridTable = (JqGridTable)this.entityManager.createNamedQuery("JqGrid.getJqGridTableByIdentifier").setParameter("tableIdentifier", tableIdentifier).getSingleResult();
	    	retMap.put("JqGridTable", jqGridTable);
	    	List<JqGridTableColumns> jqGridTableColumns = this.entityManager.createNamedQuery("JqGridColumn.getJqGridTableColumnsByTableIdentifier").setParameter("tableIdentifier", tableIdentifier).getResultList();
	    	retMap.put("JqGridTableColumns", jqGridTableColumns);
	    	if (loggedUserId!=null && Long.parseLong(loggedUserId)>0) {
	    		List<KeyValueDTO> userSavedSearches = getUserSavedSearches(tableIdentifier, loggedUserId);
	    		//String lastAccessed = getSavedSearch(tableIdentifier, loggedUserId, "LastAccessed");
	    		if (userSavedSearches!=null && userSavedSearches.size()>0) retMap.put("UserSavedSearches", userSavedSearches);
	    	}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retMap;
    }
    
    private List<KeyValueDTO> getUserSavedSearches(String tableIdentifier, String loggedUserId) {
    	List<KeyValueDTO> retList = new ArrayList<KeyValueDTO>();
    	try {
    		Query q = entityManager.createNativeQuery("SELECT name, filter_query FROM JQGRID_SAVED_SEARCH where f_user = "+loggedUserId+" and f_jqgrid_table =(select id from jqgrid_table_view where table_identifier like '"+ tableIdentifier + "') order by name");
    		
    		List<Object[]> listResults = q.getResultList();
        	Iterator<Object[]> iter = listResults.iterator();
        	while (iter.hasNext()) {
    			Object[] rowdata = iter.next();
    			retList.add(new KeyValueDTO((String)rowdata[0],(String)rowdata[1]));    			
        	}
    		System.out.println("In getSavedSearch retList="+retList);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}    	
    	return retList;
    }
    
    private String getSavedSearch(String tableIdentifier, String loggedUserId, String searchName) {
    	String retStr = "";
    	try {
    		Query q = entityManager.createNativeQuery("SELECT filter_query FROM JQGRID_SAVED_SEARCH where f_user = "+loggedUserId+" and name like '" + searchName + "' and f_jqgrid_table =(select id from jqgrid_table_view where table_identifier like '"+ tableIdentifier + "')");
    		retStr = (String)q.getSingleResult();
    		System.out.println("In getSavedSearch outPut="+retStr);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retStr;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public String getJsonData(Map<String, String> allRequestQueryParams) throws BusinessException {
    	log.debug("In JqGridManagerBean getJsonData tableIdentifier="+allRequestQueryParams.get("viewTableIdentifier"));
    	SimpleDateFormat uiFormat = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat uiTimestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	
    	//NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    	DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
    	
    	String tableIdentifier = allRequestQueryParams.get("viewTableIdentifier");
    	
    	StringBuffer retJsonString = new StringBuffer();
    	
    	JqGridTable jqGridTable = (JqGridTable)this.entityManager.createNamedQuery("JqGrid.getJqGridTableByIdentifier").setParameter("tableIdentifier", tableIdentifier).getSingleResult();
    	
    	List<JqGridTableColumns> jqGridTableVisibleColumns = this.entityManager.createNamedQuery("JqGridColumn.getVisibleJqGridTableColumnsByTableIdentifier").setParameter("tableIdentifier", tableIdentifier).setParameter("isVisible",true).getResultList();
    	Map <String, JqGridTableColumns> mappedjqGridTableVisibleColumns = new HashMap<String, JqGridTableColumns>();
    	for(int i=0;i<jqGridTableVisibleColumns.size();i++) {
    		JqGridTableColumns aColumn = (JqGridTableColumns)jqGridTableVisibleColumns.get(i);
    		mappedjqGridTableVisibleColumns.put(aColumn.getViewToDbIdentifier(), aColumn);
    	}
    	
    	List<JqGridTableColumns> jqGridTableSearchableColumns = this.entityManager.createNamedQuery("JqGridColumn.getSearchableJqGridTableColumnsByTableIdentifier").setParameter("tableIdentifier", tableIdentifier).setParameter("isSearchable",true).getResultList();
    	Map <String, JqGridTableColumns> mappedjqGridTableSearchableColumns = new HashMap<String, JqGridTableColumns>();
    	for(int i=0;i<jqGridTableSearchableColumns.size();i++) {
    		JqGridTableColumns aColumn = (JqGridTableColumns)jqGridTableSearchableColumns.get(i);
    		mappedjqGridTableSearchableColumns.put(aColumn.getViewToDbIdentifier(), aColumn);
    	}
    	
    	// Include invisible columns in where clause
    	List<JqGridTableColumns> jqGridTableInvisibleColumns = this.entityManager.createNamedQuery("JqGridColumn.getVisibleJqGridTableColumnsByTableIdentifier").setParameter("tableIdentifier", tableIdentifier).setParameter("isVisible",false).getResultList();
    	String additinalClause = getAdditionalClauses(jqGridTableInvisibleColumns, allRequestQueryParams);
    	
    	String filterClauseToUse = allRequestQueryParams.get("filters");
    	if (filterClauseToUse==null || filterClauseToUse.length()==0) filterClauseToUse = allRequestQueryParams.get("customFilters"); 
    	String filterClause = "" ;
    	if (filterClauseToUse==null || filterClauseToUse.length()==0) filterClause = jqGridTable.getDefaultFilter(); 
    	else filterClause = getFilterSql(mappedjqGridTableSearchableColumns, filterClauseToUse);
    	System.out.println("!!!!!!!!!filterClauseToUse="+filterClauseToUse + " filterClause="+filterClause);
    	
    	String countQuery = "SELECT count(*) " + jqGridTable.getJoiningQuery();
    	if (additinalClause.length()>0) countQuery = countQuery + " " + additinalClause;
    	if (filterClause!=null && filterClause.length()>0) countQuery = countQuery + " " + filterClause;
    	Query countQry = entityManager.createNativeQuery(countQuery);
    	Long totalRecords = ((BigInteger)countQry.getSingleResult()).longValue();
    	int recordsPerPage = Integer.parseInt(allRequestQueryParams.get("rows"));
    	int pageNumber = Integer.parseInt(allRequestQueryParams.get("page"));
    	Long totalPages = totalRecords/recordsPerPage;
    	if (totalPages*recordsPerPage!=totalRecords) totalPages++;    	
    	retJsonString.append("({\"records\":\"" + totalRecords + "\",\"page\":" + pageNumber + ",\"total\":"+totalPages+",\"rows\":[");
    	    	
    	String dataSql = "SELECT ";    	
    	for(int i=0;i<jqGridTableVisibleColumns.size();i++) {
    		JqGridTableColumns aColumn = (JqGridTableColumns)jqGridTableVisibleColumns.get(i);
    		if (i!=0) dataSql = dataSql + ", ";
    		dataSql = dataSql + aColumn.getActualColumnName() + " ";
    	}
    	dataSql = dataSql + " " + jqGridTable.getJoiningQuery();
    	
    	if (additinalClause.length()>0) dataSql = dataSql + " " + additinalClause;
    	if (filterClause!=null && filterClause.length()>0) dataSql = dataSql + " " + filterClause;
    	// Add order by
    	String orderBySqlPart = getOrderSqlPart(mappedjqGridTableVisibleColumns, allRequestQueryParams); 
    	if (orderBySqlPart.length()>0) dataSql = dataSql + " ORDER BY " + orderBySqlPart;
    	else {
    		if (allRequestQueryParams.get("filters")==null || allRequestQueryParams.get("filters").length()==0) {
    			if (jqGridTable.getDefaultOrderBy()!=null && jqGridTable.getDefaultOrderBy().length()>0) {
    				dataSql = dataSql + " ORDER BY " + jqGridTable.getDefaultOrderBy();
    			}
    		}
    	}
    	
    	Query q = entityManager.createNativeQuery(dataSql);    	
    	q.setMaxResults(recordsPerPage);
    	q.setFirstResult((pageNumber-1)*Integer.parseInt(allRequestQueryParams.get("rows")));
    	
    	List<Object[]> listResults = q.getResultList();
    	Iterator<Object[]> iter = listResults.iterator();
    	boolean isFirstRowOver = false;
    	while (iter.hasNext()) {
    		if (isFirstRowOver==true) retJsonString.append(",");
    		else isFirstRowOver = true;
    		retJsonString.append("{");
			Object[] rowdata = iter.next();
			for(int i=0;i<jqGridTableVisibleColumns.size();i++) {
	    		JqGridTableColumns aColumn = (JqGridTableColumns)jqGridTableVisibleColumns.get(i);
	    		Object aColData = rowdata[i];
	    		if (i!=0) retJsonString.append(",");
	    		retJsonString.append("\"");
	    		retJsonString.append(aColumn.getViewToDbIdentifier());
	    		retJsonString.append("\"");
	    		retJsonString.append(":");
	    		retJsonString.append("\"");
	    		String dataToAppend = "";
	    		if (aColData!=null) {
	    			if (aColumn.getColumnType().equalsIgnoreCase("Date")) {
	    				dataToAppend = uiFormat.format((Date)aColData);
	    			} else if (aColumn.getColumnType().equalsIgnoreCase("Timestamp")) {
	    				dataToAppend = uiTimestampFormat.format((Date)aColData);
	    			} else if (aColumn.getColumnType().equalsIgnoreCase("String") && aColData.toString().contains("SyndContentImpl")) {
	    				dataToAppend = "Invalid data or no data available"; // This fix added for few rss feed reader having invalid content
	    			} else if (aColumn.getColumnType().equalsIgnoreCase("Boolean")) {
	    				if (aColumn.getDataFormatter()!=null && aColumn.getDataFormatter().trim().length()>0) {
	    					String[] eachValueType = aColumn.getDataFormatter().trim().split(";");
	    					Map<String, String> keyMap = new HashMap<String, String>();
	    					for(int eachValue = 0;eachValue<eachValueType.length;eachValue++) {
	    						String[] eachValues = eachValueType[eachValue].split(":");
	    						keyMap.put(eachValues[0], eachValues[1]);
	    					}
	    					dataToAppend = keyMap.get(aColData.toString());
	    				} else {
		    				if (aColData.toString().equalsIgnoreCase("true")) {
		    					dataToAppend = "<img src='images/up.png'>";
		    				} else {
		    					dataToAppend = "<img src='images/down.png'>";
		    				}
	    				}
	    			} else {
	    				dataToAppend = aColData.toString();
	    			}
	    		}
	    		if (dataToAppend.length()>0 && aColumn.getColumnType().equalsIgnoreCase("number")) {
	    			dataToAppend = decimalFormat.format(Double.valueOf(dataToAppend));
	    		}
	    		retJsonString.append(dataToAppend.replaceAll("\"", "'"));
	    		retJsonString.append("\"");	    		
			}
			retJsonString.append("}");
    	}
    	retJsonString.append("]})");
    	
    	//System.out.println("Json String="+retJsonString.toString());
    	return retJsonString.toString();
    }
    
    private String getFilterSql(Map <String, JqGridTableColumns> mappedjqGridTableVisibleColumns, String filter) {
    	System.out.println("In getFilterSql filter="+filter);
    	String retStr = "";
    	if (filter!=null && filter.length()>0) {
	    	try { 
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(filter);			
				JSONObject jsonObject = (JSONObject) obj;
				String operation = (String)jsonObject.get("groupOp");
				System.out.println("operation="+operation);				
				JSONArray msg = (JSONArray) jsonObject.get("rules");
				Iterator<String> iterator = msg.iterator();
				boolean isFirstFilterOver = false;
				boolean atLeastOneCriteriaAdded = false;
				while (iterator.hasNext()) { // "field":"ScripName","op":"eq","data":"54"
					Object obj1 = iterator.next();
					JSONObject eachRule = (JSONObject)obj1;
					String fieldSection = (String)eachRule.get("field");
					System.out.println("fieldSection="+fieldSection);
					
					String opSection = (String)eachRule.get("op");
					System.out.println("opSection="+opSection);
					
					String dataSection = ((String)eachRule.get("data")).trim();
					System.out.println("data="+dataSection);
					
					if ((fieldSection.length()>0 && opSection.length()>0 && dataSection.length()>0)||(fieldSection.length()>0 && opSection.length()>0 &&(opSection.equals("nu")||opSection.equals("nn")))) {
						JqGridTableColumns columnToUse = mappedjqGridTableVisibleColumns.get(fieldSection);
						System.out.println("columnToUse="+columnToUse);
						
						
						
						if (isFirstFilterOver) retStr = retStr + operation + " ";
						if (columnToUse.getColumnType().equals("String")||columnToUse.getColumnType().equals("Select")) {
							retStr = retStr + "UPPER(" + getFieldNameWithoutAlias(columnToUse.getActualColumnName()) + ")" + getQueryOperation(opSection, dataSection, columnToUse) + " ";
						} else {
							retStr = retStr + getFieldNameWithoutAlias(columnToUse.getActualColumnName()) + getQueryOperation(opSection, dataSection, columnToUse) + " ";
						}
						atLeastOneCriteriaAdded = true;
					}
					isFirstFilterOver = true;
				}
				JSONArray subGroups = (JSONArray) jsonObject.get("groups");				
				if (subGroups!=null && subGroups.size()>0) {
					Iterator<String> subGroupIterator = subGroups.iterator();
					while (subGroupIterator.hasNext()) { // "field":"ScripName","op":"eq","data":"54"
						Object obj1 = subGroupIterator.next();
						JSONObject eachSubGroup = (JSONObject)obj1;
						retStr = retStr + getFilterSql(mappedjqGridTableVisibleColumns, eachSubGroup.toJSONString());
					}					
				}
				if (atLeastOneCriteriaAdded) {
					retStr = " AND (" + retStr + ")";
				}
	    	} catch(Exception exec) {
	    		exec.printStackTrace();
	    	}
	    	
    	}
    	System.out.println(" In getFilterSql retStr="+retStr);    	
    	return retStr;
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    @SuppressWarnings(value="unchecked")
    public void saveUserSearch(String userId, String taleIdentifier, String filter, String searchName) throws BusinessException {
    	System.out.println("In saveUserSearch userId="+userId+" taleIdentifier="+taleIdentifier+" filter="+filter);
    	try {
    		if (userId!=null && userId.length()>0 && taleIdentifier!=null && taleIdentifier.length()>0 && filter!=null && filter.length()>0) {
	    		Query q = entityManager.createNativeQuery("update JQGRID_SAVED_SEARCH set filter_query=? where f_user = "+userId+" and name like '" + searchName + "' and f_jqgrid_table =(select id from jqgrid_table_view where table_identifier like '"+ taleIdentifier + "')");
	    		q.setParameter(1, filter);
	    		int affectedRecords = q.executeUpdate();
	    		if (affectedRecords==0) {
	    			q = entityManager.createNativeQuery("INSERT INTO JQGRID_SAVED_SEARCH(id, name, f_jqgrid_table, f_user, filter_query) values(nextval('JQGRID_SAVED_SEARCH_id_seq'),'" + searchName + "', (select id from jqgrid_table_view where table_identifier like '"+ taleIdentifier + "'), "+userId+", ?)");
	    			q.setParameter(1, filter);
	        		q.executeUpdate();
	    		}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private String getFieldNameWithoutAlias(String inputString) {
    	String fieldNameToUse = "";
		if (inputString.indexOf(" as ")!=-1) {
			fieldNameToUse = inputString.substring(0,inputString.lastIndexOf(" as "));
		} else {
			fieldNameToUse = inputString;
		}
		System.out.println("getFieldNameWithoutAlias="+fieldNameToUse);
		return fieldNameToUse;
    }
    
    private String getQueryOperation(String jqOpearation, String jqDataVal, JqGridTableColumns columnToUse) {
    	String jqData = jqDataVal;
    	String retStr = "";
    	String quoteToUse = "'";
    	String like=" = ";
    	String not = " ! ";
    	if (columnToUse.getColumnType().equals("Integer") || columnToUse.getColumnType().equals("number")) quoteToUse = "";
    	else if (columnToUse.getColumnType().equals("String") || columnToUse.getColumnType().equals("Select")){
    		like=" LIKE ";
        	not = " NOT ";
        	jqData = jqData.toUpperCase();
    	} else if (columnToUse.getColumnType().equals("Select") || columnToUse.getColumnType().equals("SelectManyToMany")){
    		like=" IN ";
        	not = " NOT ";        	
    	} else if (columnToUse.getColumnType().equalsIgnoreCase("date") || columnToUse.getColumnType().equalsIgnoreCase("timestamp") ) {
    		if (jqData.length()>0) {
	    		try {
		    		SimpleDateFormat uiFormat = new SimpleDateFormat("dd/MM/yyyy");
		    		SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
		    		Date dateSelected = uiFormat.parse(jqData);
		    		if (columnToUse.getColumnType().equalsIgnoreCase("timestamp") && jqOpearation.equalsIgnoreCase("le")) {
		    			Calendar cal = Calendar.getInstance();
		    			cal.setTime(dateSelected);
		    			cal.add(Calendar.DATE, 1);
		    			jqData = dbFormat.format(cal.getTime());
		    		} else {
		    			jqData = dbFormat.format(dateSelected);
		    		}
	    		} catch(Exception ex) {
	    			ex.printStackTrace();
	    		}
    		}
    	}
    	if (columnToUse.getSearchWhereClause()!=null) {
    		if ("Watchlist".equals(columnToUse.getViewToDbIdentifier())||"SecondWatchlist".equals(columnToUse.getViewToDbIdentifier())) {
    			jqData = "(" + getWatchlistFetchQuery(jqDataVal) + ")"; 
    			quoteToUse = "";
    		} else {
	    		String queryPart = columnToUse.getSearchWhereClause();
	    		System.out.println("queryPart="+queryPart);
	    		jqData = queryPart.replaceAll("paramValue", jqDataVal);
	    		quoteToUse = "";
    		}
    	}
    	
    	if (jqOpearation.equalsIgnoreCase("eq")) retStr = like + quoteToUse + jqData + quoteToUse;
    	else if (jqOpearation.equalsIgnoreCase("ne")) retStr = not + like + quoteToUse + jqData + quoteToUse;
    	else if (jqOpearation.equalsIgnoreCase("bw")) {
    		if (jqData.endsWith("$")) {
    			retStr = like + quoteToUse + jqData.substring(0, jqData.length()-1) + quoteToUse;
    		} else {
    			retStr = like + quoteToUse + jqData + "%" + quoteToUse; //Begins with
    		}
    	}
    	else if (jqOpearation.equalsIgnoreCase("bn")) retStr = not + like + quoteToUse + jqData + "%" + quoteToUse; //Does not begin with
    	else if (jqOpearation.equalsIgnoreCase("ew")) retStr = like + quoteToUse + "%" + jqData + quoteToUse; //Ends with
    	else if (jqOpearation.equalsIgnoreCase("en")) retStr = not + like + quoteToUse + "%" + jqData + quoteToUse; //Does not end with
    	else if (jqOpearation.equalsIgnoreCase("cn")) retStr = like + quoteToUse + "%" + jqData + "%" + quoteToUse; //Contains
    	else if (jqOpearation.equalsIgnoreCase("nc")) retStr = not + like + quoteToUse + "%" + jqData + "%" + quoteToUse; //Does not Contains
    	else if (jqOpearation.equalsIgnoreCase("nu")) retStr = " is null "; // is null
    	else if (jqOpearation.equalsIgnoreCase("nn")) retStr = " is not null "; // is not null
    	else if (jqOpearation.equalsIgnoreCase("in")) retStr = " in (" + quoteToUse + jqData + quoteToUse + ")"; // is in ()
    	else if (jqOpearation.equalsIgnoreCase("ni")) retStr = " is not in (" + quoteToUse + jqData + quoteToUse + ")"; // not in
    	else if (jqOpearation.equalsIgnoreCase("lt")) retStr = " < " + quoteToUse + jqData + quoteToUse; // less than
    	else if (jqOpearation.equalsIgnoreCase("le")) retStr = " <= " + quoteToUse + jqData + quoteToUse; // less or equal to
    	else if (jqOpearation.equalsIgnoreCase("gt")) retStr = " > " + quoteToUse + jqData + quoteToUse; // greater than
    	else if (jqOpearation.equalsIgnoreCase("ge")) retStr = " >= " + quoteToUse + jqData + quoteToUse; // greater or equal    	
    	return retStr;
    }
    
    private String getWatchlistFetchQuery(String watlistId) {
    	String retStr = "";
    	WatchlistDao wlDao = new WatchlistDao(entityManager);
    	retStr = wlDao.getWatchlistById(Long.parseLong(watlistId)).getScripFecthSql();
    	return retStr;
    }
    
    private String getOrderSqlPart(Map <String, JqGridTableColumns> mappedjqGridTableVisibleColumns, Map<String, String> allRequestQueryParams) {
    	String retStr = "";
    	String allSortParam = "";
    	if (allRequestQueryParams.get("sidx")!=null && allRequestQueryParams.get("sidx").length()>0) { 
    		allSortParam = allRequestQueryParams.get("sidx").trim() + " " + allRequestQueryParams.get("sord");
    	}
    	System.out.println("allSortParam="+allSortParam);
    	if (allSortParam.length()>0) {
    		String[] allParamsArray = allSortParam.split(",");
    		for(int i=0;i<allParamsArray.length;i++) {
    			String[] eachField = (allParamsArray[i].trim()).split(" ");
    			if (eachField.length>1) {
    				JqGridTableColumns mappedField = mappedjqGridTableVisibleColumns.get(eachField[0]);
    				if (i!=0) retStr = retStr + ", ";
    				String fieldNameToUse = getFieldNameWithoutAlias( mappedField.getActualColumnName());
    				retStr = retStr + " " + fieldNameToUse + " " + eachField[1];
    			}
    		}
    	}
    	System.out.println(" In getOrderSqlPart retStr="+retStr);    	
    	return retStr;
    }
    
    private String getAdditionalClauses(List<JqGridTableColumns> jqGridTableInvisibleColumns, Map<String, String> allRequestQueryParams) {
    	String retStr = "";
    	for(int i=0;i<jqGridTableInvisibleColumns.size();i++) {
    		JqGridTableColumns anInvisibleColumn = jqGridTableInvisibleColumns.get(i);
    		
    		if (anInvisibleColumn.getIsSearchable()==false) { // Both invisible & non searchable means to be used in where clause and params are passed from servlet session like UserId/Owner etc
    			
    			if (allRequestQueryParams.get(anInvisibleColumn.getViewToDbIdentifier())!=null) {
    				retStr = retStr + " AND " + anInvisibleColumn.getActualColumnName() + "='" + allRequestQueryParams.get(anInvisibleColumn.getViewToDbIdentifier())+ "'";
    			}
    		}
    	}
    	System.out.println(" In getAdditionalClauses retStr="+retStr);    	
    	return retStr;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<KeyValueDTO> getSelectList(Long jqColumnId, String loggedUserId) throws BusinessException {
    	List<KeyValueDTO> retList = new ArrayList<KeyValueDTO>();
    	log.debug("In JqGridManagerBean getJsonData jqColumnId="+jqColumnId+" loggedUserId="+loggedUserId);
    	JqGridTableColumns jqGridTableColumn = (JqGridTableColumns)this.entityManager.createNamedQuery("JqGridColumn.getJqGridColumnById").setParameter("jqColumnId", jqColumnId).getSingleResult();
    	if (jqGridTableColumn!=null) {
    		String selectQuery = jqGridTableColumn.getSelectQuery().replaceAll("UserID", loggedUserId);
    		Query q = entityManager.createNativeQuery(selectQuery);  
    		List<Object[]> listResults = q.getResultList();
        	Iterator<Object[]> iter = listResults.iterator();
        	while (iter.hasNext()) {
    			Object[] rowdata = iter.next();
    			Object keyPart = rowdata[0];
    			Object valPart = rowdata[1];
    			retList.add(new KeyValueDTO(keyPart.toString(), valPart.toString()));
        	}
    	}
    	
    	
    	return retList;
    }
    
}
  
