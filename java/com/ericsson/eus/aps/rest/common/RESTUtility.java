package com.ericsson.eus.aps.rest.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.ericsson.eus.aps.rest.common.RESTConstants.CustomErrorCodes;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomErrorMessages;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;





public class RESTUtility
{
	private RESTUtility() {
			
	}
    /**
     * Utility method to add values to header element
     * 
     * @param headerName
     * @param headerValue
     * @return
     */
    public static HttpHeaders addHeader(String headerName, String headerValue)
    {
        HttpHeaders hdr = new HttpHeaders();
        hdr.add(headerName, headerValue);

        return hdr;
    }
    
    

    /**
     * Utility method to copy specified list of header elements to response
     * 
     * @param request
     * @param response
     * @param headerNames
     */
    public static void copyHdrElementsToResponse(HttpServletRequest request, HttpServletResponse response, String[] headerNames)
    {
        for (String headerName : headerNames)
        {
        	if (request.getHeader(headerName) != null) {
				String getHeaderName = request.getHeader(headerName);
				response.addHeader(headerName, getHeaderName);
        	}
        }
    }


    /**
     * Utility method to find if given elements are present in request header
     * 
     * @param request
     * @param headerNames
     * @return
     */
    public static String findMissingHeader(HttpServletRequest request, String[] headerNames)
    {

        for (String headerName : headerNames)
        {
            if (StringUtils.isEmpty(request.getHeader(headerName)))
                return headerName;
        }

        return null;
    }

    /**
     * Utility method to validate if specified mandatory header elements are present in request
     * 
     * @param request
     * @param response 
     * @param fieldNames
     */
    public static void validateMandatoryHeader(HttpServletRequest request, HttpServletResponse response, String[] fieldNames)
    {
        String missingHdrName = findMissingHeader(request, fieldNames);

        if (null != missingHdrName)
        {
        	response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new RESTValidationException(CustomErrorCodes.GLOBAL_MISSING_HEADER_CD,
                    String.format(CustomErrorMessages.GLOBAL_MISSING_HEADER_MSG, missingHdrName), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 
     * User: ekxrxax , Date: Sep 29, 2020 5:01:43 PM 2020
     *
     * Purpose: set response
     *
     * US/D/F Number: US84027
     * 
     * Return Type: ResponseEntity<BaseResponse>
     *
     * @param responseBody
     * @param header
     * @param status
     */
    public static ResponseEntity<BaseResponse> createResponse(BaseResponse responseBody, HttpHeaders header, HttpStatus status)
    {
        if(null == header) {
            return new ResponseEntity<>(responseBody, status);
        }
            
        return new ResponseEntity<>(responseBody, header, status);
    }
	
	
	
    
  }
