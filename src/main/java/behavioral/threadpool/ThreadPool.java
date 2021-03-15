package behavioral.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("DuplicatedCode")
public class ThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        executor();

        executorService();

        threadPoolExecutor_newFixedThreadPool();
        threadPoolExecutor_newCachedThreadPool();
        threadPoolExecutor_newSingleThreadExecutor();
        threadPoolExecutor_newScheduledThreadPool();
        threadPoolExecutor_newScheduledThreadPool_fixedRate();

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

    /**
     * corePoolSize = 5
     * maximumPoolSize = Integer.MAX_VALUE
     * keepAliveTime = 0s
     * delay = 500 millis
     */
    private static void threadPoolExecutor_newScheduledThreadPool() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.schedule(() -> System.out.println("Hello World"), 500, TimeUnit.MILLISECONDS);
    }

    /**
     * corePoolSize = 5
     * maximumPoolSize = Integer.MAX_VALUE
     * keepAliveTime = 0s
     * initial delay = 500 millis
     * fixed rate execution = 100 millis
     */
    private static void threadPoolExecutor_newScheduledThreadPool_fixedRate() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("Hello World! count: " + lock.getCount());
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        lock.await(1000, TimeUnit.MILLISECONDS);
        future.cancel(true);
    }
}
