package org.fxkc.peis.third.core;

import org.fxkc.common.core.domain.R;

import java.util.concurrent.ExecutionException;

/**
 * @author zj
 * @description: 体检系统获取第三方结果抽象接口
 * @date 2024-01-28 17:55
 */
public interface IThridService {

    /**
     * 是否支持这个项目
     * @param itemId 项目id
     * @return
     */
    boolean supportItem(String itemId);

    /**
     * 获取第三方结果前置处理
     * @param objects 参数
     * @return
     */
    R postProcessBeforeGetResult(Object... objects);

    /**
     * 获取第三方结果后置处理
     * @param objects 参数
     * @return
     */
    R postProcessAfterGetResult(Object... objects) throws ExecutionException, InterruptedException;


    /**
     * 获取项目id
     * @param objects 参数
     * @return
     */
    Object getItemResult(Object... objects) throws ExecutionException, InterruptedException;
}
