package scrawler;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/12/11 23:19
 * @Modified By:
 * @Version:1.0.0
 **/
 public final class DruidConnection {

    private static DruidDataSource dataSource ;

    private DruidConnection(){

    }

    //该代码只执行一次保证dataSource的一致
    static{
        Properties properties = new Properties();
        dataSource = new DruidDataSource();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource.configFromPropety(properties);
    }

    public static Connection getConnection() throws IOException, SQLException {
        if(dataSource == null){
            Properties properties = new Properties();
            dataSource = new DruidDataSource();
            dataSource.configFromPropety(properties);
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));

        }
        return dataSource.getConnection();
    }
}
