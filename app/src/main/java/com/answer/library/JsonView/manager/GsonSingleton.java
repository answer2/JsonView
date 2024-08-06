package com.answer.library.JsonView.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @Author AnswerDev
 * @Date 2024/08/05 22:11
 */

public class GsonSingleton {
    private static Gson instance;

    private GsonSingleton() {
        // private constructor to prevent instantiation
    }

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonSingleton.class) {
                if (instance == null) {
                    instance = new GsonBuilder().create();
                }
            }
        }
        return instance;
    }
}

