package scrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author:binblink
 * @Description 扫描第一个页面
 * @Date: Create on  2018/12/7 1:22
 * @Modified By:
 * @Version:1.0.0
 **/
public class ScanFirstPage implements Runnable {

    private String firstURL;

    private ExecutorService executorService;

    private LinkedBlockingQueue<Address> links;

    public ScanFirstPage(String url, ExecutorService executorService,LinkedBlockingQueue<Address> links) {
        this.firstURL = url;
        this.executorService = executorService;
        this.links = links;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"---------正在执行！" + firstURL);
        Document document = null;
        String parentId = "";
        int selectKey = 0;
        try {
            parentId = "0000";
            document = Jsoup.connect(firstURL).timeout(5000).get();
        } catch (IOException e) {
            System.out.println(firstURL + "连接超时！ 重新提交");
            executorService.submit(new ScanFirstPage(firstURL, executorService,links));
            return;

        }

        Elements elements = document.select("tr.provincetr");
        Elements eles = elements.select("a");
        for (Element ele : eles) {

            Address address = new Address();
            String uuid = StringUtils.uuid();

            String newurl = ele.attr("abs:href");
            address.setName(ele.html().replaceAll("<br>", ""));
            address.setId(uuid);
            address.setAreacode(null);
            address.setPid(parentId);
            executorService.submit(new ScanPage(newurl,selectKey,executorService,uuid,links));
            System.out.println(Thread.currentThread().getName()+"---------正在执行！" + address.toString());
            try {
                links.put(address);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
