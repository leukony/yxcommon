package com.yunxi.common.spring.autoconfigure.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yunxi.common.rpc.http.HttpClientTemplate;

/**
 * HttpClientTemplate自动装配
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientTemplateAutoConfiguration.java, v 0.1 2017年4月18日 下午3:34:29 leukony Exp $
 */
@Configuration
@ConditionalOnClass({HttpClientTemplate.class})
@EnableConfigurationProperties(HttpClientTemplateProperties.class)
public class HttpClientTemplateAutoConfiguration {
    
    @Autowired
    private HttpClientTemplateProperties properties;
    
    @Bean("httpClientTemplate")
    @ConditionalOnMissingBean
    public HttpClientTemplate httpClientTemplate() {
        HttpClientTemplate template = new HttpClientTemplate();
        template.setAppName(properties.getAppName());
        template.setMaxConnPerHost(properties.getMaxConnPerHost());
        template.setMaxTotalConn(properties.getMaxTotalConn());
        template.setSoTimeout(properties.getSoTimeout());
        template.setConnectionTimeout(properties.getConnectionTimeout());
        template.setConnectionManagerTimeout(properties.getConnectionManagerTimeout());
        template.setContentCharset(properties.getContentCharset());
        template.setProxyHost(properties.getProxyHost());
        template.setProxyPort(properties.getProxyPort());
        template.setProxyUserName(properties.getProxyUserName());
        template.setProxyUserPassword(properties.getProxyUserPassword());
        template.initialize();
        return template;
    }
}