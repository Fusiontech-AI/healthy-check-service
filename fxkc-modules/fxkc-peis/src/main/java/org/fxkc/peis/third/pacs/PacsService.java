package org.fxkc.peis.third.pacs;

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
 * @description: 获取pacs结果业务实现类
 * @date 2024-01-28 18:14
 */

@Service
@Slf4j
public class PacsService implements IThridService {

    @Autowired
    PacsProvider pacsProvider;

    @Autowired
    @Qualifier(ThreadPoolConfig.PACS_POOL_PREX)
    ExecutorService pacsPool;

    /**
     * 获取pacs结果前置处理
     * @param objects 参数
     * @return 前置处理结果
     */
    @Override
    public R postProcessBeforeGetResult(Object... objects) {
        List<Integer> list = (List<Integer>)objects[1];
        List<Integer> collect = list.stream().filter(w -> 1 != w).collect(Collectors.toList());
        return R.ok(collect);
    }

    /**
     * 什么条件走这个服务
     * @param itemId 项目id
     * @return true-服务支持此项目id false-服务不支持此项目id
     */
    @Override
    public boolean supportItem(String itemId) {
        return "1".equals(itemId);
    }

    /**
     * 获取pacs结果
     * @param objects 参数
     * @return
     * @throws ExecutionException 异步调用方法提执行异常
     * @throws InterruptedException 线程被中断异常（如所有线程执行完成在等待时候，线程被中断）
     */
    @Override
    public Object getItemResult(Object... objects) throws ExecutionException, InterruptedException {
        List<Integer> projectInfos = (List<Integer>)objects[0];
        // 使用CompletableFuture并行查询数据库
        List<CompletableFuture<Integer>> futures = projectInfos.stream().map(e ->
            // todo 用pacsResponse替换统一封装
            CompletableFuture.supplyAsync(() -> {
                    Object result = pacsProvider.pacsResult(ServiceProviderEnum.JULEI_PACS, e);
                    return (Integer)result;
                },pacsPool)).toList();
        // 等待所有查询完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        // 获取所有查询结果
        List<Integer> results = allOf.thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
            .get();
        return results;
    }

    /**
     * 获取pacs结果后置处理
     * @param objects 参数
     * @return pacs结果后置处理结果
     */
    @Override
    public R postProcessAfterGetResult(Object... objects){
        log.info("pacs process data:{}", JSON.toJSONString(objects[1]));
        return R.ok("pacs process success!");
    }
}
