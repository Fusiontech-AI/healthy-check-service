package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.AmountCalculationVo;
import org.fxkc.peis.domain.vo.PackageAndProjectVo;
import org.fxkc.peis.domain.vo.TjPackageVo;

import java.math.BigDecimal;
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

    AmountCalculationVo commonDynamicBilling(AmountCalculationBo bo);

    BigDecimal getReceivableAmountByDiscount(BigDecimal standardAmount, BigDecimal discount);

    BigDecimal getDiscountByReceivableAmount(BigDecimal standardAmount,BigDecimal receivableAmount);

    void calCulPayType(List<AmountCalculationItemBo> addItems, List<AmountCalculationItemBo> haveItems,TjTeamGroup tjTeamGroup);

    void fillSingle(AmountCalculationItemBo bo);

    TableDataInfo<PackageAndProjectVo> queryPackageAndProjectPages(String name, PageQuery pageQuery);
}
