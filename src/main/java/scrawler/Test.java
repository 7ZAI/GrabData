package scrawler;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 23:16
 * @Modified By:
 * @Version:1.0.0
 **/
public class Test {

    public static void main(String[] args) {

        String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017";

        LinkedList<String> list = new LinkedList<String>();
        list.add(baseurl);

        Links links = new Links(list);

        final LinkedList<Address> addrlist = new LinkedList<Address>();

        AreaScrawler areaScrawler =  new AreaScrawler(links,addrlist);

//        ThreadPool ThreadPoolExecutor

        Thread t1 = new Thread(areaScrawler);


        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Address addr : areaScrawler.getAddrList()){
            System.out.println(addr);
        }


    }
}
