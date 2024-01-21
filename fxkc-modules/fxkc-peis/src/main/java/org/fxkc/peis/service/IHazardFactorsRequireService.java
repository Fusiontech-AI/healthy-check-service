package org.fxkc.peis.service;

import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 危害因素必检项目主Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface IHazardFactorsRequireService {

    /**
     * 查询危害因素必检项目主
     */
    HazardFactorsRequireVo queryById(Long id);

    /**
     * 查询危害因素必检项目主列表
     */
    TableDataInfo<HazardFactorsRequireVo> queryPageList(HazardFactorsRequireBo bo, PageQuery pageQuery);

    /**
     * 查询危害因素必检项目主列表
     */
    List<HazardFactorsRequireVo> queryList(HazardFactorsRequireBo bo);

    /**
     * 新增危害因素必检项目主
     */
    Boolean insertByBo(HazardFactorsRequireBo bo);

    /**
     * 修改危害因素必检项目主
     */
    Boolean updateByBo(HazardFactorsRequireBo bo);

    /**
     * 校验并批量删除危害因素必检项目主信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    HazardFactorsRequireVo hazardFactorsQuery(HazardFactorsRequireBo bo, PageQuery pageQuery);
}
