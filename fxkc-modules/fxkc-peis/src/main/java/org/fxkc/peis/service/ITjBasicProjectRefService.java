package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjBasicProjectRef;
import org.fxkc.peis.domain.vo.TjBasicProjectRefVo;
import org.fxkc.peis.domain.bo.TjBasicProjectRefBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 体检基础项目参考信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjBasicProjectRefService {

    /**
     * 查询体检基础项目参考信息
     */
    TjBasicProjectRefVo queryById(Long id);

    /**
     * 查询体检基础项目参考信息列表
     */
    TableDataInfo<TjBasicProjectRefVo> queryPageList(TjBasicProjectRefBo bo, PageQuery pageQuery);

    /**
     * 查询体检基础项目参考信息列表
     */
    List<TjBasicProjectRefVo> queryList(TjBasicProjectRefBo bo);

    /**
     * 新增体检基础项目参考信息
     */
    Boolean insertByBo(TjBasicProjectRefBo bo);

    /**
     * 修改体检基础项目参考信息
     */
    Boolean updateByBo(TjBasicProjectRefBo bo);

    /**
     * 校验并批量删除体检基础项目参考信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
