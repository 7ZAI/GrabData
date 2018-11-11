package scrawler;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    private static final String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017";

    public AreaScrawler(Links links, LinkedList<Address> addrList) {
        this.links = links;
        this.addrList = addrList;
    }

    //单个页面的任务
    @Override
    public void run() {

        String urlAndPid = links.getUrl();
        //根据‘/’的个数判断到了那个层级的页面 从未确定选择器 参数
        int index = StringUtils.appearNumber(urlAndPid, "/") - StringUtils.appearNumber(baseurl, "/");

        if (index > 3) {
            return;
        }

        String url = "";
        String parentId ="";
        String newurl = "";
        Document document  = null;
        Elements elements = null;

        Address address = new Address();
        StringBuilder tool = new StringBuilder();

        //获取全国省份信息
        if (index == 0) {
            url = urlAndPid;
            parentId = "0000";
            document = StringUtils.getDocument(url);
            elements = document.select(SELECT_STRING[index]);
            Elements eles = elements.select("a");
            for (Element ele : eles) {
                address.setName(ele.html().replaceAll("<br>", ""));
                String uuid = StringUtils.uuid();
                address.setId(uuid);
                address.setAreacode(null);
                address.setPid(parentId);
                newurl = tool.append(ele.attr("abs:href")).append(",").append(uuid).toString();
                tool.setLength(0);
                links.addUrl(newurl);

                synchronized (addrList) {
                    addrList.add(address);

                }

            }
            //获取市、区 、街道
        } else {
            url = urlAndPid.split(",")[0];
            parentId = urlAndPid.split(",")[1];
            document = StringUtils.getDocument(url);
            elements = document.select(SELECT_STRING[index]);

            for (Element ele : elements) {
                Elements aeles = ele.select("a");

                String uuid = StringUtils.uuid();
                address.setId(uuid);
                address.setAreacode(aeles.get(0).html());
                address.setPid(parentId);
                address.setName(aeles.get(1).html().replaceAll("<br>", ""));
                newurl = tool.append(aeles.get(0).attr("abs:href")).append(",").append(uuid).toString();
                tool.setLength(0);

                if (index != 3) {
                    //到了街道层 不用再添加新的url
                    links.addUrl(newurl);
                }

                synchronized (addrList) {
                    addrList.add(address);
                }
            }
        }
    }



}
