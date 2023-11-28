package org.stock.portal.domain;

import java.util.Date;


public interface EodData {

	public Long getId() ;

	public void setId(Long id) ;

	public Float getClosePrice();
	
	public void setClosePrice(Float closePrice) ;

	public Date getDataDate() ;

	public void setDataDate(Date dataDate) ;
	
	public Float getHighPrice() ;

	public void setHighPrice(Float highPrice) ;

	public Float getLowPrice();

	public void setLowPrice(Float lowPrice) ;

	public Long getOpenInterest();

	public void setOpenInterest(Long openInterest);
	
	public Float getOpenPrice() ;
	
	public void setOpenPrice(Float openPrice);
	
	public Float getPreviousClose();
	
	public void setPreviousClose(Float previousClose);

	public Long getVolume();
	
	public void setVolume(Long volume);

	public Scrip getScrip() ;

	public void setScrip(Scrip scrip) ;
	
	public Float getStochasticValue() ;

	public void setStochasticValue(Float stochasticValue);
	
	public Boolean getPriceMoveTrend();

	public void setPriceMoveTrend(Boolean priceMoveTrend);
}