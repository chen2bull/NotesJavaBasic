package corejava.corejava1.ch14;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @version 1.0 2004-08-01
 * @author Cay Horstmann
 */
public class Ch14_10_ThreadPoolTest {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();
        
        //使用Executors类的工厂方法创建线程池
        ExecutorService pool = Executors.newCachedThreadPool(); 
        //还有固定线程数量的newFixedThreadPool和只有一个线程的池newSingleThreadExcecutor

        MatchCounter2 counter = new MatchCounter2(new File(directory), keyword, pool);
        
        // 将实现了Callable接口的类加到pool中，并返回实现了Future接口的类，
        Future<Integer> result = pool.submit(counter);

        // 这里加入任意其它的代码!!
        
        try {
            // 调用Future的get方法，在这里可以得到call方法的返回值
            System.out.println(result.get() + " matching files.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
        // 当用完一个线程池时，调用shutdown启动该线程池的关闭序列。该线程池不再接受新的任务。
        // 当所有任务完成后，线程池中线程死亡
        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size=" + largestPoolSize);

    }
}

/**
 * This task counts the files in a directory and its subdirectories that contain a given keyword.
 */
class MatchCounter2 implements Callable<Integer> {
    /**
     * Constructs a MatchCounter2.
     * 
     * @param directory
     *            the directory in which to start the search
     * @param keyword
     *            the keyword to look for
     * @param pool
     *            the thread pool for submitting subtasks
     */
    public MatchCounter2(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    public Integer call() {
        count = 0;
        try {
            File[] files = directory.listFiles();
            
            //当前目录下的所有一级子目录对应的线程，放在这个列表里
            ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();

            for (File file : files)
                if (file.isDirectory()) {
                    MatchCounter2 counter = new MatchCounter2(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if (search(file))
                        count++;
                }

            for (Future<Integer> result : results)
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        } catch (InterruptedException e) {
        }
        return count;
    }

    /**
     * Searches a file for a given keyword.
     * 
     * @param file
     *            the file to search
     * @return true if the keyword is contained in the file
     */
    public boolean search(File file) {
        try {
            Scanner in = new Scanner(new FileInputStream(file));
            boolean found = false;
            while (!found && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(keyword))
                    found = true;
            }
            in.close();
            return found;
        } catch (IOException e) {
            return false;
        }
    }

    private File            directory;
    private String          keyword;
    private ExecutorService pool;
    private int             count;
}
