package org.fxkc.peis.third.lis;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.domain.R;
import org.fxkc.peis.third.core.IThridService;
import org.fxkc.peis.third.core.ThreadPoolConfig;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author zj
 * @description: 获取lis结果业务实现类
 * @date 2024-01-28 18:14
 */

@Service
@Slf4j
public class LisService implements IThridService {

    @Autowired
    LisProvider lisProvider;

    @Autowired
    @Qualifier(ThreadPoolConfig.LIS_POOL_PREX)
    ExecutorService lisPool;

    /**
     * 获取lis结果前置处理
     * @param objects 参数
     * @return 前置处理结果
     */
    @Override
    public R postProcessBeforeGetResult(Object... objects) {
        List<String> list = (List<String>)objects[1];
        List<String> collect = list.stream().filter(e -> !"a".equals(e)).collect(Collectors.toList());
        return R.ok(collect);
    }

    /**
     * 决定什么条件走这个服务
     * @param itemId 项目id
     * @return true-这个服务支持这个项目id false-这个服务不支持这个项目id
     */
    @Override
    public boolean supportItem(String itemId) {
        return "a".equals(itemId);
    }

    /**
     *
     * @param objects 参数
     * @return 第三方结果
     * @throws ExecutionException 执行方法体抛出异常
     * @throws InterruptedException 线程中断异常
     */
    @Override
    public Object getItemResult(Object... objects) throws ExecutionException, InterruptedException {
        List<String> projectInfos = (List<String>)objects[0];
        // 使用CompletableFuture并行查询数据库
        List<CompletableFuture<String>> futures = projectInfos.stream().map(e ->
            // todo 用lisResponse替换统一封装
            CompletableFuture.supplyAsync(() -> {
                    Object result = lisProvider.lisResult(ServiceProviderEnum.FUSIONTECH_LIS, e);
                    return JSON.toJSONString(result);
                },lisPool)).toList();
        // 等待所有查询完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        // 获取所有查询结果
        List<String> results = allOf.thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
                .get();
        return results;
    }

    /**
     * 获取lis结果后后置处理
     * @param objects 参数
     * @return 后置处理结果
     */
    @Override
    public R postProcessAfterGetResult(Object... objects){
        log.info("lis process data:{}", JSON.toJSONString(objects[1]));
        return R.ok("lis process success!");
    }
}
