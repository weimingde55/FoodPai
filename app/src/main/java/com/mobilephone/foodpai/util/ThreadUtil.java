package com.mobilephone.foodpai.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ThreadUtil {
    private static ExecutorService executorService;

    public static void execute(Runnable runnable) {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(5);
        }
        executorService.submit(runnable);

    }
}
