package com.yunxi.common.spring.autoconfigure.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yunxi.common.rpc.http.HttpServerFilter;

/**
 * HttpServerFilter自动装配
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServerFilterAutoConfiguration.java, v 0.1 2017年4月18日 下午4:18:42 leukony Exp $
 */
@Configuration
@ConditionalOnClass({HttpServerFilter.class})
@EnableConfigurationProperties(HttpServerFilterProperties.class)
public class HttpServerFilterAutoConfiguration {

    @Autowired
    private HttpServerFilterProperties properties;
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServerFilter());
        registration.addUrlPatterns("/*");
        registration.setName("HttpServerFilter");
        registration.addInitParameter("appName", properties.getAppName());
        registration.addInitParameter("excludeUrl", properties.getExcludeUrl());
        registration.setOrder(1);
        return registration;
    }
}