package concurrency.basic.chapter05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 10-16 读写锁简单例子
 */
public class Cache<Type> {
    private final Map<String, Type> map;
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Cache() {
        map = new HashMap<String, Type>();
    }

    public final Type get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public final Type put(String key, Type value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

    public static void main(String[] args) {
        Cache<String> stringCache = new Cache<String>();
        stringCache.put("abc", "bbb");
        System.out.println(stringCache.get("abc"));
    }
}
