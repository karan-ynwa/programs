package com.ericsson.eus.aps.service.data.beans;

public class AutoPayUnenrollResponseBean extends BaseResponse{
	private static final long serialVersionUID = 2547783713056479291L;
	
	private AutoPayDetailBean detailBean;

	public AutoPayDetailBean getDetailBean() {
		return detailBean;
	}

	public void setDetailBean(AutoPayDetailBean detailBean) {
		this.detailBean = detailBean;
	}

	@Override
	public String toString() {
		return "AutoPayUnenrollResponseBean [detailBean=" + detailBean + "]";
	}

	
}
