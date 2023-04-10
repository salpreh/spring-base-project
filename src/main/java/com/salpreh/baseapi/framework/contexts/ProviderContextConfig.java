package com.salpreh.baseapi.framework.contexts;

import com.salpreh.baseapi.adapters.application.api.config.filters.ProviderFilter;
import com.salpreh.baseapi.domain.models.contexts.ProviderContext;
import javax.servlet.Filter;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class ProviderContextConfig {

//  @Bean(destroyMethod = "destroy")
//  public ThreadLocalTargetSource threadLocalProviderContext() {
//    ThreadLocalTargetSource threadLocalTargetSource = new ThreadLocalTargetSource();
//    threadLocalTargetSource.setTargetBeanName("providerContext");
//
//    return threadLocalTargetSource;
//  }

  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ProviderContext providerContext() {
    return new ProviderContext();
  }
}
