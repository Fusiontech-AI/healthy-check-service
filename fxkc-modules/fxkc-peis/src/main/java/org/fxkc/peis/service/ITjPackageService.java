package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjPackageAddBo;
import org.fxkc.peis.domain.bo.TjPackageBillBo;
import org.fxkc.peis.domain.bo.TjPackageBo;
import org.fxkc.peis.domain.vo.TjPackageVo;

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
    Boolean insertByBo(TjPackageAddBo bo);

    /**
     * 修改体检套餐
     */
    Boolean updateByBo(TjPackageAddBo bo);

    /**
     * 校验并批量删除体检套餐信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 体检套餐动态算费(可复用)
     */
    TjPackageBillBo dynamicBilling(TjPackageBillBo bo);
}
