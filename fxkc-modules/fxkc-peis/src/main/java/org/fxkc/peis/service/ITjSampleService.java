package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjSampleBatchUpdateBo;
import org.fxkc.peis.domain.bo.TjSampleBo;
import org.fxkc.peis.domain.bo.TjSamplePageBo;
import org.fxkc.peis.domain.bo.TjSampleUpdateBo;
import org.fxkc.peis.domain.vo.TjSampleInfoListVo;
import org.fxkc.peis.domain.vo.TjSampleVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检样本Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjSampleService {

    /**
     * 查询体检样本
     */
    TjSampleVo queryById(Long id);

    /**
     * 查询体检样本列表
     */
    TableDataInfo<TjSampleVo> queryPageList(TjSamplePageBo bo, PageQuery pageQuery);

    /**
     * 查询体检样本列表
     */
    List<TjSampleVo> queryList(TjSampleBo bo);

    /**
     * 新增体检样本
     */
    Boolean insertByBo(TjSampleBo bo);

    /**
     * 修改体检样本
     */
    Boolean updateByBo(TjSampleBo bo);

    /**
     * 校验并批量删除体检样本信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<TjSampleInfoListVo> getCombinProjectBySampleId(Long id);

    Boolean updateCombinProjectBySampleId(TjSampleUpdateBo tjSampleUpdateBo);

    Boolean batchDisable(List<Long> ids);

    Boolean batchUpdateCategory(TjSampleBatchUpdateBo bo);
}
