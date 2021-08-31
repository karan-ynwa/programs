package com.ericsson.eus.aps.rest.common;

public class RESTConstants {

	private RESTConstants() {

	}

	public static final String START_TIME = "startTime";

	public static final String RESPONSE_TEXT = "responseText";

	public static final Integer SUCCESS_RESPONSE_CODE = 1000;
	public static final String SUCCESS_RESPONSE_TEXT = "EVALUATED_SUCCESSFULLY ";
	public static final String AUTOPAY_RESPONSE_TEXT = "SUCCESS";

	public static class CustomErrorCodes {
		private CustomErrorCodes() {

		}

		public static final String GLOBAL_MISSING_HEADER_CD = "5900";

	}

	public static class CustomErrorMessages {
		private CustomErrorMessages() {

		}

		public static final String GLOBAL_MISSING_HEADER_MSG = "Missing header element %s";

	}

	public static class CustomHeaders {
		private CustomHeaders() {

		}

		// Request Headers
		public static final String AUTHORIZATION = "Authorization";

		public static final String CONTENT_TYPE = "Content-Type"; // mandatory

		public static final String MSISDN = "X-MSISDN"; // mandatory

		public static final String EVENT = "X-Event"; // mandatory

		public static final String ACTOR = "X-Actor"; // mandatory

		public static final String TRANSACTION = "X-Transaction"; // mandatory

		public static final String MSISDN_COUNT = "X-MSISDN-Count"; // Mandatory

		// Response Headers
		public static final String RESPONSE_CODE = "X-Response-Code";

	}

	public static class ApsConstants {
		private ApsConstants() {

		}

		public static final String INVALID_PARAM_VALUE = "%s has invalid value";
		public static final String INVALID_PARAM_LENGTH = "%s length cannot exceed %s";

		public static final String DEFAULT_HOST_IP = "127.0.0.1";

		public static final String DEFAULT_HOST_NAME = "localhost";
		public static final String ALARM_LOGGER_NAME = "ALARM_LOG";

		public static final String EBI_CACHE_CONFIG_UPDATE_INFO_DAO = "ebiCacheConfigDao";
		public static final String EBI_DISCOUNTS_DAO = "ebiDiscountsDao";
		public static final String VU_RATE_PLAN_TIER_DAO = "vuRatePlanTierDao";
		public static final int PLACES = 2;
		public static final String ML_PRIMARY = "PRIMARY";
		public static final String ML_MEMBER = "MEMBER";
		public static final String MULTILINE = "MULTILINE";
		public static final String NON_MULTILINE = "NON_MULTILINE";
		public static final String INDVIDUAL = "INDVIDUAL";
		public static final String NA = "N/A";
		public static final String ACTIVE = "ACTIVE";
		public static final String INSTALLED = "INSTALLED";
		public static final String ACTIVE_FLAG = "Y";
		public static final String MULTILINE_DISCOUNT = "MULTILINE";
		public static final String PERPETUAL_DISCOUNT = "PERPETUAL";
		// F1217 : US92104
		public static final String DISCOUNT_DAO = "discountDao";
		public static final String CEF_CRITERIA_DAO = "cefCriteriaDao";
		public static final String VU_RATE_PLAN_DAO = "vuRatePlanDao";
		public static final String AUTO_PAY_DISCOUNT_DETAILS_DAO = "autoPayDiscountDetailsDao";
		
		// Toggle
		public static final String F81681 = "F81681";

		public static final String EMPTY_STRING = "";

		public static final String FAULT_CODE_CLIENT = "Client";
		public static final String ON = "ON";
		public static final String INACTIVE_STATUS = "INACTIVE";
		public static final String SOFT_INACTIVE_STATUS = "SOFT_INACTIVE";
		public static final String QUERY_EXECUTING_MSG = " executing ";
		public static final String RESPONSE_MSG = "Response: {}";
		public static final String REQUEST_MSG = " Request: {} ";
		public static final String DOLLARS = "DOLLARS";
		public static final String CENTS = "CENTS";
		public static final String MONEY = "MONEY";

		public static final String FIXED_AMOUNT_TOPUP_8_DEFAULT = "25";
		
		public static final String F107041 = "F107041";
		
		public static final String ENHANCED = "ENHANCED";
		public static final String STANDARD = "STANDARD";

		public enum Mode {
			A("Available and existing list of features along with the conflict information"),
			E("List of features already enrolled in auto renew"), C("List of installed features");

			private String description;

			private Mode(String desc) {
				this.description = desc;

			}

			public String getDescription() {
				return this.description;
			}

			/**
			 *
			 * @return
			 */
			public String getMode() {
				return this.toString();
			}
		}

		public enum AutoPayEventTracker {
			PDOF_TOKEN, PDOF_LAST4
		}

	}

}
