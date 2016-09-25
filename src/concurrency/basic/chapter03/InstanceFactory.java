package concurrency.basic.chapter03;

/**
 * 正确的延迟初始化方案3
 * 基于类初始化的延迟初始化
 */
public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance; //这里将导致InstanceHolder类被初始化
    }

    static class Instance {
    }
}
