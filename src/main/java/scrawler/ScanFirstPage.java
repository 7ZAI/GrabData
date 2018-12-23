package scrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:binblink
 * @Description 扫描第一个页面
 * @Date: Create on  2018/12/7 1:22
 * @Modified By:
 * @Version:1.0.0
 **/
public class ScanFirstPage implements Runnable {

    private String firstURL;
    private ThreadPoolExecutor threadPoolExecutor;
    private LinkedBlockingQueue<Address> links;

    public ScanFirstPage(String url, ThreadPoolExecutor threadPoolExecutor, LinkedBlockingQueue<Address> links) {
        this.firstURL = url;
        this.threadPoolExecutor = threadPoolExecutor;
        this.links = links;
    }

    @Override
    public void run() {
        Document document ;
        String parentId  = "0000";
        int selectKey = 0;
        try {
            //设置5s超时
            document = Jsoup.connect(firstURL).timeout(5000).get();
        } catch (IOException e) {
            System.out.println(firstURL + "连接超时！ 重新提交");
            threadPoolExecutor.submit(new ScanFirstPage(firstURL, threadPoolExecutor,links));
            return;

        }

        Elements elements = document.select("tr.provincetr");
        Elements  aElements = elements.select("a");
        for (Element ele : aElements) {

            Address address = new Address();
            String uuid = StringUtils.uuid();

            String nextUrl = ele.attr("abs:href");
            address.setName(ele.html().replaceAll("<br>", ""));
            address.setId(uuid);
            address.setAreacode("");
            address.setPid(parentId);
            threadPoolExecutor.submit(new ScanPage(nextUrl,selectKey, threadPoolExecutor,uuid,links));
            try {
                links.put(address);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //threadPoolExecutor.submit(new InsertData(links,threadPoolExecutor));//启动消费程序！
        new Thread(new InsertData(links,threadPoolExecutor),"writer_Thread").start();
    }
}
