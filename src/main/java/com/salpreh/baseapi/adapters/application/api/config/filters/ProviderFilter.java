package com.salpreh.baseapi.adapters.application.api.config.filters;


import com.salpreh.baseapi.domain.models.contexts.ProviderContext;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ProviderFilter implements Filter {

  private ProviderContext providerContext;

  private static final String PROVIDER_HEADER = "X-Provider";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    String providerId = ((HttpServletRequest)request).getHeader(PROVIDER_HEADER);

    if (providerId == null) {
      chain.doFilter(request, response);
      return;
    }

    try {
      providerContext.setProviderId(Long.parseLong(providerId));
      chain.doFilter(request, response);

    } catch (NumberFormatException e) {
      log.warn("Unable to parse provider id: {}", providerId);

    } finally {
      providerContext.setProviderId(null);
    }
  }
}
