package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.peis.domain.bo.TjTeamInfoBo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 体检单位信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjTeamInfoService {

    /**
     * 查询体检单位信息
     */
    TjTeamInfoVo queryById(Long id);

    /**
     * 查询体检单位信息列表
     */
    TableDataInfo<TjTeamInfoVo> queryPageList(TjTeamInfoBo bo, PageQuery pageQuery);

    /**
     * 查询体检单位信息列表
     */
    List<TjTeamInfoVo> queryList(TjTeamInfoBo bo);

    /**
     * 新增体检单位信息
     */
    TjTeamInfo insertByBo(TjTeamInfoBo bo);

    /**
     * 修改体检单位信息
     */
    TjTeamInfo updateByBo(TjTeamInfoBo bo);

    /**
     * 校验并批量删除体检单位信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    String getTeamNoById(Long id);

    String selectTeamNameById(Long id);

    List<TjTeamInfoVo> queryListByIds(List<Long> teamIdList);
}
