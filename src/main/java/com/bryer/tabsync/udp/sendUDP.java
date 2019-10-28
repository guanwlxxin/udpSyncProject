package com.bryer.tabsync.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 *  实现UDP协议的发送端:
 *    实现封装数据的类 java.net.DatagramPacket  将你的数据包装
 *    实现数据传输的类 java.net.DatagramSocket  将数据包发出去
 *    
 *  实现步骤:
 *    1. 创建DatagramPacket对象,封装数据, 接收的地址和端口
 *    2. 创建DatagramSocket
 *    3. 调用DatagramSocket类方法send,发送数据包
 *    4. 关闭资源
 *    
 *    DatagramPacket构造方法:
 *      DatagramPacket(byte[] buf, int length, InetAddress address, int port) 
 *      
 *    DatagramSocket构造方法:
 *      DatagramSocket()空参数
 *      方法: send(DatagramPacket d)
 *      
 */
public class sendUDP {

	public static void main(String[] args) {
		  // 创建数据包对象，封装要发送的数据，接收端IP，端口
        byte[] date = "aaaaP".getBytes();
        //创建InetAdress对象，封装自己的IP地址
        InetAddress inet;
		try {
			inet = InetAddress.getByName("192.168.77.135");
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

}
