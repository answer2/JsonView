package com.answer.library.JsonView.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author AnswerDev
 * @Date 2023/03/04 21:40
 * @Describe Class工具
 */

public class ClassUtil {

    public static final String TAG = "ClassUtil";
    

    private static final Map<String, Class<?>> PRIMITIVE_TYPES = new HashMap<>();
    private static final Map<String, Class<?>> WRAPPER_TYPES = new HashMap<>();

    static {
        PRIMITIVE_TYPES.put("int", int.class);
        PRIMITIVE_TYPES.put("long", long.class);
        PRIMITIVE_TYPES.put("short", short.class);
        PRIMITIVE_TYPES.put("float", float.class);
        PRIMITIVE_TYPES.put("double", double.class);
        PRIMITIVE_TYPES.put("boolean", boolean.class);
        PRIMITIVE_TYPES.put("char", char.class);
        PRIMITIVE_TYPES.put("byte", byte.class);

        WRAPPER_TYPES.put("Integer", Integer.class);
        WRAPPER_TYPES.put("Long", Long.class);
        WRAPPER_TYPES.put("Short", Short.class);
        WRAPPER_TYPES.put("Float", Float.class);
        WRAPPER_TYPES.put("Double", Double.class);
        WRAPPER_TYPES.put("Boolean", Boolean.class);
        WRAPPER_TYPES.put("String", String.class);
        WRAPPER_TYPES.put("Object", Object.class);
        WRAPPER_TYPES.put("Byte", Byte.class);
    }

    public static Class<?> getClassType(String type) {
        if (PRIMITIVE_TYPES.containsKey(type)) {
            return PRIMITIVE_TYPES.get(type);
        }
        if (WRAPPER_TYPES.containsKey(type)) {
            return WRAPPER_TYPES.get(type);
        }
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getValueClassType(Class<?> classType, String value) {
        if (value == null || value.isEmpty()) {
            return getDefaultValue(classType);
        }
        if (classType == int.class || classType == Integer.class) {
            return Integer.parseInt(value);
        } else if (classType == short.class || classType == Short.class) {
            return Short.parseShort(value);
        } else if (classType == byte.class || classType == Byte.class) {
            return Byte.parseByte(value);
        } else if (classType == double.class || classType == Double.class) {
            return Double.parseDouble(value);
        } else if (classType == boolean.class || classType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (classType == float.class || classType == Float.class) {
            return Float.parseFloat(value);
        } else if (classType == long.class || classType == Long.class) {
            return Long.parseLong(value);
        } else {
            return classType.cast(value);
        }
    }

    private static Object getDefaultValue(Class<?> classType) {
        if (classType.isPrimitive()) {
            if (classType == boolean.class) return false;
            if (classType == char.class) return '\u0000';
            return 0;
        }
        return null;
    }


}
