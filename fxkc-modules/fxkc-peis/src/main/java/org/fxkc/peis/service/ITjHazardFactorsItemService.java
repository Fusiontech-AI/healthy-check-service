package org.fxkc.peis.service;

import org.fxkc.peis.domain.vo.TjHazardFactorsItemVo;
import org.fxkc.peis.domain.bo.TjHazardFactorsItemBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 危害因素必检项目关联Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface ITjHazardFactorsItemService {

    /**
     * 查询危害因素必检项目关联
     */
    TjHazardFactorsItemVo queryById(Long id);

    /**
     * 查询危害因素必检项目关联列表
     */
    TableDataInfo<TjHazardFactorsItemVo> queryPageList(TjHazardFactorsItemBo bo, PageQuery pageQuery);

    /**
     * 查询危害因素必检项目关联列表
     */
    List<TjHazardFactorsItemVo> queryList(TjHazardFactorsItemBo bo);

    /**
     * 新增危害因素必检项目关联
     */
    Boolean insertByBo(TjHazardFactorsItemBo bo);

    /**
     * 修改危害因素必检项目关联
     */
    Boolean updateByBo(TjHazardFactorsItemBo bo);

    /**
     * 校验并批量删除危害因素必检项目关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
