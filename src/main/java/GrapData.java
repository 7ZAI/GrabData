import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/4 23:28
 * @Modified By:
 * @Version:1.0.0
 **/
public class GrapData {

//    static HashSet<String> hashSet = new HashSet<String>();
//
//    static String[] SELECT_STRING = {"tr.provincetr", "tr.citytr", "tr.countytr", "tr.towntr"};
//
//
//    static String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017";
//
//    static int baseTemp = appearNumber(baseurl, "/");
//
//    public static void getData(String url) {
//
//
//        try {
//            Document doc = Jsoup.connect(url).get();
//            doc.charset();
//
//            int m = appearNumber(url, "/") - baseTemp;
//
//            if (m >= 4) {
//                return;
//            }
//
//            Elements eles = doc.select(SELECT_STRING[m]);
//
//            Elements es = eles.select("a");
//
//            String newurl = "";
//
//            if(m==0){
//                for(Element ele: es){
//                    newurl = ele.attr("abs:href");
//
//                    System.out.println(ele.attr("abs:href").replaceAll("<br>","") + "-----" +  ele.html().replaceAll("<br>",""));
//
////                    getData(newurl);
//                }
//
//            }else{
//                System.out.println(es.get(0).html().replaceAll("<br>","") + "-----" + es.get(1).html().replaceAll("<br>",""));
//                newurl = es.get(0).attr("abs.href");
////                getData(newurl);
//
//            }



//            getData(es.get(0).attr("abs.href"));

//            for (Element ele : es) {
//                String newurl = ele.attr("abs:href");
//                if (hashSet.contains(newurl)) {
//
//
//                    System.out.println(ele.html().replaceAll("<br>", ""));
//                    continue;
//                }
//                hashSet.add(ele.attr("abs:href"));
//                System.out.println(newurl + "  " + ele.html().replaceAll("<br>", ""));
//
//            }


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }



    public static void main(String[] args) {


//        getData(baseurl);

//        HttpClient httpClient = new HttpClient();
//

//
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//
//        GetMethod getMethod = new GetMethod(baseUrl);
//
//        // 设置 get 请求超时 5s
//        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
//        // 设置请求重试处理
//        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//        // 3.执行 HTTP GET 请求
//        try {
//            int statusCode = httpClient.executeMethod(getMethod);
//            // 判断访问的状态码
//            if (statusCode != HttpStatus.SC_OK) {
//                System.err.println("Method failed: " + getMethod.getStatusLine());
//            }
//            // 4.处理 HTTP 响应内容
//            byte[] responseBody = getMethod.getResponseBody();
//
//            // 得到当前返回类型 // 读取为字节 数组
//            String contentType = getMethod.getResponseHeader("Content-Type").getValue();
//
//            Document document = Jsoup.parse(new String(responseBody, "gb2312"), baseUrl);
//
//            Elements eles = document.getElementsByClass("provincetable");
//
//            Elements es = eles.select("a");
//
//            Document doc =Jsoup.connect(baseUrl).get();
//
//            for(Element ele: es){
//
//                System.out.println(ele.attr("abs:href")+ele.html().replaceAll("<br>",""));
//
//            }
////            if (!es.isEmpty()) {
////                System.out.println("下面将打印所有a标签： ");
////                System.out.println(es);
////            }
//
//
//        } catch (HttpException e) {
//            // 发生致命的异常，可能是协议不对或者返回的内容有问题
//            System.out.println("Please check your provided http address!");
//            e.printStackTrace();
//        } catch (IOException e) {
//            // 发生网络异常
//            e.printStackTrace();
//        } finally {
//            // 释放连接
//            getMethod.releaseConnection();
//        }

    }
}
