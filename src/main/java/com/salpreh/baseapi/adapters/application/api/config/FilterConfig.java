package com.salpreh.baseapi.adapters.application.api.config;

import com.salpreh.baseapi.adapters.application.api.config.filters.DeviceFilter;
import com.salpreh.baseapi.adapters.application.api.config.filters.ProviderFilter;
import com.salpreh.baseapi.domain.models.contexts.ProviderContext;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<DeviceFilter> deviceFilter() {
    FilterRegistrationBean<DeviceFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new DeviceFilter());
    registrationBean.addUrlPatterns("*");

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<ProviderFilter> providerFilter(ProviderContext context) {
    FilterRegistrationBean<ProviderFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ProviderFilter(context));
    registrationBean.addUrlPatterns("*");

    return registrationBean;
  }
}
