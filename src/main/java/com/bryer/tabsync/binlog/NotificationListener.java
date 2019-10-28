package com.bryer.tabsync.binlog;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.QueryEvent;

import cn.hutool.core.util.StrUtil;


public class NotificationListener implements BinlogEventListener{
	private static Properties prop = new Properties();
	private InetAddress inet;
	//需要同步数据库
	private List<String> list = new ArrayList<String>();
	public NotificationListener(Properties prop ) {
		this.prop = prop;
		String databaseName = StrUtil.trim(prop.getProperty("databaseName"));
		for(String db:databaseName.split(",")) {
			list.add(db);
		}
	}
	public void onEvents(BinlogEventV4 event) {
		
		if(event==null){
			
			return;
		}
		
	    if(event instanceof QueryEvent){
			QueryEvent queryEvent = (QueryEvent)event;
			String daName = queryEvent.getDatabaseName().toString();
			if(list!=null&&list.size()==0) {
			    if(daName.length()>0) {
			    	System.out.println("需要执行的sql语句为："+queryEvent.getSql());
			    }
			}else if(list.contains(daName)&&daName.length()>0) {
				System.out.println("需要执行的sql语句为："+queryEvent.getSql());
				  // 创建数据包对象，封装要发送的数据，接收端IP，端口
          
           
                
        		try {
        	
         			byte[] data = queryEvent.getSql().toString().getBytes();
         		   //   byte[] date = queryEvent.getSql().toString().getBytes();
        			inet = InetAddress.getByName(StrUtil.trim(prop.getProperty("dest-address")));
        			//DatagramPacket dp = new DatagramPacket(date, date.length, inet,Integer.parseInt( StrUtil.trim(prop.getProperty("ip-port"))));
        			DatagramPacket dp = new DatagramPacket(data, data.length, inet,Integer.parseInt( StrUtil.trim(prop.getProperty("ip-port"))));
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
		}	
	}
}
