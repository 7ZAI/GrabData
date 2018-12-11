package scrawler;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 23:16
 * @Modified By:
 * @Version:1.0.0
 **/
public class Test {

    public static void main(String[] args) {


//
//        LinkedList<String> list = new LinkedList<String>();
//        list.add(baseurl);
//
//        Links links = new Links(list);
//
//        final LinkedList<Address> addrlist = new LinkedList<Address>();
//
//        AreaScrawler areaScrawler =  new AreaScrawler(links,addrlist);

//        ThreadPool ThreadPoolExecutor

//        ExecutorService executorService  = Executors.newFixedThreadPool(3);
//        Future future = executorService.submit(areaScrawler);

        String firsturl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html";
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ExecutorService threadPool = new ThreadPoolExecutor(8, 16,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        LinkedBlockingQueue<Address> link = new LinkedBlockingQueue<Address>(2000);

//        threadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
//        threadPool.shutdown();
//        threadPool.submit(()-> System.out.println(Thread.currentThread().getName()+"dasdasdasd"));

        ScanFirstPage firstPage = new ScanFirstPage(firsturl,threadPool,link);
        threadPool.submit(firstPage);









//        Thread t1 = new Thread(areaScrawler);
//        Thread t2 = new Thread(areaScrawler);
//        Thread t3 = new Thread(areaScrawler);
//
//
//        t1.start();
//        t2.start();
//        t3.start();
//
//        try {
//            t1.join();
//            t2.join();
//            t3.join();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("地址数量：" + addrlist.size());


    }
}
