package scrawler;


import java.sql.Connection;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/12/11 23:13
 * @Modified By:
 * @Version:1.0.0
 **/
public class InsertData implements Runnable {


    @Override
    public void run() {

        Connection connection;
        int count = 1000;
        String sql = "INSERT INTO addr";

        try {
            connection = DruidConnection.getConnection();
            connection.setSavepoint();

            connection.setAutoCommit(false);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
