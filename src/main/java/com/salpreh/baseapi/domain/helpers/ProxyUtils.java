package com.salpreh.baseapi.domain.helpers;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class ProxyUtils {

  public static <T, H> T createPassThroughProxy(Class<T> proxyClazz, H handler) {
    return (T) Proxy.newProxyInstance(
      ProxyUtils.class.getClassLoader(),
      new Class[] {proxyClazz},
      new PassThroughInvocationHandler(handler)
    );
  }

  public static class PassThroughInvocationHandler<H> implements InvocationHandler {

    private final H handler;
    private final Map<String, Method> methods = new HashMap<>();

    public PassThroughInvocationHandler(H handler) {
      this.handler = handler;
      registerMethods(handler);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
      Method hMethod = methods.get(method.getName());
      if (hMethod == null) throw new RuntimeException("Invoked proxy method not found in handler");

      return hMethod.invoke(handler, objects);
    }

    private void registerMethods(H handler) {
      Arrays.stream(Object.class.getDeclaredMethods())
        .map(m -> {
          try {
            return handler.getClass().getMethod(m.getName());
          } catch (NoSuchMethodException e) {
            // Continue
            return null;
          }
        })
        .filter(Objects::nonNull)
        .forEach(m -> methods.put(m.getName(), m));

      Arrays.stream(handler.getClass().getDeclaredMethods()).forEach(m -> methods.put(m.getName(), m));
    }
  }
}
