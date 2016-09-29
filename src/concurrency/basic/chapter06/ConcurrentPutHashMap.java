/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package concurrency.basic.chapter06;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 并发put
 * 
 * @author tengfei.fangtf
 * @version $Id: Snippet.java, v 0.1 2015-7-31 下午11:53:55 tengfei.fangtf Exp $
 */
public class ConcurrentPutHashMap {

    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> hashMap = new HashMap<>();
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
    }


    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
