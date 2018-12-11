import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/12/1 23:45
 * @Modified By:
 * @Version:1.0.0
 **/
public class DruidTest {

    public static void main(String[] args) throws IOException, SQLException {

        Properties properties = new Properties();
        DruidDataSource dataSource = new DruidDataSource();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
        dataSource.configFromPropety(properties);

//        dataSource.setConnectProperties();
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("");
        preparedStatement.addBatch();

    }
}
