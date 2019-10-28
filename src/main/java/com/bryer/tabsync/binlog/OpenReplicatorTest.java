package com.bryer.tabsync.binlog;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.bryer.tabsync.config.DataSyncConfig;
import com.google.code.or.OpenReplicator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;

@Component
public class OpenReplicatorTest implements Runnable {
	private static Properties prop = new Properties();
	//private final Session srcSession;
//    private final Session destSession;
//
//    public OpenReplicatorTest(@Qualifier("srcSession") Session srcSession) {
//     //   this.srcSession = srcSession;
//        
//    }

  
	@Override
	public void run() {
		try {
			System.out.println("开始执行Bin-Log监听程序");
			prop = new DataSyncConfig().InitProperties();
			
		//	String srcSql = "show master status;";
		    
		 //   List<Entity> entityList = srcSession.query(srcSql);
			final OpenReplicator or = new OpenReplicator();
			or.setUser(StrUtil.trim(prop.getProperty("username")));
			or.setPassword(StrUtil.trim(prop.getProperty("password")));
			or.setHost(StrUtil.trim(prop.getProperty("src-address")));
			System.out.println(StrUtil.trim(prop.getProperty("sql-port")));
			or.setPort(Integer.parseInt( StrUtil.trim(prop.getProperty("sql-port"))));
			or.setServerId(Integer.parseInt(StrUtil.trim(prop.getProperty("serverid"))) );
			or.setBinlogPosition(Integer.parseInt(StrUtil.trim(prop.getProperty("binlogposition"))) );
			or.setBinlogFileName(StrUtil.trim(prop.getProperty("binlogFileName")));
			
			or.setBinlogEventListener(new NotificationListener(prop));
			or.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
}
