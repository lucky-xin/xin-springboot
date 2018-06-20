package com.xin.springboot.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 断言工具类
 * @date 2018-05-20 18:11
 * @Copyright (C)2018 , Luchaoxin
 */
public class AssertUtil {

    public static <E> E checkNotNull(E object, String errorMessage) {
        if (null == object) {
            throw new IllegalArgumentException(errorMessage);
        }
        return object;
    }

    public static <K, V> void checkNotEmpty(Map<K, V> map, String errorMessage) {
        if (checkNotNull(map, errorMessage).isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkNotEmpty(String object, String errorMessage) {
        if (checkNotNull(object, errorMessage).isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static <E> void checkNotEmpty(E[] object, String errorMessage) {
        if (checkNotNull(object, errorMessage).length == 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static <E> void checkNotEmpty(Collection<E> collection, String errorMessage) {
        if (checkNotNull(collection, errorMessage).isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static <E> void checkNotEmpty(Enumeration<E> collection, String errorMessage) {
        if (!checkNotNull(collection, errorMessage).hasMoreElements()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkCondition(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
