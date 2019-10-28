package com.bryer.tabsync;

import com.bryer.tabsync.binlog.OpenReplicatorTest;
import com.bryer.tabsync.udp.ReceiveUDPlistening;
import cn.hutool.core.util.StrUtil;
import com.bryer.tabsync.udp.SendUDPlistening;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhangnan@yansou.org
 */
@SpringBootApplication
public class BackPlaneSyncApplication {
    private static final Logger log = LoggerFactory.getLogger(BackPlaneSyncApplication.class);

    public static void main(String[] args) {
    	log.info("程序开始:{}","Begin!");
    	
       ConfigurableApplicationContext context = SpringApplication.run(BackPlaneSyncApplication.class,args);
     //   log.info("UDP接收开始:{}",context);
      //  SendUDPlistening  sync =  context.getBean(SendUDPlistening.class);
       //   ReceiveUDPlistening ort = context.getBean(ReceiveUDPlistening.class);
        OpenReplicatorTest ort = context.getBean(OpenReplicatorTest.class); 
      //  log.info("获得同步对象:{}",context);
      ort.run();
        log.info("程序结束:{}","End!");
    	
    }
}
