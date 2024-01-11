package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjTeamDept;
import org.fxkc.peis.domain.vo.TjTeamDeptVo;
import org.fxkc.peis.domain.bo.TjTeamDeptBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 单位部门信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjTeamDeptService {

    /**
     * 查询单位部门信息
     */
    TjTeamDeptVo queryById(Long id);

    /**
     * 查询单位部门信息列表
     */
    TableDataInfo<TjTeamDeptVo> queryPageList(TjTeamDeptBo bo, PageQuery pageQuery);

    /**
     * 查询单位部门信息列表
     */
    List<TjTeamDeptVo> queryList(TjTeamDeptBo bo);

    /**
     * 新增单位部门信息
     */
    Boolean insertByBo(TjTeamDeptBo bo);

    /**
     * 修改单位部门信息
     */
    Boolean updateByBo(TjTeamDeptBo bo);

    /**
     * 校验并批量删除单位部门信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    String getDeptNoById(Long id);
}
