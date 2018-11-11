import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/7 0:49
 * @Modified By:
 * @Version:1.0.0
 **/
public class Test {

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText 源字符串
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

    public static void main(String[] args) {
        String test = "asdklad ";

        System.out.println(test.replaceAll("<br>",""));
         String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017";
         String sda = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/das/ds/d";
        System.out.println(appearNumber(sda,"/"));
        System.out.println(appearNumber(baseurl,"/"));


    }
}
