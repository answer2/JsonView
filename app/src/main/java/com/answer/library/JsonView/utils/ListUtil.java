package com.answer.library.JsonView.utils;

import java.util.List;
/**
 * @Author AnswerDev
 * @Date 2023/02/25 21:18
 * @Describe List工具类
 */
import java.util.List;

public class ListUtil {

    public static final String TAG = "ListUtil";

    /**
     * Converts a List to an array.
     * @param list The list to convert.
     * @param clazz The class type of the array elements.
     * @param <T> The type of the elements in the list.
     * @return An array containing the elements of the list.
     */
    public static <T> T[] listToArray(List<T> list, Class<T> clazz) {
        if (list == null) return null;
        @SuppressWarnings("unchecked")
            T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, list.size());
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Converts a List of objects to a float array.
     * @param list The list to convert.
     * @param <T> The type of the elements in the list.
     * @return A float array containing the float representation of the elements.
     */
    public static <T> float[] listToFloatArray(List<T> list) {
        if (list == null) return new float[0];
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = Float.parseFloat(list.get(i).toString());
        }
        return array;
    }
}

