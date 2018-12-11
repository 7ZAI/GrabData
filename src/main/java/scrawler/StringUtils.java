package scrawler;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 16:04
 * @Modified By:
 * @Version:1.0.0
 **/
public class StringUtils {

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 随机生成uid
     *
     * @return
     */
    public static String uuid() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取Docment对象
     *
     * @param url
     * @return
     */
    public static Document getDocument(String url) {

        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(url);
        Document document = null;

        // 设置 get 请求超时 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 15000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        // 3.执行 HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            // 4.处理 HTTP 响应内容
            byte[] responseBody = getMethod.getResponseBody();
            // 得到当前返回类型 // 读取为字节 数组
//            String contentType = getMethod.getResponseHeader("Content-Type").getValue();
            document = Jsoup.parse(new String(responseBody, "gb2312"), url);

            return document;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            System.out.println(url+" 连接超时！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }

        return document;
    }
}
