package com.answer.library.JsonView.exceptions;

import com.answer.library.JsonView.debug.JsonLog;

public class LoadJsonException extends java.lang.RuntimeException {

    public static final String TAG = "LoadJsonException";

    public LoadJsonException() {
        super();
        JsonLog.d(TAG, "LoadJsonException:"+this.toString());
    }

    public LoadJsonException(String message) {
        super(message);
        JsonLog.d(TAG, "msg:"+message);
    }

    public LoadJsonException(String message, Throwable cause) {
        super(message,cause);
        JsonLog.d(TAG, "msg:"+message+"-LoadJsonException:"+cause.toString());
    }

   
    
   
}
