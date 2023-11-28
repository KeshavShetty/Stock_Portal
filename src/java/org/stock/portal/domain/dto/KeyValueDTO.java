package org.stock.portal.domain.dto;

import java.io.Serializable;
import java.net.URLEncoder;

public class KeyValueDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String keyLabel;	
	private String keyValue;
	
	public KeyValueDTO() {
		super();
	}
	
	public KeyValueDTO(String keyLabel, String keyValue) {
		super();
		this.keyLabel = keyLabel;
		this.keyValue = keyValue;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	public String getEncodedKeyValue() {
		return URLEncoder.encode(this.keyValue);
	}
}
