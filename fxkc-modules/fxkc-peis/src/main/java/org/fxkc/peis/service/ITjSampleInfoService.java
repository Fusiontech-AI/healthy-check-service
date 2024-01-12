package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjSampleInfoBo;
import org.fxkc.peis.domain.vo.TjSampleInfoVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检样本配置信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
public interface ITjSampleInfoService {

    /**
     * 查询体检样本配置信息
     */
    TjSampleInfoVo queryById(Long id);

    /**
     * 查询体检样本配置信息列表
     */
    TableDataInfo<TjSampleInfoVo> queryPageList(TjSampleInfoBo bo, PageQuery pageQuery);

    /**
     * 查询体检样本配置信息列表
     */
    List<TjSampleInfoVo> queryList(TjSampleInfoBo bo);

    /**
     * 新增体检样本配置信息
     */
    Boolean insertByBo(TjSampleInfoBo bo);

    /**
     * 修改体检样本配置信息
     */
    Boolean updateByBo(TjSampleInfoBo bo);

    /**
     * 校验并批量删除体检样本配置信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
