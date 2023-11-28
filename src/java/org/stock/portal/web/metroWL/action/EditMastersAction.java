package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.service.master.MasterManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class EditMastersAction extends BaseAction {

	Logger log = Logger.getLogger(EditMastersAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	 
    private Long pkId;
    private String tbl;
    private String col;
    
    private String newValue;
    
    @InjectEJB (name ="MasterManager")
    MasterManager masterManager;
    
   
    public EditMastersAction() {
        super();
    }
    
    public String prepareEdit() {  
    	return INPUT;    	
    }

    public String save() {    	
        String retVal = "responsePage";
        try {
        	if (newValue.trim().length()>0) {
        		int recCount = masterManager.saveMasterValue(this.pkId,this.tbl,this.col, this.newValue);
        		if (recCount==0) {
        			retVal = INPUT;
            		addActionError("No records with such criteria found.");
        		}
        	} else {
        		retVal = INPUT;
        		addActionError("Invalid input. Please check again.");
        	}
        } catch(BusinessException e){
        	log.error(e);
        	retVal = INPUT;
        	addActionError(e.getMessage());
        } catch (Exception e) {
        	log.error(e);
        	retVal = INPUT;
            addActionError(e.getMessage());
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getTbl() {
		return tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}	
}
