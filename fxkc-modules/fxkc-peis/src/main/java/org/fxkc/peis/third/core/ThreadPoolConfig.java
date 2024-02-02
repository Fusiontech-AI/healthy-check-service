package org.fxkc.peis.third.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ExecutorService;

/**
 * @author zj
 * @description: 自定义线程池
 * @date 2024-02-01 14:00
 */
@Configuration
@Slf4j
public class ThreadPoolConfig implements DisposableBean, ApplicationContextAware {

    public final static String LIS_POOL_PREX = "lis-pool-";

    public final static String PACS_POOL_PREX = "pacs-pool-";

    public final static String DEVICE_POOL_PREX = "device-pool-";

    private ApplicationContext applicationContext;

    public ThreadPoolConfig() {
        super();
    }

    @Override
    public void destroy() {
        log.warn("begin to destroy thread pool-------------");
        ExecutorService lisPool = applicationContext.getBean(LIS_POOL_PREX,ExecutorService.class);
        ExecutorService pacsPool= applicationContext.getBean(PACS_POOL_PREX,ExecutorService.class);
        ExecutorService devicePool= applicationContext.getBean(DEVICE_POOL_PREX,ExecutorService.class);
        if (!ObjectUtils.isEmpty(lisPool)){
            lisPool.shutdown();
        }
        if (!ObjectUtils.isEmpty(pacsPool)){
            pacsPool.shutdown();
        }
        if (!ObjectUtils.isEmpty(devicePool)){
            devicePool.shutdown();
        }
        log.warn("finished destroy thread pool-------------");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Bean(LIS_POOL_PREX)
    @Lazy
    public ExecutorService getLisPool(){
        return ThreadUtil.newFixedExecutor(5,LIS_POOL_PREX,true);
    }

    @Bean(PACS_POOL_PREX)
    @Lazy
    public ExecutorService getPacsPool(){
        return ThreadUtil.newFixedExecutor(4,PACS_POOL_PREX,true);
    }

    @Bean(DEVICE_POOL_PREX)
    @Lazy
    public ExecutorService getDevicePool(){
        return ThreadUtil.newFixedExecutor(3,DEVICE_POOL_PREX,true);
    }
}
