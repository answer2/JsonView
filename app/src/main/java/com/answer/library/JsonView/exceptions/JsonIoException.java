package com.answer.library.JsonView.exceptions;

import java.io.IOException;
import com.answer.library.JsonView.debug.JsonLog;

/**
 * @Author AnswerDev
 * @Date 2023/02/19 12:49
 * @Describe Json的io错误管理
 */
public class JsonIoException extends IOException {
    
    public static final String TAG = "JsonIoException";
    
    public JsonIoException() {
        super();
        JsonLog.d(TAG, "LoadJsonException:"+this.toString());
    }

    public JsonIoException(String message) {
        super(message);
        JsonLog.d(TAG, "msg:"+message);
    }

    public JsonIoException(String message, Throwable cause) {
        super(message,cause);
        JsonLog.d(TAG, "msg:"+message+"-LoadJsonException:"+cause.toString());
    }
    
}
