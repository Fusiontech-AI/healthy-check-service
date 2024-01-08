package org.fxkc.peis;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 业务模块
 *
 * @author fxkc
 */
@EnableDubbo
@SpringBootApplication
public class FxKcPeisApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FxKcPeisApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
