package org.stock.portal.web.taglib;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.stock.portal.common.JqDataUtil;
import org.stock.portal.common.ServiceLocator;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.JqGridTable;
import org.stock.portal.domain.JqGridTableColumns;
import org.stock.portal.domain.User;
import org.stock.portal.domain.dto.KeyValueDTO;
import org.stock.portal.service.jqGrid.JqGridManager;
import org.stock.portal.web.util.Constants;
 
public class JqGridTableBuilder extends SimpleTagSupport {
    private String tableIdentifier;
    
    private Boolean formRequired;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }
    
    private HttpServletRequest getReuqest() {
    	PageContext pageContext = (PageContext) getJspContext();  
    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();  
    	return request;
    }
    
    private List<JqGridTableColumns> reArrangebyTabOrder(List<JqGridTableColumns> originalList) {
    	List<JqGridTableColumns> retList = new ArrayList<JqGridTableColumns>(originalList);
    	for(int i=0;i<retList.size()-1;i++) {
    		for(int j=i+1;j<retList.size();j++) {
    			if (retList.get(i).getTabOrder()>retList.get(j).getTabOrder()) {
    				JqGridTableColumns tempCol = retList.get(i);
    				retList.set(i, retList.get(j));
    				retList.set(j, tempCol);
    			}
    		}
    	}
    	
    	return retList;
    }
    
    private boolean isTradingDay(Calendar cal) {
    	boolean retVal = true;
    	if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) retVal = false;
    	//if (cal.get(Calendar.HOUR_OF_DAY)<19) retVal = false;
    	return retVal;
    }
    
    private String getActiveTradingDay(int numberOfDaysAhead) {
    	String retStr = "";
    	try {
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.DATE, numberOfDaysAhead);
    		while(!isTradingDay(cal)) {
    			cal.add(Calendar.DATE, numberOfDaysAhead);
    			if (numberOfDaysAhead==0) cal.add(Calendar.DATE, -1);
    		}
    		retStr = dateFormat.format(cal.getTime());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retStr;
    }
    
    private String getPastDate(int numberOfDaysAhead) {
    	String retStr = "";
    	try {
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.DATE, numberOfDaysAhead);   
    		retStr = dateFormat.format(cal.getTime());
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retStr;
    }
    
    private void generateFilterForm(JqGridTable jqGridTable, List<JqGridTableColumns> originalColumns, List<KeyValueDTO> userSavedSearches, Writer out) throws IOException, BusinessException {
    	out.write("<form id=\"form" + jqGridTable.getTableIdentifier()+"\" action=\"#\" method=\"post\">");
    	out.write("<table class=\"formTable\" datatable=\"0\">");
    	out.write("<tbody>");
    	
    	int curRow = 0;
    	int processedColumnCount = 0;
    	boolean isDateFieldFound = false;
    	if (originalColumns!=null && originalColumns.size()>0) {
	    	do {
	    		boolean isThisColumnAdded = false;
	    		List<JqGridTableColumns> jqGridTableColumns = reArrangebyTabOrder(originalColumns);
	    		JqGridTableColumns aColumn = jqGridTableColumns.get(curRow);
	    		if (processedColumnCount%3==0) { out.write("<tr class=\"row\">");}
	    		if (aColumn.getIsSearchable()) {
	    			if (!aColumn.getColumnType().equalsIgnoreCase("Index")) {
	    				out.write("<td><label class=\"fieldLabel\">" + aColumn.getDisplayName() + ":</label></td>");
	    				out.write("<td class=\"inputField\">");
	    				out.write("	<div>");
	    				
	    				String minValueToUse = "";
	    				String maxValueToUse = "";
	    				if (aColumn.getMinimumFilterValue()!=null) minValueToUse = aColumn.getMinimumFilterValue();
	    				if (aColumn.getMaximumFilterValue()!=null) maxValueToUse = aColumn.getMaximumFilterValue();
	    				
		    			if ( aColumn.getColumnType().equalsIgnoreCase("String")) {
		    				out.write("		<input name=\"" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + minValueToUse + "\" id=\"" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    			} else if (aColumn.getColumnType().equalsIgnoreCase("Boolean")) {
		    				out.write("<select name=\"" + aColumn.getViewToDbIdentifier()+ "\" id=\"" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\">");
		    				out.write("<option value=\"\">Select</option>");
		    				out.write("<option value=\"true\">true</option>");
		    				out.write("<option value=\"false\">false</option>");
		    				out.write("</select>");
		    			} else if (aColumn.getColumnType().equalsIgnoreCase("Select") || aColumn.getColumnType().equalsIgnoreCase("SelectManyToMany")){
		    				out.write("<select name=\"" + aColumn.getViewToDbIdentifier()+ "\" id=\"" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\">");
		    				JqGridManager jqGridManager = (JqGridManager)ServiceLocator.getInstance().getServiceFacade(JqGridManager.class);
		    				String loggedUserId = "-1";
		    	        	HttpServletRequest request = getReuqest();
		    	    		if (request.getSession(false)!=null && request.getSession(false).getAttribute((Constants.LOGGED_IN_USER))!=null) {
		    	        		loggedUserId = ((User)request.getSession().getAttribute(Constants.LOGGED_IN_USER)).getId() + "";
		    	        	}
		    				List<KeyValueDTO> selectOptions = jqGridManager.getSelectList(aColumn.getId(), loggedUserId);
		    				out.write("<option value=\"\">Select</option>");
	        				for(int rowCount=0;rowCount<selectOptions.size();rowCount++) {
	        					KeyValueDTO aDto = selectOptions.get(rowCount);
	        					out.write("<option value=\""+ aDto.getKeyLabel() + "\">" + aDto.getKeyValue() + "</option>");
	        				}
		    				out.write("</select>");
		    			} else if ( aColumn.getColumnType().equalsIgnoreCase("date") || aColumn.getColumnType().equalsIgnoreCase("Timestamp")) {
		    				out.write("		<input name=\"min" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + minValueToUse + "\" id=\"min" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    				out.write("		<input name=\"max" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + maxValueToUse + "\" id=\"max" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    				out.write("		<a href=\"javascript:void(0)\" onclick=\"javascript:jqGridFormChangeDate('form" + jqGridTable.getTableIdentifier() + "', '" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "', -1)\">[<]</a>");
		    				out.write("		<a href=\"javascript:void(0)\" onclick=\"javascript:jqGridFormChangeDate('form" + jqGridTable.getTableIdentifier() + "', '" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "', 0)\">[=]</a>");
		    				out.write("		<a href=\"javascript:void(0)\" onclick=\"javascript:jqGridFormChangeDate('form" + jqGridTable.getTableIdentifier() + "', '" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "', 1)\">[>]</a>");
		    				
		    				isDateFieldFound = true;
		    			} else if (aColumn.getColumnType().equalsIgnoreCase("Number") || aColumn.getColumnType().equalsIgnoreCase("Integer")){
		    				out.write("		<input name=\"min" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + minValueToUse + "\" id=\"min" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    				out.write("		<input name=\"max" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + maxValueToUse + "\" id=\"max" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    			} else if (aColumn.getColumnType().equalsIgnoreCase("Unique")) {
		    				out.write("		<input name=\"Unique" + aColumn.getViewToDbIdentifier()+ "\" value=\"" + minValueToUse + "\" id=\"Unique" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() +"\" class=\"textinput\" type=\"text\">");
		    			}
		    			out.write("	</div>");
	    				out.write("</td>");	
		    			isThisColumnAdded=true;
	    			}
	    		}    		
	    		if (isThisColumnAdded) processedColumnCount++;
	    		if (processedColumnCount%3==0) {
	    			out.write("</tr>");
	    		}
	    		curRow++;
	    	} while(curRow<originalColumns.size());
    	}
    	if (processedColumnCount%3!=0) {
    		for(int i=processedColumnCount%3;i<3;i++) {
    			out.write("<td></td>");
    		}
    		out.write("</tr>");
    	}
    	
    	out.write("<tr>");
    	out.write("<td colspan=\"5\"  style=\"text-align:right\" class=\"selectopts\">");
    	out.write("Use pre saved search:");
    	out.write("<select onchange=\"populateSavedSearch(this, 'form" + jqGridTable.getTableIdentifier()+"', '" + jqGridTable.getTableIdentifier()+"')\" name=\"preSavedSearch" + jqGridTable.getTableIdentifier() + "\" id=\"preSavedSearch" + jqGridTable.getTableIdentifier() + "\" class=\"textinput\">");
    	out.write("<option value=\"\">Select</option>");
    	
    	if (userSavedSearches!=null) {			
			for(int rowCount=0;rowCount<userSavedSearches.size();rowCount++) {
				KeyValueDTO aDto = userSavedSearches.get(rowCount);
				out.write("<option value=\"" + aDto.getEncodedKeyValue() + "\">" + aDto.getKeyLabel() + "</option>");
			}        			
		}
    	
    	
    	
    	out.write("</select>");
    	out.write("</td>");
    	
    	out.write("<td style=\"text-align:right\" class=\"EditButton\">");
    	
    	out.write("<a class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left ui-search\" href=\"javascript:void(0)\" onclick=\"javascript:clearForm('form" + jqGridTable.getTableIdentifier()+"');\">");
    	out.write("<span class=\"ui-icon ui-icon-arrowreturnthick-1-w\"></span>Clear Filter");
    	out.write("</a>&nbsp;");
    	
    	out.write("<a class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left ui-reset\" href=\"javascript:void(0)\" onclick=\"javascript:submitJqGridFilter('jqGrid_" + jqGridTable.getTableIdentifier()+ "');\">");
    	out.write("<span class=\"ui-icon ui-icon-search\"></span>Search");
    	out.write("</a>");

    	out.write("</td>");
    	out.write("</tr>");
    	
    	out.write("<tr><td></td></tr>");
    	
    	out.write("</tbody>");
    	out.write("</table>");
    	out.write("</form>");
    	if (isDateFieldFound) {
    		out.write("<script language=\"Javascript\">");
    		out.write("$(function() {");
    		for(int i=0;i<originalColumns.size();i++) {
    			JqGridTableColumns aColumn = originalColumns.get(i);
    			if ( aColumn.getColumnType().equalsIgnoreCase("date") || aColumn.getColumnType().equalsIgnoreCase("timestamp")) {
    				String maxDate = getActiveTradingDay(0);
    				String minDate = "";
    				if (aColumn.getCustomFieldSetter()!=null) {    						
						Class classToUse = JqDataUtil.class;
						try {
							Object obj = classToUse.newInstance();
							Class noparams[] = {};
							Method method = classToUse.getDeclaredMethod(aColumn.getCustomFieldSetter(), noparams);
							minDate = (String) method.invoke(obj, null);
							maxDate = minDate;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    						
    				} else if (aColumn.getDateGapDays()!=null) {
    					minDate = getPastDate(-1*aColumn.getDateGapDays());
    				} else {
    					minDate = getPastDate(-1);
    				}
    				out.write("$( \"#min" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "\" ).datepicker();");
    				out.write("$( \"#min" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "\" ).val('"+minDate+"');");
    				out.write("$( \"#max" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "\" ).datepicker();");
    				out.write("$( \"#max" + jqGridTable.getTableIdentifier() + "_" + aColumn.getViewToDbIdentifier() + "\" ).val('"+maxDate+"');");
    			}
    		}    		
    		out.write("});");
    		out.write("</script>");
    	}
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public void doTag() throws JspException {
        Writer out = getWriter();
        
        System.out.println("In JqGridTableBuilder tableIdentifier="+tableIdentifier);        

 
        try {
        	String loggedUserId = "-1";
        	HttpServletRequest request = getReuqest();
    		if (request.getSession(false)!=null && request.getSession(false).getAttribute((Constants.LOGGED_IN_USER))!=null) {
        		loggedUserId = ((User)request.getSession().getAttribute(Constants.LOGGED_IN_USER)).getId() + "";
        	}
    		
        	JqGridManager jqGridManager = (JqGridManager)ServiceLocator.getInstance().getServiceFacade(JqGridManager.class);
        	Map<String, Object> tableViewDetailsMap = jqGridManager.getJqGridTableDetails(this.tableIdentifier,loggedUserId);
        	JqGridTable jqGridTable = (JqGridTable)tableViewDetailsMap.get("JqGridTable");
        	if (jqGridTable!=null) {
        		
        		List<JqGridTableColumns> jqGridTableColumns = (List<JqGridTableColumns>)tableViewDetailsMap.get("JqGridTableColumns");
        		if (formRequired) {
        			generateFilterForm(jqGridTable, jqGridTableColumns, (List<KeyValueDTO>)tableViewDetailsMap.get("UserSavedSearches"), out);
        		}
        		out.write("<table id=\"jqGrid_" + jqGridTable.getTableIdentifier()+ "\"></table>");
        		out.write("<div id=\"jqGridPager_" + jqGridTable.getTableIdentifier()+ "\"></div>");
        		
        		out.write("<script type=\"text/javascript\">");
        		out.write("var theScreenWidth = screen.width - 265;");
        		if (jqGridTable.getGridHeight()!=null) {
        			out.write("var gridHeight = " + jqGridTable.getGridHeight()+ ";");
        		} else {
        			out.write("var gridHeight = 400;");
        		}
        		
        		out.write("$(document).ready(function () {");
        		out.write("$(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid({");
        		out.write("		url: '" + request.getContextPath() + "/servlet/JqGridDataProviderServlet?callback=?&viewTableIdentifier="+jqGridTable.getTableIdentifier()+"' , ");
        		out.write("		mtype: \"GET\" , ");
        		if (formRequired) {
	        		out.write("postData: {");
	        		out.write("customFilters: function() {  return getFormToJsonFilter('form" + jqGridTable.getTableIdentifier()+"'); }");
	        		out.write("},");
        		}
//        		 postData: {
//			        StateId: function() { return jQuery("#StateId option:selected").val(); },
//			        CityId: function() { return jQuery("#CityId option:selected").val(); },
//			        hospname: function() { return jQuery("#HospitalName").val(); }
//			    }
        		out.write("		datatype: \"jsonp\" , ");
        		//out.write("		toppager: true,"); // Clone the navigation/pagination toolbar at top 
        		out.write("		colModel: [");
        		
        		boolean isFirstColumnAdded = false;
        		for(int i=0;i<jqGridTableColumns.size();i++) {
        			JqGridTableColumns aColumn = jqGridTableColumns.get(i);
        			if (aColumn.getIsSearchable() || aColumn.getIsVisible()) {
        				
        				if (isFirstColumnAdded) out.write(","); // Do not add comma for last column, Now adding second+ column so add a comma.
        				
	        			out.write("{ label: '" + aColumn.getDisplayName() + "', name: '" + aColumn.getViewToDbIdentifier()+"'");
	        			if (aColumn.getIsVisible()) out.write(", width: "+aColumn.getUiWidth());
	        			else out.write(", hidden: true");
	        			
	        			if ( aColumn.getColumnType().equalsIgnoreCase("String")) {
	        				out.write(", sorttype: 'text'");
	        				if (aColumn.getIsVisible()==false && aColumn.getIsSearchable()==true) out.write(", searchoptions: { searchhidden: true }");
	        			} else if (aColumn.getColumnType().equalsIgnoreCase("Select") || aColumn.getColumnType().equalsIgnoreCase("SelectManyToMany")){	        				
	        				
	        				out.write(",stype: \"select\"");
	        				out.write(", searchoptions: { ");
	        				if (aColumn.getIsVisible()==false && aColumn.getIsSearchable()==true) out.write("searchhidden: true,");
	        				out.write(" value: \":[All];"); // // searchoptions value - name values pairs for the dropdown - they will appear as options like ALFKI:ALFKI;ANATR:ANATR;WOLZA:WOLZA\" }");
	        				List<KeyValueDTO> selectOptions = jqGridManager.getSelectList(aColumn.getId(), loggedUserId);
	        				for(int rowCount=0;rowCount<selectOptions.size();rowCount++) {
	        					KeyValueDTO aDto = selectOptions.get(rowCount);
	        					out.write(aDto.getKeyLabel()+":"+aDto.getKeyValue());
	        					if (rowCount!=selectOptions.size()-1) out.write(";");
	        				}
	        				out.write("\"");
	        				out.write(", sopt:['eq','ne']");
	        				out.write(" }");
	        			} else if ( aColumn.getColumnType().equalsIgnoreCase("Index")) {
	        				out.write(", hidden: true,key: true,searchhidden:false");
	        			} else if (aColumn.getColumnType().equalsIgnoreCase("Number") || aColumn.getColumnType().equalsIgnoreCase("Integer") || aColumn.getColumnType().equalsIgnoreCase("Decimal")  || aColumn.getColumnType().equalsIgnoreCase("Unique")){
	        				out.write(", sorttype: '" + aColumn.getColumnType()+"'");
	        				out.write(", align: \"right\"");
	        			} else {
	        				out.write(", sorttype: '" + aColumn.getColumnType()+"'");
	        			}
	        			
	        			if ( aColumn.getColumnType().equalsIgnoreCase("date") || aColumn.getColumnType().equalsIgnoreCase("timestamp")) {
	        				out.write(", searchoptions: {");
	        				out.write("dataInit: function (element) {"); // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	        				out.write("$(element).datepicker({"); // use it to place a third party control to customize the toolbar
	        				out.write("id: 'orderDate_datePicker',");
	        				out.write("dateFormat: 'dd/mm/yy',	");
	        				out.write("showOn: 'focus'");
	        				out.write(" });");
	        				out.write("  }");
	        				out.write("}");
	        			}
	        			if (isFirstColumnAdded==false) out.write(", frozen: true"); //First column the Scrip Name is always Frozen
	        			out.write(" } ");
	        			isFirstColumnAdded = true;
        			}
        		}
        		out.write("],");
        		if (jqGridTable.getOnSelectRowFunction()!=null && jqGridTable.getOnSelectRowFunction().length()>0) {
	        		out.write("onSelectRow: function(id) {");
	        		out.write(jqGridTable.getOnSelectRowFunction());
	        		//out.write("showFloatingColorBox('/portal/scripInfo.do', 'jqIndex='+id , 800, 600)");
	        		out.write("}, ");
        		}
        		out.write("viewrecords: true,");
        		out.write("width: theScreenWidth,height: gridHeight,rowNum: 50,rowList: [25,50,100,200], shrinkToFit: false, altRows: true, sortable: true, multiSort: false,");
        		out.write("pager: \"#jqGridPager_" + jqGridTable.getTableIdentifier()+ "\",");
        		out.write("caption: \"Search Results of " + jqGridTable.getDisplayHeader() + "\"");
        		out.write("});");
        		
        		if (tableViewDetailsMap.get("UserSavedSearches")!=null) {
        			List<KeyValueDTO> userSavedSearches = (List<KeyValueDTO>)tableViewDetailsMap.get("UserSavedSearches");
        			for(int rowCount=0;rowCount<userSavedSearches.size();rowCount++) {
        				KeyValueDTO aDto = userSavedSearches.get(rowCount);
        				out.write("var tmplt_" + aDto.getKeyLabel() + " = "+ aDto.getKeyValue() + ";");
        			}        			
        		}
        		
        		out.write("$('#jqGrid_" + jqGridTable.getTableIdentifier()+ "').navGrid(\"#jqGridPager_" + jqGridTable.getTableIdentifier()+ "\", {");
        		out.write("search: true, add: false, edit: false, del: false, refresh: true");
        		out.write("},");
        		out.write("{}, "); // edit options 
        		out.write("{}, "); // add options 
        		out.write("{}, "); // delete options
        		out.write("{ multipleSearch: true, multipleGroup: true "); // search options - define multiple search
        		if (tableViewDetailsMap.get("UserSavedSearches")!=null) {
        			List<KeyValueDTO> userSavedSearches = (List<KeyValueDTO>)tableViewDetailsMap.get("UserSavedSearches");
        			out.write(",tmplNames: [");
        			for(int rowCount=0;rowCount<userSavedSearches.size();rowCount++) {
        				KeyValueDTO aDto = userSavedSearches.get(rowCount);
        				if (rowCount!=0) out.write(",");
        				out.write("\"" + aDto.getKeyLabel() + "\"");
        			}
        			out.write("]");
        			out.write(",tmplFilters: [");
        			for(int rowCount=0;rowCount<userSavedSearches.size();rowCount++) {
        				KeyValueDTO aDto = userSavedSearches.get(rowCount);
        				if (rowCount!=0) out.write(",");
        				out.write("tmplt_" + aDto.getKeyLabel());
        			}
        			out.write("]");
        		}
        		out.write(" } ");
        		out.write(");");
        		out.write("jQuery(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid('gridResize',{maxWidth: theScreenWidth, minWidth:350,minHeight:80});;");
        		out.write("$('#jqGrid_" + jqGridTable.getTableIdentifier()+ "').navButtonAdd('#jqGridPager_" + jqGridTable.getTableIdentifier()+ "',");
        		out.write("{");
        		out.write("buttonicon: \"ui-icon-calculator\",");
        		out.write("title: \"Column chooser\",");
        		out.write("caption: \"Columns\",");
        		out.write("position: \"last\",");
        		out.write("onClickButton: function() {");
        		out.write("jQuery(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid('columnChooser');");
        		out.write("}");
        		out.write("});");
        		out.write("$(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid('bindKeys');");

        		out.write("$(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid('setFrozenColumns');");
        		out.write("$(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").jqGrid('setGridWidth', theScreenWidth);");
        		out.write(" });");
        		
        		//out.write("$(\"#jqGrid_" + jqGridTable.getTableIdentifier()+ "\").triggerHandler(\"jqGridAfterGridComplete\");");

        		out.write("</script>");
        	}
        } catch (java.io.IOException ex) {
            throw new JspException("Error in JqGridTableBuilder tag", ex);
        } catch (BusinessException ex) {
        	throw new JspException("Error in JqGridTableBuilder tag", ex);
        }
    }
    
	public String getTableIdentifier() {
		return tableIdentifier;
	}

	public void setTableIdentifier(String tableIdentifier) {
		this.tableIdentifier = tableIdentifier;
	}

	public Boolean getFormRequired() {
		return formRequired;
	}

	public void setFormRequired(Boolean formRequired) {
		this.formRequired = formRequired;
	}
 
    
}