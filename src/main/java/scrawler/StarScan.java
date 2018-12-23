package scrawler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/12/24 2:03
 * @Modified By:
 * @Version:1.0.0
 **/
public class StarScan {

    public static void main(String[] args) {


        String firstUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html";
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("scan-pool-%d").build();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 16,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50000), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        LinkedBlockingQueue<Address> links = new LinkedBlockingQueue<Address>(50000);

        ScanFirstPage firstPage = new ScanFirstPage(firstUrl, threadPool, links);
        threadPool.submit(firstPage);
    }
}
