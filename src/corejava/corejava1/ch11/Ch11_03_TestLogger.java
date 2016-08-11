package corejava.corejava1.ch11;

import java.util.logging.*;

/**
 * 演示Logger的使用
 */
public class Ch11_03_TestLogger {
    public Ch11_03_TestLogger() {
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.ALL);// 改变日志的记录级别,可以用来
        try {
            final int LOG_ROTATION_COUNT = 10;
            // 在用户主目录(%h/)中使用TestLogger%g.log.(%g为0~LOG_ROTATION_COUNT-1,共LOG_ROTATION_COUNT个文件)
            // 为日志文件,限定这个日志文件的大小为10000B,当文件(大约)超出大小限制,使用下一个文件.
            // 当文件数目超出了大小限制,最旧的文件就会被删除,%t表示当前的临时目录
            FileHandler fileHandler = new FileHandler("%h/TestLogger%g.log", 10000, LOG_ROTATION_COUNT, true);
            // 用简单的Formatter代替默认的XMLFormatter来对日志文件进行格式化
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.info("构造logger对象");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "打开文件错误", e);
        }
    }

    public static void main(String[] args) {
        Ch11_03_TestLogger testLogger = new Ch11_03_TestLogger();
        testLogger.TestingLogger();
        int re_num;
        re_num = testLogger.divide(9, 3);
        System.out.println(re_num);
        re_num = testLogger.divide(9, 0);
        System.out.println(re_num);
    }

    public void TestingLogger() {   // 日志记录的六个等级
        logger.severe("Logger 严重");
        logger.warning("Logger 警告");
        logger.info("Logger 信息");
        logger.config("Logger 配置");
        logger.fine("Logger 良好"); // 也可以将程序员需要而用户不需要的信息显示在这里
        logger.finer("Logger 较好");
        logger.finest("Logger 最好");
    }

    public int divide(int a, int b) {
        try {
            logger.entering(getClass().getName(), "进入方法divide"); // 进入方法divide,用跟踪执行的流程,更准确
            logger.info("a: " + a);
            logger.info("b: " + b);
            if (b == 0) {
                // 用以下的方式来抛出异常
                Exception exception = new Exception("在这里发生了一个除零异常");
                logger.throwing(getClass().getName(), "divide", exception);
                throw exception;
            }
            logger.exiting(getClass().getName(), "divide函数正常结束");
            return a / b;
        } catch (Exception e) {
            // 用这个方法来记录异常中的信息到日志文件(同时输出)
            logger.log(Level.SEVERE, "方法出错", e);
            logger.exiting(getClass().getName(), "divide函数非正常结束");  // 结束方法
            return -1;
        }
    }

    private Logger logger;
}
