package com.ericsson.eus.aps.rest.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ericsson.eus.aps.rest.common.RESTConstants.CustomErrorCodes;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomErrorMessages;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomHeaders;
import com.ericsson.eus.aps.rest.controller.AutoPayInfoServiceController;
import com.ericsson.eus.aps.rest.controller.AutopayServiceController;
import com.ericsson.eus.vestastat.StatLogger;

/**
 * 
 * FILE_NAME: RESTHeaderValidationInterceptor.java
 * 
 * MODULE DESCRIPTION: Interceptor for validating REST headers, US/F/D: US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 2:55:18 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class RESTHeaderValidationInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LogManager.getLogger(this.getClass());
	String pattern = "MM-dd-yyyy HH:mm:ss:SS";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute(RESTConstants.START_TIME, startTime);
		logRequest(request);


		// copy request headers to response
		RESTUtility.copyHdrElementsToResponse(request, response,
				new String[] { CustomHeaders.CONTENT_TYPE, CustomHeaders.TRANSACTION, CustomHeaders.ACTOR });

		// validate mandatory header elements
		try {
			RESTUtility.validateMandatoryHeader(request, response, new String[] { CustomHeaders.AUTHORIZATION,
					CustomHeaders.CONTENT_TYPE, CustomHeaders.TRANSACTION, CustomHeaders.ACTOR, CustomHeaders.EVENT });
		} catch (RESTValidationException ex) {
			setStatLoggerError(handler, request);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			throw new RESTValidationException(CustomErrorCodes.GLOBAL_MISSING_HEADER_CD,
					String.format(CustomErrorMessages.GLOBAL_MISSING_HEADER_MSG, ex.getMessage()),
					HttpStatus.BAD_REQUEST);

		}
		return super.preHandle(request, response, handler);

	}

	private void setStatLoggerError(Object handler, HttpServletRequest request) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method method = hm.getMethod();
			// below check has been added to stop logging invalid requests into stats
			if (method.getDeclaringClass().isAssignableFrom(AutopayServiceController.class)) {

				String apiName = (method.getName() == null) ? null : method.getName().toUpperCase();
				StatLogger.printIncomingLog(simpleDateFormat.format(new Date()), apiName,
						request.getHeader(RESTConstants.CustomHeaders.MSISDN),
						request.getHeader(CustomHeaders.TRANSACTION), "", "", "",
						request.getHeader(CustomHeaders.ACTOR), request.getHeader(CustomHeaders.EVENT));
			}

			if (method.getDeclaringClass().isAssignableFrom(AutoPayInfoServiceController.class)) {

				String apiName = (method.getName() == null) ? null : method.getName().toUpperCase();
				StatLogger.printIncomingLog(simpleDateFormat.format(new Date()), apiName,
						request.getHeader(RESTConstants.CustomHeaders.MSISDN),
						request.getHeader(CustomHeaders.TRANSACTION), "", "", "",
						request.getHeader(CustomHeaders.ACTOR), request.getHeader(CustomHeaders.EVENT),
						request.getHeader(CustomHeaders.MSISDN_COUNT));
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method method = hm.getMethod();

			// below check has been added to stop logging invalid requests into stats
			if (method.getDeclaringClass().isAssignableFrom(AutopayServiceController.class)) {
				long startTime = (Long) request.getAttribute(RESTConstants.START_TIME);
				long timeTaken = System.currentTimeMillis() - startTime;
				String responseText = (String) request.getAttribute(RESTConstants.RESPONSE_TEXT);
				String methodName = method.getName().toUpperCase();
				StatLogger.printIncomingLog(simpleDateFormat.format(new Date()), methodName,
						request.getHeader(RESTConstants.CustomHeaders.MSISDN),
						request.getHeader(CustomHeaders.TRANSACTION), response.getHeader(CustomHeaders.RESPONSE_CODE),
						responseText, timeTaken + "MS", request.getHeader(CustomHeaders.ACTOR),
						request.getHeader(CustomHeaders.EVENT));
			}

			if (method.getDeclaringClass().isAssignableFrom(AutoPayInfoServiceController.class)) {
				long startTime = (Long) request.getAttribute(RESTConstants.START_TIME);
				long timeTaken = System.currentTimeMillis() - startTime;
				String responseText = (String) request.getAttribute(RESTConstants.RESPONSE_TEXT);
				String methodName = method.getName().toUpperCase();
				StatLogger.printIncomingLog(simpleDateFormat.format(new Date()), methodName,
						request.getHeader(RESTConstants.CustomHeaders.MSISDN),
						request.getHeader(CustomHeaders.TRANSACTION), response.getHeader(CustomHeaders.RESPONSE_CODE),
						responseText, timeTaken + "MS", request.getHeader(CustomHeaders.ACTOR),
						request.getHeader(CustomHeaders.EVENT), request.getHeader(CustomHeaders.MSISDN_COUNT));
			}
		}

	}

	private void logRequest(HttpServletRequest request) {
		if (logger.isDebugEnabled()) {
			StringBuilder stringBuilder = new StringBuilder();
			Map<String, String> httpHeaderMap = getRequestHeaders(request);
			Set<String> keys = httpHeaderMap.keySet();

			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = i.next();
				String value = httpHeaderMap.get(key);
				stringBuilder.append(" param name: " + key);
				stringBuilder.append(" value: " + value);
			}
			logger.debug("Request Header: {}" + stringBuilder);

		}

	}

	/**
	 * 
	 * @param request
	 * @return http request headers
	 * @throws IOException
	 */
	private Map<String, String> getRequestHeaders(HttpServletRequest request) {
		Map<String, String> httpHeaderMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			httpHeaderMap.put(headerName, request.getHeader(headerName));

		}
		return httpHeaderMap;
	}
}
