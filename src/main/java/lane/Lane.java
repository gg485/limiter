package lane;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

//相同的请求并发调用时只有一个能得到执行
public class Lane<T> {
    private final Lock lock = new ReentrantLock();
    private Map<String, Call<T>> map = null;

    public T exec(String key, Supplier<T> supplier) throws InterruptedException {
        lock.lock();
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.containsKey(key)) {
            Call<T> c = map.get(key);
            lock.unlock();
            c.latch.await();
            return c.result;
        }
        Call<T> c = new Call<>();
        map.put(key, c);
        lock.unlock();

        c.result = supplier.get();
        c.latch.countDown();

        lock.lock();
        map.remove(key);
        lock.unlock();
        return c.result;
    }

    static class Call<T> {
        CountDownLatch latch = new CountDownLatch(1);
        T result;
    }
}
