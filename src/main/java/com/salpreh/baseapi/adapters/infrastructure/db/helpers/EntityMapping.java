package com.salpreh.baseapi.adapters.infrastructure.db.helpers;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class EntityMapping {

  public static void mapNonNullProperties(Object source, Object target) {
    List<String> ignoreNull = new ArrayList<>();
    Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass())).forEach(pd -> {
      Object val = ReflectionUtils.invokeMethod(pd.getReadMethod(), source);
      if (val == null) ignoreNull.add(pd.getName());
    });

    BeanUtils.copyProperties(source, target, ignoreNull.toArray(String[]::new));
  }

  public static void mapNonNullAndNestedProperties(Object source, Object target) {
    mapNonNullAndNestedProperties(source, target, new ArrayList<>());
  }

  private static void mapNonNullAndNestedProperties(Object source, Object target, List<Integer> processedObjects) {
    List<String> ignoreNull = new ArrayList<>();
    Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass())).forEach(pd -> {
      Object val = ReflectionUtils.invokeMethod(pd.getReadMethod(), source);

      if (val == null || processedObjects.contains(val.hashCode())) {
        ignoreNull.add(pd.getName());
        return;
      }
      processedObjects.add(val.hashCode());

      if (isExcludedFromNestedMapping(val.getClass())) return;

      if (BeanUtils.getPropertyDescriptors(val.getClass()).length > 1) {
        Object nestedSrc = ReflectionUtils.invokeMethod(pd.getReadMethod(), source);
        Object nestedTrg = ReflectionUtils.invokeMethod(pd.getReadMethod(), target);

        mapNonNullAndNestedProperties(nestedSrc, nestedTrg, processedObjects);
        ignoreNull.add(pd.getName());
      }
    });

    BeanUtils.copyProperties(source, target, ignoreNull.toArray(String[]::new));
  }

  private static String addToPath(String root, String newElem) {
    return root + "." + newElem;
  }

  private boolean isExcludedFromNestedMapping(Class<?> clazz) {
    return Collection.class.isAssignableFrom(clazz) || clazz.getPackageName().startsWith("java.lang");
  }
}
