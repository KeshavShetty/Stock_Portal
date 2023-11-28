package org.stock.portal.service.data.mdb;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.service.data.DataMutatorManager;

@MessageDriven(name="DailyDataProcessorMDB", activationConfig = {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue=SPConstants.DATA_PROCESS_QUEUE_NAME)
    })
public class DataProcessorMDB implements MessageListener {
	
	@EJB
    DataMutatorManager dmManager;
         
    private static final long serialVersionUID = 7787506959720025502L;
    
    protected final static Logger log = Logger.getLogger(DataProcessorMDB.class.getName());

    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void onMessage(javax.jms.Message message) {
    	//log.debug("onMessage()....");
        try {
        	ObjectMessage msg = (ObjectMessage)message;
        	Map<String, Object> messageMap = (HashMap<String, Object>)msg.getObject();
            String action = (String) messageMap.get("Action");
            
            if (action.equals("UpdateIntraday") ) {
            	Object targetObj = messageMap.get("TargetObject");
            	
//            	ProcessBSEIntraDayData bseIDayProcessor = new ProcessBSEIntraDayData();
//            	bseIDayProcessor.processIntradayaData(((IntradayDataProcess)targetObj).getScrip());
//            	bseIDayProcessor.releaseDb(); 
//            	bseIDayProcessor = null;
            } else if (action.equals("UpdateEODData")) {
            	//insertEodData(messageMap);  
            	//log.debug("Inserted EOD data ");
            } else if (action.equals("PopulateTreePerformance")) {
            	Object sourceScripId = messageMap.get("SourceScripId");
            	System.out.println("onMessage for PopulateTreePerformance sourceScripId="+sourceScripId);
            	//dmManager.populateTreePerformance((Long)sourceScripId);
            } else {
            	
            }
        } catch(Throwable t) {
        	t.printStackTrace();
            log.error(t);
        } finally {
            ;
        }
    }
    
    
    private void insertEodData(Map dataMap) throws BusinessException {
    	
    	dmManager.insertEodData(dataMap);
    }   
}

