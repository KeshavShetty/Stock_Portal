package org.stock.portal.domain.dto;

import java.io.Serializable;

public class NOWOrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String exchangeCode;
	String scripCode;
	String orderNumber;
	String transactiontype;
	String price;
	String avgPrice;
	String totalQuantity;
	String pendingQuantity;
	String disclosedQuantity;
	String filledQuantity;
	String exchangeOrderNo;
	String status;
	String rejectionReason;
	String orderType;
	String orderTime;
	
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public String getScripCode() {
		return scripCode;
	}
	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(String avgPrice) {
		this.avgPrice = avgPrice;
	}
	public String getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getPendingQuantity() {
		return pendingQuantity;
	}
	public void setPendingQuantity(String pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}
	public String getDisclosedQuantity() {
		return disclosedQuantity;
	}
	public void setDisclosedQuantity(String disclosedQuantity) {
		this.disclosedQuantity = disclosedQuantity;
	}
	public String getFilledQuantity() {
		return filledQuantity;
	}
	public void setFilledQuantity(String filledQuantity) {
		this.filledQuantity = filledQuantity;
	}
	public String getExchangeOrderNo() {
		return exchangeOrderNo;
	}
	public void setExchangeOrderNo(String exchangeOrderNo) {
		this.exchangeOrderNo = exchangeOrderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	@Override
	public String toString() {
		return "NOWOrderDTO [exchangeCode=" + exchangeCode + ", scripCode="
				+ scripCode + ", orderNumber=" + orderNumber
				+ ", transactiontype=" + transactiontype + ", price=" + price
				+ ", avgPrice=" + avgPrice + ", totalQuantity=" + totalQuantity
				+ ", pendingQuantity=" + pendingQuantity
				+ ", disclosedQuantity=" + disclosedQuantity
				+ ", filledQuantity=" + filledQuantity + ", exchangeOrderNo="
				+ exchangeOrderNo + ", status=" + status + ", rejectionReason="
				+ rejectionReason + ", orderType=" + orderType + ", orderTime="
				+ orderTime + "]";
	}	
}