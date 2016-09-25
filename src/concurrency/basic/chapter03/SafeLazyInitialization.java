package concurrency.basic.chapter03;

/**
 * 正确的延迟初始化方案2
 * 基于synchronized的延迟初始化
 */
public class SafeLazyInitialization {
    private static Instance instance;

    public synchronized static Instance getInstance() {
        if (instance == null)
            instance = new Instance();
        return instance;
    }

    static class Instance {
    }
}