package scrawler;

import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/16 23:58
 * @Modified By:
 * @Version:1.0.0
 **/
public class ConnectionDriver {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        Properties prop = new Properties();

        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));

        Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));

        String sql = "select * from addr";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
    }
}
