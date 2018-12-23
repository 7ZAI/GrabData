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
 * @Description
 * @Date: Create on  2018/11/24 0:55
 * @Modified By:
 * @Version:1.0.0
 **/
public class ScanPage implements Runnable {

    private String url;
    private int keyIndex;
    private String parentId;
    private ThreadPoolExecutor threadPoolExecutor;
    private static final String[] SELECT_KEY = {"tr.citytr", "tr.countytr", "tr.towntr"};
    private LinkedBlockingQueue<Address> links;

    public ScanPage(String url, int keyIndex, ThreadPoolExecutor threadPoolExecutor, String parentId, LinkedBlockingQueue<Address> links) {
        this.url = url;
        this.keyIndex = keyIndex;
        this.threadPoolExecutor = threadPoolExecutor;
        this.parentId = parentId;
        this.links = links;
    }

    @Override
    public void run() {
        Document document = null;

        try {

            document = Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            System.out.println(url + "连接超时！ 重新加入任务");
            threadPoolExecutor.submit(this);
            return;
        }
        Elements elements = document.select(SELECT_KEY[keyIndex]);
//        if (elements.size() <= 0) {
//            return;
//        }
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

            if (keyIndex < 2) {
                //到了街道层 不用再添加新的url
                threadPoolExecutor.submit(new ScanPage(newurl,keyIndex+1, threadPoolExecutor,uuid,links));
            }
//            System.out.println(Thread.currentThread().getName()+"---------正在执行！ 队列中任务数量为：" + threadPoolExecutor.getQueue().size());
            try {
                links.put(address);
//                synchronized (links){
//                    System.out.println(Thread.currentThread5().getName()+"---------正在执行！" + address.toString()+"当前个数----"+links.size());
//                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
