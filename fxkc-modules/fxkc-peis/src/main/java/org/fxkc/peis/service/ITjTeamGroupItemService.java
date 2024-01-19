package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjTeamGroupItem;
import org.fxkc.peis.domain.vo.TjTeamGroupItemVo;
import org.fxkc.peis.domain.bo.TjTeamGroupItemBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 团检分组对应项目Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
public interface ITjTeamGroupItemService {

    /**
     * 查询团检分组对应项目
     */
    TjTeamGroupItemVo queryById(Long id);

    /**
     * 查询团检分组对应项目列表
     */
    TableDataInfo<TjTeamGroupItemVo> queryPageList(TjTeamGroupItemBo bo, PageQuery pageQuery);

    /**
     * 查询团检分组对应项目列表
     */
    List<TjTeamGroupItemVo> queryList(TjTeamGroupItemBo bo);

    /**
     * 新增团检分组对应项目
     */
    Boolean insertByBo(TjTeamGroupItemBo bo);

    /**
     * 修改团检分组对应项目
     */
    Boolean updateByBo(TjTeamGroupItemBo bo);

    /**
     * 校验并批量删除团检分组对应项目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
