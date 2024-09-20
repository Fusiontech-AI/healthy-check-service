package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjCombinationProjectInfoBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectInfoVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检组合项目详细信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjCombinationProjectInfoService {

    /**
     * 查询体检组合项目详细信息
     */
    TjCombinationProjectInfoVo queryById(Long id);

    /**
     * 查询体检组合项目详细信息列表
     */
    TableDataInfo<TjCombinationProjectInfoVo> queryPageList(TjCombinationProjectInfoBo bo, PageQuery pageQuery);

    /**
     * 查询体检组合项目详细信息列表
     */
    List<TjCombinationProjectInfoVo> queryList(TjCombinationProjectInfoBo bo);

    /**
     * 新增体检组合项目详细信息
     */
    Boolean insertByBo(TjCombinationProjectInfoBo bo);

    /**
     * 修改体检组合项目详细信息
     */
    Boolean updateByBo(TjCombinationProjectInfoBo bo);

    /**
     * 校验并批量删除体检组合项目详细信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<TjCombinationProjectInfoVo> queryBasicListByCombinIds(List<Long> combinIds);
}
