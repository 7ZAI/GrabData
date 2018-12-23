import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/12/23 23:15
 * @Modified By:
 * @Version:1.0.0
 **/
public class ExecutorsTest {

    public static void main(String[] args) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("scan-pool-%d").build();
        final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(128), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程数--------"+threadPool.getActiveCount());
                System.out.println(Thread.currentThread().getName()+"---------正在执行");
            }
        });

        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程数--------"+threadPool.getActiveCount());
                System.out.println(Thread.currentThread().getName()+"---------正在执行");
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程数--------"+threadPool.getActiveCount());

    }

}
