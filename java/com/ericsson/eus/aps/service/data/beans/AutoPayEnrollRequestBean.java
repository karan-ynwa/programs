package com.ericsson.eus.aps.service.data.beans;


import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AutoPayEnrollRequestBean extends BaseRequest {
	private static final long serialVersionUID = 674495602688489839L;

	@NotEmpty(message = "AutoPayEnrollRequestBean's msisdn has invalid value")
	private String msisdn;
	@NotEmpty(message = "AutoPayEnrollRequestBean's description has invalid value")
	@Size(min =1, max=30, message = "AutoPayEnrollRequestBean's description has invalid value")
	private String description;
	@NotEmpty(message = "AutoPayEnrollRequestBean's pdofToken has invalid value")
	@Size(min=1, max=19, message = "AutoPayEnrollRequestBean's pdofToken has invalid value")
	private String pdofToken;
	private String pdofLast4;
	private Date supervisionExpiryDate;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdofToken() {
		return pdofToken;
	}

	public void setPdofToken(String pdofToken) {
		this.pdofToken = pdofToken;
	}

	public String getPdofLast4() {
		return pdofLast4;
	}

	public void setPdofLast4(String pdofLast4) {
		this.pdofLast4 = pdofLast4;
	}
	
	public Date getSupervisionExpiryDate() {
		return supervisionExpiryDate;
	}

	public void setSupervisionExpiryDate(Date supervisionExpiryDate) {
		this.supervisionExpiryDate = supervisionExpiryDate;
	}

	@Override
	public String toString() {
		return "AutoPayEnrollRequestBean [msisdn=" + msisdn + ", description=" + description + ", pdofToken="
				+ pdofToken + ", pdofLast4=" + pdofLast4 + ", supervisionExpiryDate=" + supervisionExpiryDate + "]";
	}
}