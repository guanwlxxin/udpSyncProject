package com.bryer.tabsync.config;

import cn.hutool.core.util.StrUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author zhangnan@yansou.org
 */
@Configuration
public class DataSyncConfig {
    private static Properties prop = new Properties();
  
    static {
        File file = null;

        File file1 = new File("F:/srcdataSync.properties");
      //  File file1 = new File("srcdataSync.properties");
        File file2 = new File("/root/srcdataSync.properties");
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (file1.exists() && file1.isFile()) {
            file = file1;
        }
        if (file2.exists() && file2.isFile()) {
            file = file2;
        }

        try {
            assert file != null;

            prop.load(new FileReader(file));
            if (runTime == null) {
	            System.err.println("Create runtime false!");
	        }
            pro = runTime.exec("arp -s "+StrUtil.trim(prop.getProperty("dest-address"))+" "+StrUtil.trim(prop.getProperty("dest-mac")));
            Thread.sleep(500);
            //ifconfig ens32 promisc 
        //    pro = runTime.exec("ifconfig "+StrUtil.trim(prop.getProperty("dest-eth"))+" promisc");
          //  Thread.sleep(500);
            pro.destroy();
        } catch (IOException | InterruptedException e) {
        	System.out.println(e);
        }
      
    }

    @Bean
    @Qualifier("initProperties")
    public Properties InitProperties() throws IOException, PropertyVetoException {
      
        return prop;
    }
    @Bean
    @Qualifier("MysqlDataSource")
    public DataSource MysqlDataSource() throws IOException, PropertyVetoException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
//
//
//        ds.setJdbcUrl("jdbc:mysql://192.168.77.135:3306/"+StrUtil.trim(prop.getProperty("databaseName"))+"?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
//        ds.setUser(StrUtil.trim(prop.getProperty("re-username")));
//        ds.setPassword(StrUtil.trim(prop.getProperty("re-password")));
//        System.out.println("源数据库URL:" + ds.getJdbcUrl());
        return ds;
    }
   
}
