package scrawler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author:binblink
 * @Description
 * @Date: Create on  2018/11/9 15:26
 * @Modified By:
 * @Version:1.0.0
 **/
public class Links {

    private final LinkedList<String> list;

    public Links(LinkedList<String> list) {
        this.list = list;
    }

    public synchronized void addUrl(String url) {

        list.add(url);
        if(list.size()>0){
            this.notifyAll();
        }

    }

    public synchronized String getUrl(){

        while(list.size()<=0 ){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list.removeFirst();

    }

//    public static void main(String[] args) {
//
//        final Links l = new Links(new LinkedList<String>());
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(l.getUrl());
//            }
//        }).start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                l.addUrl("asdasd");
//            }
//        }).start();
//
//
//    }


}
