package thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    public static Map<String,Lock> LockMap = new ConcurrentHashMap<String,Lock>();

    public static void main(String[] args) {
        final SynObj sy = new SynObj();
        new Thread(new Runnable() {
            public void run() {
                startTask("1");
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                startTask("1");
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                startTask("2");
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                startTask("2");
            }
        }).start();
    }

    public static void startTask(String str) {
        Lock lock = null;
        synchronized (LockMap) {
            lock = LockMap.get(str);
            if(null == lock){
                lock = new ReentrantLock();
                LockMap.put(str, lock);
        }
        }
        lock.lock();
        System.out.println("============"+str);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
