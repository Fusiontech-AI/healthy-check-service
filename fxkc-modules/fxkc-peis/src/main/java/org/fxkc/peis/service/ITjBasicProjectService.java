package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjBasicProjectBo;
import org.fxkc.peis.domain.vo.TjBasicProjectVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检基础项目Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjBasicProjectService {

    /**
     * 查询体检基础项目
     */
    TjBasicProjectVo queryById(Long id);

    /**
     * 查询体检基础项目列表
     */
    TableDataInfo<TjBasicProjectVo> queryPageList(TjBasicProjectBo bo, PageQuery pageQuery);

    /**
     * 查询体检基础项目列表
     */
    List<TjBasicProjectVo> queryList(TjBasicProjectBo bo);

    /**
     * 新增体检基础项目
     */
    Boolean insertByBo(TjBasicProjectBo bo);

    /**
     * 修改体检基础项目
     */
    Boolean updateByBo(TjBasicProjectBo bo);

    /**
     * 校验并批量删除体检基础项目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
