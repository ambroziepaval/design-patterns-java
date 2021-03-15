package behavioral.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("DuplicatedCode")
public class ThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        executor();

        executorService();

        threadPoolExecutor_newFixedThreadPool();
        threadPoolExecutor_newCachedThreadPool();
        threadPoolExecutor_newSingleThreadExecutor();

        System.exit(0);
    }

    private static void executor() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Hello world"));
    }

    private static void executorService() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> "Hello World");
        System.out.println("Some operation");
        System.out.println(future.get());
        System.out.println("After get");
    }

    /**
     * corePoolSize = 2
     * maximumPoolSize = 2
     * keepAliveTime = 0s
     */
    private static void threadPoolExecutor_newFixedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        System.out.println("FixedThreadPool: " + threadPoolExecutor.getPoolSize());
        System.out.println("FixedThreadPool: " + threadPoolExecutor.getQueue().size());
    }

    /**
     * corePoolSize = 0
     * maximumPoolSize = Integer.MAX_VALUE
     * keepAliveTime = 60s
     */
    private static void threadPoolExecutor_newCachedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        System.out.println("CachedThreadPool: " + threadPoolExecutor.getPoolSize());
        System.out.println("CachedThreadPool: " + threadPoolExecutor.getQueue().size());
    }

    private static void threadPoolExecutor_newSingleThreadExecutor() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> counter.set(1));
        System.out.println("SingleThreadExecutor: " + counter.get());

        executor.submit(() -> counter.compareAndSet(1, 2));
        System.out.println("SingleThreadExecutor: " + counter.get());

        Thread.sleep(500);
        System.out.println("SingleThreadExecutor: " + counter.get());
    }
}
