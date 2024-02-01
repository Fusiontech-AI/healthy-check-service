package org.fxkc.peis.third.core;


/**
 * @author zj
 * @description:  第三方服务提供者
 * @date 2024-01-31 18:07
 */
public interface ThirdServiceProvider {

    /**
     * 获取lis结果
     * @param objects 获取lis结果参数
     * @return lis结果数据
     */
    default Object lisResult(Object... objects) {
        return null;
    }

    /**
     * 获取pacs结果
     * @param objects 获取pacs结果参数
     * @return pacs结果数据
     */
    default Object pacsResult(Object... objects) {
        return null;
    }

    /**
     * 获取C14结果
     * @param objects 获取C14结果参数
     * @return C14结果数据
     */
    default Object c14Result(Object... objects) {
        return null;
    }
}
