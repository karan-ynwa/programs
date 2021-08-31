package com.ericsson.eus.aps.service.data.beans;

public class AutoPayEnrollResponseBean extends BaseResponse{
	private static final long serialVersionUID = 2547783713056479291L;
	
	private Long msisdn;
	private AutoPayDetailBean detailBean;

	public Long getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(Long msisdn) {
		this.msisdn = msisdn;
	}
	
	public AutoPayDetailBean getDetailBean() {
		return detailBean;
	}

	public void setDetailBean(AutoPayDetailBean detailBean) {
		this.detailBean = detailBean;
	}

	@Override
	public String toString() {
		return "AutoPayEnrollResponseBean [msisdn=" + msisdn + ", detailBean=" + detailBean + ", responseCode="
				+ responseCode + ", responseText=" + responseText + "]";
	}

	
}
