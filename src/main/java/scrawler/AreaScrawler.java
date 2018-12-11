package scrawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 15:57
 * @Modified By:
 * @Version:1.0.0
 **/
public class AreaScrawler implements Runnable {


    public Links getLinks() {
        return links;
    }

    public LinkedList<Address> getAddrList() {
        return addrList;
    }

    private final Links links;

    private final LinkedList<Address> addrList;

    private static final String[] SELECT_STRING = {"tr.provincetr", "tr.citytr", "tr.countytr", "tr.towntr"};

    private static final String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html";

    public AreaScrawler(Links links, LinkedList<Address> addrList) {
        this.links = links;
        this.addrList = addrList;
    }

    //单个页面的任务
    @Override
    public void run() {
//        int count = 0;
        while (links.getSize() > 0) {

            int index;
            String urlAndPid = links.getUrl();


            if (urlAndPid.equals(baseurl)) {
                index = 0;
            } else {
                //根据‘/’的个数判断到了那个层级的页面 从未确定选择器 参数 另一个思路 遍历关键字数组 这样不够精准
                index = StringUtils.appearNumber(urlAndPid, "/") - StringUtils.appearNumber(baseurl, "/") + 1;
            }

            String url = "";
            String parentId = "";
            String newurl = "";
            Document document = null;
            Elements elements = null;

            StringBuilder tool = new StringBuilder();

            //获取全国省份信息
            if (index == 0) {

                url = urlAndPid;
                parentId = "0000";
//                document = StringUtils.getDocument(url);
                try {
                    document = Jsoup.connect(url).timeout(5000).get();
                } catch (IOException e) {
                    System.out.println( url + "连接超时！ 重新加入list");
                    links.addUrl(url);
                    continue;
                }
                elements = document.select(SELECT_STRING[index]);
                Elements eles = elements.select("a");
                for (Element ele : eles) {

                    Address address = new Address();
                    String uuid = StringUtils.uuid();

                    newurl = tool.append(ele.attr("abs:href")).append(",").append(uuid).toString();
                    address.setName(ele.html().replaceAll("<br>", ""));
                    address.setId(uuid);
                    address.setAreacode(null);
                    address.setPid(parentId);
                    links.addUrl(newurl);
                    tool.setLength(0);
                    synchronized (addrList) {
                        System.out.println(address);
                        addrList.add(address);

                    }

                }
                //获取市、区 、街道
            }

            if( index >0 && index<4  ){
                System.out.println(urlAndPid+"   index:"+index);
                url = urlAndPid.split(",")[0];
                parentId = urlAndPid.split(",")[1];
                try {
                    document = Jsoup.connect(url).timeout(5000).get();
                } catch (IOException e) {
                    System.out.println( url + "连接超时！ 重新加入list");
                    links.addUrl(urlAndPid);
                    continue;
                }
                elements = document.select(SELECT_STRING[index]);
                if (elements.size() <= 0) {
                    continue;
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
                    newurl = tool.append(aeles.get(0).attr("abs:href")).append(",").append(uuid).toString();
                    tool.setLength(0);

                    if (index < 3) {
                        //到了街道层 不用再添加新的url
                        links.addUrl(newurl);
                    }
                    synchronized (addrList) {
                        System.out.println(address);
                        addrList.add(address);
                    }
                }
            }

//            count++;
//            System.out.println(count);
        }

    }

}
