package org.stock.portal.web.taglib;

import java.io.Writer;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
 
public class Paginator extends SimpleTagSupport {
    private int currentPageNumber;
    private int recordsPerPage;
    private int totalRecords;
 
    private String formName;
    private String resultDivName;
    
    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }
 
    @Override
    public void doTag() throws JspException {
        Writer out = getWriter();
        
        int firstPage = 1;
        int lastPage = totalRecords/recordsPerPage; 
        System.out.println("In Taglib totalRecords="+totalRecords+" recordsPerPage="+recordsPerPage+" lastPage="+lastPage+" currentPageNumber="+currentPageNumber);
        if (totalRecords%recordsPerPage!=0) lastPage = lastPage + 1;
        System.out.println("In Taglib lastPage="+lastPage);
        
//        <ul class="pagination clear">
//			<li class="prev_page"><a href="/page-4/">prev<em> page</em></a></li>
//			<li><a href="../">1</a></li>
//			<li><span>...</span></li>
//			<li><a href="/page-2/">2</a></li>
//			<li><a href="/page-3/">3</a></li>
//			<li><a href="/page-4/">4</a></li>
//			<li class="active"><a href="">5</a></li>
//			<li><a href="/page-6/">6</a></li>
//			<li><a href="/page-7/">7</a></li>
//			<li><a href="/page-8/">8</a></li>
//			<li class="next_page"><a href="/page-6/">next<em> page</em></a></li>
//		</ul>
 
        try {
        	
            out.write("<ul class=\"pagination\">");
            out.write("<li>Showing "+((currentPageNumber-1)*recordsPerPage+1)+" to "+(currentPageNumber*recordsPerPage<totalRecords?currentPageNumber*recordsPerPage:totalRecords)+" of "+totalRecords+"&nbsp;&nbsp;&nbsp;&nbsp;</li>");
            if (currentPageNumber!=firstPage) {
            	out.write("<li class=\"prev_page\"><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','" + (new Integer(currentPageNumber-1)) + "','" + recordsPerPage + "','"+ resultDivName +"')\">prev<em> page</em></a></li>");
            	out.write("<li><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','1','" + recordsPerPage + "','"+ resultDivName +"')\">1</a></li>");
            }
            if (currentPageNumber>(firstPage+2)) {
            	out.write("<li><span>...</span></li>");            	
            }
            if (currentPageNumber>2) { // One befor current active page
            	out.write("<li><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','"+(currentPageNumber-1)+"','" + recordsPerPage + "','"+ resultDivName +"')\">" + (currentPageNumber-1) + "</a></li>");
            }
            out.write("<li class=\"active\"><a href=\"javascript:void(0)\">"+currentPageNumber+"</a></li>");
            if (currentPageNumber<lastPage-1) {
            	out.write("<li><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','"+(currentPageNumber+1)+"','" + recordsPerPage + "','"+ resultDivName +"')\">"+(currentPageNumber+1)+"</a></li>");
            }
            if (currentPageNumber<(lastPage-2)) {            	
            	out.write("<li><span>...</span></li>");
            }
            if (currentPageNumber!=lastPage) {
            	out.write("<li><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','"+lastPage+"','" + recordsPerPage + "','"+ resultDivName +"')\">"+lastPage+"</a></li>");
            	out.write("<li class=\"next_page\"><a href=\"javascript:void(0)\" onclick=\"sortMainPageTable('"+formName+"','','','" + (new Integer(currentPageNumber+1)) + "','" + recordsPerPage + "','"+ resultDivName +"')\">next<em> page</em></a></li>");        	
            }
            out.write("</ul>");            
 
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }
    
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getResultDivName() {
		return resultDivName;
	}

	public void setResultDivName(String resultDivName) {
		this.resultDivName = resultDivName;
	}
 
    
}