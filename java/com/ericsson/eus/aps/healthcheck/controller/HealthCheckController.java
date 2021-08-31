package com.ericsson.eus.aps.healthcheck.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author eihsnad
 * This is a Controller class for health check of server either up or down
 * */
@Controller
public class HealthCheckController {
	
	

	private final Logger logger = LogManager.getLogger(HealthCheckController.class);
	
	
	/**
	 * readiness health check http://localhost:8085/AutoPayService/healthCheck
	 * 
	 * @return
	 */
     @GetMapping(path = "/healthCheck") 
     @ResponseBody
     public String healthCheck() {
		logger.info("APS is UP and RUNNING!!");
		return "APS is UP and RUNNING!!";
	}
}
