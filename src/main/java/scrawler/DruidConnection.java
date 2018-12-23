package scrawler;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author:binblink
 * @Description 单例模式获取dataSource
 * @Date: Create on  2018/12/11 23:19
 * @Modified By:
 * @Version:1.0.0
 **/
 public final class DruidConnection {

    private static DruidDataSource dataSource = null ;

    private DruidConnection(){

    }

    public static Connection getConnection() throws IOException, SQLException {
        if(dataSource == null){
            synchronized (DruidConnection.class){
                if(dataSource == null){
                    dataSource = new DruidDataSource();
                    dataSource.setUrl("jdbc:mysql://localhost:3306/scrawler?characterEncoding=utf8&serverTimezone=UTC&useUnicode=true&useSSL=false");
                    dataSource.setUsername("root");
                    dataSource.setPassword("94816qibin");
                }
            }
        }
        return dataSource.getConnection();
    }
}
