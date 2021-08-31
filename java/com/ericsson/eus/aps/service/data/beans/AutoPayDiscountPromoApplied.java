package com.ericsson.eus.aps.service.data.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutoPayDiscountPromoApplied {
	private String apPromoMsisdn;
	private String mlType;
	private String discountCode;
	private String discountType;
	private String discountName;
	private String apPromoAmount;
	private String apPromoAdjType;
	private String apPromoAdjCode;

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}


	@Override
	public String toString() {
		return "AutoPayDiscountPromoApplied [apPromoMsisdn=" + getApPromoMsisdn() + ", discountCode="
				+ getDiscountCode()
				+ ", discountType="
				+ getDiscountType() + ", apPromoAmount=" + getApPromoAmount() + ",mlType=" + getMlType()
				+ ", apPromoAdjType=" + getApPromoAdjType() + ", apPromoAdjCode=" + getApPromoAdjCode()
				+ ", discountName=" + getDiscountName() + " ]";
	}

	/**
	 * @return the apPromoMsisdn
	 */
	public String getApPromoMsisdn() {
		return apPromoMsisdn;
	}

	/**
	 * @param apPromoMsisdn
	 *            the apPromoMsisdn to set
	 */
	public void setApPromoMsisdn(String apPromoMsisdn) {
		this.apPromoMsisdn = apPromoMsisdn;
	}

	/**
	 * @return the mlType
	 */
	public String getMlType() {
		return mlType;
	}

	/**
	 * @param mlType
	 *            the mlType to set
	 */
	public void setMlType(String mlType) {
		this.mlType = mlType;
	}

	/**
	 * @return the apPromoAmount
	 */
	public String getApPromoAmount() {
		return apPromoAmount;
	}

	/**
	 * @param apPromoAmount
	 *            the apPromoAmount to set
	 */
	public void setApPromoAmount(String apPromoAmount) {
		this.apPromoAmount = apPromoAmount;
	}

	/**
	 * @return the apPromoAdjType
	 */
	public String getApPromoAdjType() {
		return apPromoAdjType;
	}

	/**
	 * @param apPromoAdjType
	 *            the apPromoAdjType to set
	 */
	public void setApPromoAdjType(String apPromoAdjType) {
		this.apPromoAdjType = apPromoAdjType;
	}

	/**
	 * @return the apPromoAdjCode
	 */
	public String getApPromoAdjCode() {
		return apPromoAdjCode;
	}

	/**
	 * @param apPromoAdjCode
	 *            the apPromoAdjCode to set
	 */
	public void setApPromoAdjCode(String apPromoAdjCode) {
		this.apPromoAdjCode = apPromoAdjCode;
	}

	/**
	 * @return the discountName
	 */
	public String getDiscountName() {
		return discountName;
	}

	/**
	 * @param discountName
	 *            the discountName to set
	 */
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	
	
}
