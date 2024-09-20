package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjBasicCommonResultBo;
import org.fxkc.peis.domain.vo.TjBasicCommonResultVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检基础项目常见结果Service接口
 *
 * @author JunBaiChen
 * @date 2024-03-05
 */
public interface ITjBasicCommonResultService {

    /**
     * 查询体检基础项目常见结果
     */
    TjBasicCommonResultVo queryById(Long id);

    /**
     * 查询体检基础项目常见结果列表
     */
    TableDataInfo<TjBasicCommonResultVo> queryPageList(TjBasicCommonResultBo bo, PageQuery pageQuery);

    /**
     * 查询体检基础项目常见结果列表
     */
    List<TjBasicCommonResultVo> queryList(TjBasicCommonResultBo bo);

    /**
     * 新增体检基础项目常见结果
     */
    Boolean insertByBo(TjBasicCommonResultBo bo);

    /**
     * 修改体检基础项目常见结果
     */
    Boolean updateByBo(TjBasicCommonResultBo bo);

    /**
     * 校验并批量删除体检基础项目常见结果信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
