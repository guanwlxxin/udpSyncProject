package com.bryer.tabsync.udp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import cn.hutool.db.sql.Wrapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangnan@yansou.org
 */
@Component
public class ReceiveUDPlistening implements Runnable {

    private final static String TOP = "JYGC";
    
  //  private final Session srcSession;
 //   private final Session destSession;

//    public ReceiveUDPlistening(@Qualifier("srcSession") Session srcSession,@Qualifier("destSession") Session destSession) {
//        this.srcSession = srcSession;
//        this.destSession = destSession;
//    }
  public ReceiveUDPlistening() throws Exception {
//	  InetAddress    inet = InetAddress.getByName("127.0.0.1");
//	 
//      // 创建数据包对象，传递字节数组
//      dp = new DatagramPacket(data, data.length,inet,8888); 
}

    @Override
    public void run() {
        System.out.println("同步开始");
        AtomicInteger syncThreadSeq = new AtomicInteger();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3,r -> {
            Thread thread = new Thread(r);
            thread.setName("[TAB TO TAB:" + syncThreadSeq.incrementAndGet() + "]");
            return thread;
        });
        Runnable incRun = () -> {
        	
                 try(DatagramSocket ds = new DatagramSocket(8888);) {   
                  while(true) {
                	   //创建字节数据
                      byte[] data = new byte[1024];
                      InetAddress    inet = InetAddress.getByName("127.0.0.1");
//                 	 
                	 DatagramPacket dp =new DatagramPacket(data, data.length);
                	 ds.receive(dp);
                	 //获取接收到的字节个数
                     int length = dp.getLength();
                     //获取发送端的IP地址对象
                     String ip = dp.getAddress().getHostAddress();
                     int port = dp.getPort();
                     System.out.println(new String(data, 0, length) + "........." + ip + ":" + port);
                  }
                	 //获取接收到的字节个数
//                     int length = dp.getLength();
//                     String reData = new String(dp.getData(),dp.getOffset(),dp.getLength(),"UTF-8");
//                     System.out.println("jie"+reData);
                 } catch (Exception e) {
                     e.printStackTrace();
                     ThreadUtil.sleep(10 * 1000);
                 }
           
        };

        //开启一直接手UDP的线程
        executor.execute(incRun);

    }
}
