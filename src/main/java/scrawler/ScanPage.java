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
 * @Description
 * @Date: Create on  2018/11/24 0:55
 * @Modified By:
 * @Version:1.0.0
 **/
public class ScanPage implements Runnable {

    private String url;
    private int keyIndex;
    private String parentId;
    private ExecutorService executorService;
    private static final String[] SELECT_KEY = {"tr.citytr", "tr.countytr", "tr.towntr"};
    private LinkedBlockingQueue<Address> links;

    public ScanPage(String url, int keyIndex, ExecutorService executorService, String parentId, LinkedBlockingQueue<Address> links) {
        this.url = url;
        this.keyIndex = keyIndex;
        this.executorService = executorService;
        this.parentId = parentId;
        this.links = links;
    }

    @Override
    public void run() {
        Document document = null;

        try {

            document = Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            System.out.println(url + "连接超时！ 重新加入list");
            executorService.submit(this);
            return;
        }
        Elements elements = document.select(SELECT_KEY[keyIndex]);
        if (elements.size() <= 0) {
            return;
        }
        for (Element ele : elements) {
            Address address = new Address();
            Elements aeles = ele.select("a");

            if (aeles.size() <= 0) {
                continue;
            }
            String uuid = StringUtils.uuid();
            address.setId(uuid);
            address.setAreacode(aeles.get(0).html());
            address.setPid(parentId);
            address.setName(aeles.get(1).html().replaceAll("<br>", ""));
            String newurl = aeles.get(0).attr("abs:href");
//            System.out.println(newurl);
            System.out.println(Thread.currentThread().getName()+"---------正在执行！" + address.toString());
            if (keyIndex < 2) {
                //到了街道层 不用再添加新的url
                executorService.submit(new ScanPage(newurl,keyIndex+1,executorService,uuid,links));
            }
            try {
                links.put(address);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
