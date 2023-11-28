package org.stock.portal.service.data;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.ScripDao;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.IntradayDataProcess;
import org.stock.portal.web.util.Constants;

//import org.hornetq.api.core.TransportConfiguration;
//import org.hornetq.api.jms.HornetQJMSClient;
//import org.hornetq.api.jms.JMSFactoryType;
//import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

@Stateless(name="DailyProcessorManager", mappedName="org.stock.portal.service.data.DailyProcessorManager")
public class DailyProcessorManagerBean implements DailyProcessorManager {
	@Resource 
	private SessionContext context;
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	@Resource( mappedName="java:/JmsXA" )
	private QueueConnectionFactory jmsConnectionFactory;

	@Resource( mappedName="java:/"+SPConstants.DATA_PROCESS_QUEUE_NAME)
	private Queue dataRepoDestination;

	private static Logger log = Logger.getLogger(DailyProcessorManager.class.getName());
	
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public void processBseIntraData() throws BusinessException {
		log.debug("Begin processData...");
		System.out.println("Begin processData...");
		List scripList = (new ScripDao(entityManager)).getBseScripByAverageVolume(5000L);
		//List scripList = (new ScripDao(entityManager)).getBseScripByAverageVolumeDummy(5000L);
		
		for(int i=0;i<scripList.size();i++) {
			Scrip aScr = (Scrip)scripList.get(i);
			IntradayDataProcess aDto = new IntradayDataProcess();
			aDto.setExchangeCode("BSE");
			aDto.setScrip(aScr);
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("Action", "UpdateIntraday");
			messageMap.put("TargetObject", aDto);
			sendMessage(messageMap);
		}
		
	}
	
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public void processEODData(Map dataMap) throws BusinessException {
		log.debug("Begin processEODData...");	
		dataMap.put("Action", "UpdateEODData");
		sendMessage(dataMap);
		
	}
	
	private void sendMessage(Object aObj) throws BusinessException {
		javax.jms.Message message = null;
		javax.jms.QueueConnection jmsConnection = null;
		javax.jms.QueueSession jmsSession = null;
		QueueSender jmsSender = null;
		try {
			jmsConnection = jmsConnectionFactory.createQueueConnection();
			jmsSession = jmsConnection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			jmsSender = jmsSession.createSender(dataRepoDestination);
			
			message = jmsSession.createObjectMessage();
			((ObjectMessage) message).setObject((Serializable)aObj);
			jmsSender.send(message);			
			log.info("Begin processData...JMS message sent");
		} catch (Exception e) {
			throw new BusinessException("errors.process.data", e);
		} finally {
			try {
				if (jmsConnection!=null) jmsConnection.close();                
			}catch (Exception e) {
				throw new BusinessException("errors.process.data", e);
			}
		}
	}
}
  
