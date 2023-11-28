package org.stock.portal.web.util;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.stock.portal.domain.dto.ScripEOD;

import ChartDirector.*;

public class QuickChartImageGenerator {
	
	private String symbol;
	private String compareSymbol;
	
	
	private Date[] timeStamps = null;	
	double[] openData = null;
	double[] highData = null;
	double[] lowData = null;	
	double[] closeData = null;
	double[] volData = null;
	
	double[] compareData = null;

	int resolution = 86400;
	
	public QuickChartImageGenerator(String symbol,List<ScripEOD> dataList, String compareSymbol, List<ScripEOD> compareDataList) {
		super();
		this.symbol = symbol;
		this.compareSymbol = compareSymbol;
		if (dataList!=null && dataList.size()>0) {
			timeStamps = new Date[dataList.size()];
			openData = new double[dataList.size()];
			highData = new double[dataList.size()];
			lowData = new double[dataList.size()];
			closeData = new double[dataList.size()];
			volData = new double[dataList.size()];
			
			for(int i=0;i<dataList.size();i++){
	            ScripEOD aData = (ScripEOD)dataList.get(i);
	            timeStamps[i] = aData.getDataDate();
	            openData[i] = aData.getOpenPrice();
	            highData[i] = aData.getHighPrice(); 
	            lowData[i] = aData.getLowPrice(); 
	            closeData[i] = aData.getClosePrice(); 	            
	            volData[i] = aData.getVolume(); 
			}
			if (compareDataList!=null && compareDataList.size()>0) {
				compareData = new double[dataList.size()];
				for(int i=0;i<dataList.size();i++){
					int foundAt = -1;
					for(int j=0;j<compareDataList.size();j++) {
						ScripEOD aData = (ScripEOD)compareDataList.get(j);
						if (aData.getDataDate().equals(timeStamps[i])) {
							foundAt = j;
							break;
						}
					}
					if (foundAt>=0) {
						ScripEOD aData = (ScripEOD)compareDataList.get(foundAt);
						compareData[i] = aData.getClosePrice();
					} else {
						if (i==0) {
							compareData[i] = 0;
						} else {
							compareData[i] = compareData[i-1];
						}
					}
				}
			}
		}
	}
	
//	
//	timeStamps = db.getTimeStamps();
//    highData = db.getHighData();
//    lowData = db.getLowData();
//    openData = db.getOpenData();
//    closeData = db.getCloseData();
//    volData = db.getVolData();
    	
	public BaseChart drawChart(HttpServletRequest request) {
	    
	    // The moving average periods selected by the user.
	    int avgPeriod1 = 0;
	    try { avgPeriod1 = Integer.parseInt(request.getParameter("movAvg1")); }
	    catch (Exception e) { avgPeriod1 = 0; }
	    int avgPeriod2 = 0;
	    try { avgPeriod2 = Integer.parseInt(request.getParameter("movAvg2")); }
	    catch (Exception e) { avgPeriod2 = 0; }
	    int avgPeriod3 = 0;
	    try { avgPeriod3 = Integer.parseInt(request.getParameter("movAvg3")); }
	    catch (Exception e) { avgPeriod3 = 0; }

	    if (avgPeriod1 < 0) {
	        avgPeriod1 = 0;
	    } else if (avgPeriod1 > 300) {
	        avgPeriod1 = 300;
	    }

	    if (avgPeriod2 < 0) {
	        avgPeriod2 = 0;
	    } else if (avgPeriod2 > 300) {
	        avgPeriod2 = 300;
	    }
	    
	    if (avgPeriod3 < 0) {
	        avgPeriod3 = 0;
	    } else if (avgPeriod3 > 300) {
	        avgPeriod3 = 300;
	    }
	    
	    int extraPoints = 20;
	    if (avgPeriod1 > extraPoints) {
	        extraPoints = avgPeriod1;
	    }
	    if (avgPeriod2 > extraPoints) {
	        extraPoints = avgPeriod2;
	    }	
	    if (avgPeriod3 > extraPoints) {
	        extraPoints = avgPeriod3;
	    }
	    
	    if (extraPoints >= timeStamps.length) { // Check if there is any valid data	        
	        return errMsg("No data available for the specified time period");
	    }

	    // In some finance chart presentation style, even if the data for the latest day
	    // is not fully available, the axis for the entire day will still be drawn, where
	    // no data will appear near the end of the axis.
	    if (resolution < 86400) {
	        // Add extra points to the axis until it reaches the end of the day. The end
	        // of day is assumed to be 16:00 (it depends on the stock exchange).
	        GregorianCalendar lastTime = new GregorianCalendar();
	        lastTime.setTime(timeStamps[timeStamps.length - 1]);
	        int extraTrailingPoints = (int)((16 * 3600 - lastTime.get(Calendar.HOUR_OF_DAY) * 3600
	            - lastTime.get(Calendar.MINUTE) * 60 - lastTime.get(Calendar.SECOND)) / resolution);
	        if (extraTrailingPoints > 0)
	        {
	            Date[] extendedTimeStamps = new Date[timeStamps.length + extraTrailingPoints];
	            System.arraycopy(timeStamps, 0, extendedTimeStamps, 0, timeStamps.length);
	            for (int i = 0; i < extraTrailingPoints; ++i)
	            {
	                lastTime.add(Calendar.SECOND, resolution);
	                extendedTimeStamps[i + timeStamps.length] = (Date)lastTime.getTime().clone();
	            }
	            timeStamps = extendedTimeStamps;
	        }
	    }

	    int width = 780;
	    int mainHeight = 255;
	    int indicatorHeight = 80;

	    String size = request.getParameter("ChartSize");
	    if ("S".equals(size)) {
	        // Small chart size
	        width = 450;
	        mainHeight = 160;
	        indicatorHeight = 60;
	    } else if ("M".equals(size)) {
	        // Medium chart size
	        width = 620;
	        mainHeight = 215;
	        indicatorHeight = 70;
	    } else if ("H".equals(size)) {
	        // Huge chart size
	        width = 1000;
	        mainHeight = 320;
	        indicatorHeight = 90;
	    }

	    // Create the chart object using the selected size
	    FinanceChart m = new FinanceChart(width);

	    // Set the data into the chart object
	    m.setData(timeStamps, highData, lowData, openData, closeData, volData,
	        extraPoints);

	    //
	    // We configure the title of the chart. In this demo chart design, we put the
	    // company name as the top line of the title with left alignment.
	    //
	    m.addPlotAreaTitle(Chart.TopLeft, this.symbol);

	    // We displays the current date as well as the data resolution on the next line.
	    String resolutionText = "";
	    if (resolution == 30 * 86400) {
	        resolutionText = "Monthly";
	    } else if (resolution == 7 * 86400) {
	        resolutionText = "Weekly";
	    } else if (resolution == 86400) {
	        resolutionText = "Daily";
	    } else if (resolution == 900) {
	        resolutionText = "15-min";
	    }

	    m.addPlotAreaTitle(Chart.BottomLeft, "<*font=Arial,size=8*>" + m.formatValue(
	    		timeStamps[timeStamps.length - 1], "mmm dd, yyyy") + " - " + resolutionText + " chart");

	    //
	    // Add the first techical indicator according. In this demo, we draw the first
	    // indicator on top of the main chart.
	    //
	    addIndicator(m, request.getParameter("Indicator1"), indicatorHeight);
	    
	    //
	    // Add the main chart
	    //
	    XYChart xyChart = m.addMainChart(mainHeight);

//	    int x1 = xyChart.getXCoor(10);
//	    int y1 = xyChart.getYCoor(120);
//	    
//	    int x2 = xyChart.getXCoor(timeStamps.length-21);
//	    int y2 = xyChart.getYCoor(240);
//	    
//	    m.addLine(x1, y1, x2, y2);
	    //
	    // Set log or linear scale according to user preference
	    //
	    if ("1".equals(request.getParameter("LogScale"))) {
	        m.setLogScale(true);
	    }

	    //
	    // Set axis labels to show data values or percentage change to user preference
	    //
	    if ("1".equals(request.getParameter("PercentageScale"))) {
	        m.setPercentageAxis();
	    }

	    //
	    // Draw any price line the user has selected
	    //
	    String mainType = request.getParameter("ChartType");
	    if ("Close".equals(mainType)) {
	        m.addCloseLine(0x000040);
	    } else if ("TP".equals(mainType)) {
	        m.addTypicalPrice(0x000040);
	    } else if ("WC".equals(mainType)) {
	        m.addWeightedClose(0x000040);
	    } else if ("Median".equals(mainType)) {
	        m.addMedianPrice(0x000040);
	    }

	    //
	    // Add comparison line if there is data for comparison
	    //
	    if (compareData != null) {
	        if (compareData.length > extraPoints) {
	            m.addComparison(compareData, 0x0000ff, compareSymbol);
	        }
	    }

	    //
	    // Add moving average lines.
	    //
	    addMovingAvg(m, request.getParameter("avgType1"), avgPeriod1, 0x0000FF);
	    addMovingAvg(m, request.getParameter("avgType2"), avgPeriod2, 0x00FF00);
	    addMovingAvg(m, request.getParameter("avgType3"), avgPeriod3, 0xFF0000);

	    //
	    // Draw candlesticks or OHLC symbols if the user has selected them.
	    //
	    if ("CandleStick".equals(mainType)) {
	        m.addCandleStick(0x33ff33, 0xff3333);
	    } else if ("OHLC".equals(mainType)) {
	        m.addHLOC(0x008800, 0xcc0000);
	    }

	    //
	    // Add parabolic SAR if necessary
	    //
	    if ("1".equals(request.getParameter("ParabolicSAR"))) {
	        m.addParabolicSAR(0.02, 0.02, 0.2, Chart.DiamondShape, 5, 0x008800, 0x000000)
	            ;
	    }

	    //
	    // Add price band/channel/envelop to the chart according to user selection
	    //
	    String bandType = request.getParameter("Band");
	    if ("BB".equals(bandType)) {
	        m.addBollingerBand(20, 2, 0x9999ff, 0xc06666ff);
	    } else if ("DC".equals(bandType)) {
	        m.addDonchianChannel(20, 0x9999ff, 0xc06666ff);
	    } else if ("Envelop".equals(bandType)) {
	        m.addEnvelop(20, 0.1, 0x9999ff, 0xc06666ff);
	    }

	    //
	    // Add volume bars to the main chart if necessary
	    //
	    if ("1".equals(request.getParameter("Volume"))) {
	        m.addVolBars(indicatorHeight, 0x99ff99, 0xff9999, 0xc0c0c0);
	    }

	    //
	    // Add additional indicators as according to user selection.
	    //
	    addIndicator(m, request.getParameter("Indicator2"), indicatorHeight);
	    addIndicator(m, request.getParameter("Indicator3"), indicatorHeight);
	    addIndicator(m, request.getParameter("Indicator4"), indicatorHeight);

	    return m;
	}

	/// <summary>
	/// Add a moving average line to the FinanceChart object.
	/// </summary>
	/// <param name="m">The FinanceChart object to add the line to.</param>
	/// <param name="avgType">The moving average type (SMA/EMA/TMA/WMA).</param>
	/// <param name="avgPeriod">The moving average period.</param>
	/// <param name="color">The color of the line.</param>
	/// <returns>The LineLayer object representing line layer created.</returns>
	protected LineLayer addMovingAvg(FinanceChart m, String avgType, int avgPeriod,
	    int color)
	{
	    if (avgPeriod > 1) {
	        if ("SMA".equals(avgType)) {
	            return m.addSimpleMovingAvg(avgPeriod, color);
	        } else if ("EMA".equals(avgType)) {
	            return m.addExpMovingAvg(avgPeriod, color);
	        } else if ("TMA".equals(avgType)) {
	            return m.addTriMovingAvg(avgPeriod, color);
	        } else if ("WMA".equals(avgType)) {
	            return m.addWeightedMovingAvg(avgPeriod, color);
	        }
	    }
	    return null;
	}

	/// <summary>
	/// Add an indicator chart to the FinanceChart object. In this demo example, the
	/// indicator parameters (such as the period used to compute RSI, colors of the lines,
	/// etc.) are hard coded to commonly used values. You are welcome to design a more
	/// complex user interface to allow users to set the parameters.
	/// </summary>
	/// <param name="m">The FinanceChart object to add the line to.</param>
	/// <param name="indicator">The selected indicator.</param>
	/// <param name="height">Height of the chart in pixels</param>
	/// <returns>The XYChart object representing indicator chart.</returns>
	protected XYChart addIndicator(FinanceChart m, String indicator, int height)
	{
	    if ("RSI".equals(indicator)) {
	        return m.addRSI(height, 14, 0x800080, 20, 0xff6666, 0x6666ff);
	    } else if ("StochRSI".equals(indicator)) {
	        return m.addStochRSI(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
	    } else if ("MACD".equals(indicator)) {
	        return m.addMACD(height, 26, 12, 9, 0x0000ff, 0xff00ff, 0x008000);
	    } else if ("FStoch".equals(indicator)) {
	        return m.addFastStochastic(height, 14, 5, 0x006060, 0x606000);
	    } else if ("SStoch".equals(indicator)) {
	        return m.addSlowStochastic(height, 14, 5, 0x006060, 0x606000);
	    } else if ("ATR".equals(indicator)) {
	        return m.addATR(height, 14, 0x808080, 0x0000ff);
	    } else if ("ADX".equals(indicator)) {
	        return m.addADX(height, 14, 0x008000, 0x800000, 0x000080);
	    } else if ("DCW".equals(indicator)) {
	        return m.addDonchianWidth(height, 20, 0x0000ff);
	    } else if ("BBW".equals(indicator)) {
	        return m.addBollingerWidth(height, 20, 2, 0x0000ff);
	    } else if ("DPO".equals(indicator)) {
	        return m.addDPO(height, 20, 0x0000ff);
	    } else if ("PVT".equals(indicator)) {
	        return m.addPVT(height, 0x0000ff);
	    } else if ("Momentum".equals(indicator)) {
	        return m.addMomentum(height, 12, 0x0000ff);
	    } else if ("Performance".equals(indicator)) {
	        return m.addPerformance(height, 0x0000ff);
	    } else if ("ROC".equals(indicator)) {
	        return m.addROC(height, 12, 0x0000ff);
	    } else if ("OBV".equals(indicator)) {
	        return m.addOBV(height, 0x0000ff);
	    } else if ("AccDist".equals(indicator)) {
	        return m.addAccDist(height, 0x0000ff);
	    } else if ("CLV".equals(indicator)) {
	        return m.addCLV(height, 0x0000ff);
	    } else if ("WilliamR".equals(indicator)) {
	        return m.addWilliamR(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
	    } else if ("Aroon".equals(indicator)) {
	        return m.addAroon(height, 14, 0x339933, 0x333399);
	    } else if ("AroonOsc".equals(indicator)) {
	        return m.addAroonOsc(height, 14, 0x0000ff);
	    } else if ("CCI".equals(indicator)) {
	        return m.addCCI(height, 20, 0x800080, 100, 0xff6666, 0x6666ff);
	    } else if ("EMV".equals(indicator)) {
	        return m.addEaseOfMovement(height, 9, 0x006060, 0x606000);
	    } else if ("MDX".equals(indicator)) {
	        return m.addMassIndex(height, 0x800080, 0xff6666, 0x6666ff);
	    } else if ("CVolatility".equals(indicator)) {
	        return m.addChaikinVolatility(height, 10, 10, 0x0000ff);
	    } else if ("COscillator".equals(indicator)) {
	        return m.addChaikinOscillator(height, 0x0000ff);
	    } else if ("CMF".equals(indicator)) {
	        return m.addChaikinMoneyFlow(height, 21, 0x008000);
	    } else if ("NVI".equals(indicator)) {
	        return m.addNVI(height, 255, 0x0000ff, 0x883333);
	    } else if ("PVI".equals(indicator)) {
	        return m.addPVI(height, 255, 0x0000ff, 0x883333);
	    } else if ("MFI".equals(indicator)) {
	        return m.addMFI(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
	    } else if ("PVO".equals(indicator)) {
	        return m.addPVO(height, 26, 12, 9, 0x0000ff, 0xff00ff, 0x008000);
	    } else if ("PPO".equals(indicator)) {
	        return m.addPPO(height, 26, 12, 9, 0x0000ff, 0xff00ff, 0x008000);
	    } else if ("UO".equals(indicator)) {
	        return m.addUltimateOscillator(height, 7, 14, 28, 0x800080, 20, 0xff6666,
	            0x6666ff);
	    } else if ("Vol".equals(indicator)) {
	        return m.addVolIndicator(height, 0x99ff99, 0xff9999, 0xc0c0c0);
	    } else if ("TRIX".equals(indicator)) {
	        return m.addTRIX(height, 12, 0x0000ff);
	    }
	    return null;
	}

	/// <summary>
	/// Creates a dummy chart to show an error message.
	/// </summary>
	/// <param name="msg">The error message.
	/// <returns>The BaseChart object containing the error message.</returns>
	protected BaseChart errMsg(String msg)
	{
	    MultiChart m = new MultiChart(400, 200);
	    m.addTitle2(Chart.Center, msg, "Arial", 10).setMaxWidth(m.getWidth());
	    return m;
	}
	
}
	
