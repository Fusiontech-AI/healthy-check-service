package org.fxkc.peis.third.core;

import org.fxkc.common.core.domain.R;

import java.util.concurrent.ExecutionException;

/**
 * @author zj
 * @description: 体检系统获取第三方结果顶层抽象接口
 * @date 2024-01-28 17:55
 */
public interface IThridService {

    /**
     * 是否支持这个项目
     * @param itemId 项目id
     * @return true-有处理该项目id的实现类  false-无处理该项目id的实现类
     */
    boolean supportItem(String itemId);

    /**
     * 获取第三方结果前置处理
     * @param objects 参数
     * @return 前置处理结果
     */
    R postProcessBeforeGetResult(Object... objects);

    /**
     * 获取第三方结果后置处理
     * @param objects 参数
     * @return 后置处理结果
     */
    R postProcessAfterGetResult(Object... objects) throws ExecutionException, InterruptedException;


    /**
     * 获取第三方结果
     * @param objects 参数
     * @return 第三方结果
     */
    Object getItemResult(Object... objects) throws ExecutionException, InterruptedException;
}
