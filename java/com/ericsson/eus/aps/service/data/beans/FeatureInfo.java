package com.ericsson.eus.aps.service.data.beans;

// F1214 - Used for Feature Information Processing
public class FeatureInfo {

	private String featureCode;
	private String featureDesc;
	private double featureAmount;

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public double getFeatureAmount() {
		return featureAmount;
	}

	public void setFeatureAmount(double featureAmount) {
		this.featureAmount = featureAmount;
	}

	/**
	 * @return the featureDesc
	 */
	public String getFeatureDesc() {
		return featureDesc;
	}

	/**
	 * @param featureDesc
	 *            the featureDesc to set
	 */
	public void setFeatureDesc(String featureDesc) {
		this.featureDesc = featureDesc;
	}

	@Override
	public String toString() {
		return "FeatureInfo [featureCode=" + featureCode + ", featureAmount=" + featureAmount + ", featureDesc="
				+ featureDesc + "]";
	}

}