package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.domain.bo.TjPackageBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 体检套餐Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjPackageService {

    /**
     * 查询体检套餐
     */
    TjPackageVo queryById(Long id);

    /**
     * 查询体检套餐列表
     */
    TableDataInfo<TjPackageVo> queryPageList(TjPackageBo bo, PageQuery pageQuery);

    /**
     * 查询体检套餐列表
     */
    List<TjPackageVo> queryList(TjPackageBo bo);

    /**
     * 新增体检套餐
     */
    Boolean insertByBo(TjPackageBo bo);

    /**
     * 修改体检套餐
     */
    Boolean updateByBo(TjPackageBo bo);

    /**
     * 校验并批量删除体检套餐信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
