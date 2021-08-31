package com.ericsson.eus.aps.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ericsson.eus.aps.rest.common.RESTHeaderValidationInterceptor;
import com.ericsson.eus.plcfg.plugin.util.PLCFGUtil;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {
	

	    @Override
	    public void addInterceptors (InterceptorRegistry registry) {
	        registry.addInterceptor(new RESTHeaderValidationInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/healthCheck");

	    }
	    @Bean
		  public PLCFGUtil featurToggle() {
		    return new PLCFGUtil();
		  }

}
