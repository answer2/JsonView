package com.answer.library.JsonView.utils;
import java.util.List;
import java.util.Optional;

public class EmptyUtil {

    public static final String TAG = "EmptyUtil";

    public static boolean isNull(String value) {
        return !isNotNull(value);
    }

    
    public static <T> boolean isNotNull(T value) {
        return Optional.ofNullable(value).isPresent();
    }

}
