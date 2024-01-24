package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.bo.TjTeamGroupBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 团检分组信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
public interface ITjTeamGroupService {

    /**
     * 查询团检分组信息
     */
    TjTeamGroupVo queryById(Long id);

    /**
     * 查询团检分组信息列表
     */
    TableDataInfo<TjTeamGroupVo> queryPageList(TjTeamGroupBo bo, PageQuery pageQuery);

    /**
     * 查询团检分组信息列表
     */
    List<TjTeamGroupVo> queryList(TjTeamGroupBo bo);

    /**
     * 新增团检分组信息
     */
    Boolean insertByBo(TjTeamGroupBo bo);

    /**
     * 修改团检分组信息
     */
    Boolean updateByBo(TjTeamGroupBo bo);

    /**
     * 校验并批量删除团检分组信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void recordGroupInfo(List<TjTeamGroup> groupList);
}
