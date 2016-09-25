package concurrency.basic.chapter03;

/**
 * 正确的延迟初始化方案1
 * 基于双重检查锁定的延迟初始化
 */
public class SafeDoubleCheckedLocking {
    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null)
                    instance = new Instance();//instance为volatile，现在没问题了
            }
        }
        return instance;
    }

    static class Instance {
    }
}
