package com.salpreh.baseapi.domain.models.contexts;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DeviceContextHolder {
  private static final ThreadLocal<Long> DEVICE_CONTEXT = new ThreadLocal<>();

    public static void setDeviceId(Long deviceId) {
        DEVICE_CONTEXT.set(deviceId);
    }

    public static Long getDeviceId() {
        return DEVICE_CONTEXT.get();
    }

    public boolean hasDeviceId() {
        return DEVICE_CONTEXT.get() != null;
    }

    public static void clear() {
        DEVICE_CONTEXT.remove();
    }
}
