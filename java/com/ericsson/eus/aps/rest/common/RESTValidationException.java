package com.ericsson.eus.aps.rest.common;


import org.springframework.http.HttpStatus;

/**
 * 
 * FILE_NAME: RESTValidationException.java
 * 
 * MODULE DESCRIPTION: Custom Exception for Validation Exception, US/F/D:
 * US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 2:55:18 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class RESTValidationException extends RuntimeException
{

    private static final long serialVersionUID = 1L;
    
    private String errorCode;
    
    private HttpStatus errorStatus;
    
    public RESTValidationException(String errorCode, String errorMessage, HttpStatus errorStatus)
    {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorStatus()
    {
        return errorStatus;
    }

    public void setErrorStatus(HttpStatus errorStatus)
    {
        this.errorStatus = errorStatus;
    }

}
