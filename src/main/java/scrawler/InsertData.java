package scrawler;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:binblink
 * @Description 46825
 * @Date: Create on  2018/12/11 23:13
 * @Modified By:
 * @Version:1.0.0
 **/
public class InsertData implements Runnable {

    private LinkedBlockingQueue<Address> links;
    private ThreadPoolExecutor threadPoolExecutor;

    public InsertData(LinkedBlockingQueue<Address> links, ThreadPoolExecutor threadPoolExecutor) {
        this.links = links;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void run() {

        int count = 0;
        int totalnumber = 0;
        int limitNumber = 1000;
        Connection connection = null;
        String sql = "INSERT INTO addr(id,pid,areacode,name) VALUES(?,?,?,?) ";

        try {
            connection = DruidConnection.getConnection();
            connection.setAutoCommit(false);
            System.out.println("写数据库线程正在执行！" + links.size());
            PreparedStatement prep = connection.prepareStatement(sql);

       while(true){
           while (count < limitNumber) {

               Address address = links.take();

               prep.setString(1, address.getId().toString());
               prep.setString(2, address.getPid().toString());
               prep.setString(3, address.getAreacode());
               prep.setString(4, address.getName().toString());
               prep.addBatch();
               count++;
               if(links.size() == 0 && threadPoolExecutor.getQueue().size()==0 && threadPoolExecutor.getActiveCount()==0){
                   break;
               }
           }

           if (count > 0) {
               boolean su =  prep.executeBatch().length > 0 ? true:false;

               System.out.println("写入数据库执行"+ (su ? "成功":"失败"));
               totalnumber += count;
               System.out.println("已添加到数据库数据量为----------" + totalnumber);
               count = 0;
           }

           if(links.size() == 0 && threadPoolExecutor.getQueue().size()==0 && threadPoolExecutor.getActiveCount()==0){
               break;
           }
       }

        connection.commit();
        System.out.println("当前线程数："+threadPoolExecutor.getActiveCount()+"  任务数："+threadPoolExecutor.getQueue().size()+"   数据："+links.size());
        threadPoolExecutor.shutdown();

        } catch (Exception e) {
            try {
                System.out.println("发生异常 添加失败 数据回滚");
                connection.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                System.out.println("数据回滚失败");
                e1.printStackTrace();
            }
        }
    }
}
