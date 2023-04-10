package com.salpreh.baseapi.adapters.application.api.config.filters;

import com.salpreh.baseapi.domain.models.contexts.DeviceContextHolder;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DeviceFilter implements Filter {

  private static final String DEVICE_HEADER = "X-Device";

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain filterChain
  ) throws IOException, ServletException {

    // Get device id from header
    String deviceId = ((HttpServletRequest)servletRequest).getHeader(DEVICE_HEADER);
    if (deviceId == null) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    try {
      DeviceContextHolder.setDeviceId(Long.parseLong(deviceId));
      filterChain.doFilter(servletRequest, servletResponse);

    } catch (NumberFormatException e) {
      log.warn("Unable to parse device id: {}", deviceId);

    } finally {
      DeviceContextHolder.clear();
    }
  }
}
