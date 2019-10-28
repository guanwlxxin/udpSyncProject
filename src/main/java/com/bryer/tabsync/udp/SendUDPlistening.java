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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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
public class SendUDPlistening implements Runnable {

    @Override
    public void run() {
        System.out.println("同步开始");
        AtomicInteger syncThreadSeq = new AtomicInteger();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3,r -> {
            Thread thread = new Thread(r);
            thread.setName("[TAB TO TAB:" + syncThreadSeq.incrementAndGet() + "]");
            return thread;
        });
        File incFile1 = new File("incrementTab.txt");
        File incFile2 = new File("/root/incrementTab.txt");
        File updFile1 = new File("updateTab.txt");
        File updFile2 = new File("/root/updateTab.txt");
        Runnable incRun = () -> {
            for (; ; ) {
            	  // 创建数据包对象，封装要发送的数据，接收端IP，端口
                byte[] date = "aaaaP".getBytes();
                //创建InetAdress对象，封装自己的IP地址
                InetAddress inet;
        		try {
        			inet = InetAddress.getByName("127.0.0.1");
        			DatagramPacket dp = new DatagramPacket(date, date.length, inet,7000);
        	        //创建DatagramSocket对象，数据包的发送和接收对象
        	        DatagramSocket ds = new DatagramSocket();
        	        //调用ds对象的方法send，发送数据包
        	        ds.send(dp);
        	        ds.close();
        		} catch (UnknownHostException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (SocketException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
            }
        };

        //开启一直接手UDP的线程
        executor.execute(incRun);

    }
}
