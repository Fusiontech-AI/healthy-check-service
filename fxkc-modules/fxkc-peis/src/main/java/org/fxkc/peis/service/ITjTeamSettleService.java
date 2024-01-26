package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjTeamTaskDiscountSealBo;
import org.fxkc.peis.domain.vo.TjTeamSettleVo;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.List;

/**
 * 体检单位结账Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
public interface ITjTeamSettleService {

    /**
     * 查询体检单位结账
     */
    TjTeamSettleVo queryById(Long id);

    /**
     * 查询体检单位结账列表
     */
    TableDataInfo<TjTeamSettleVo> queryPageList(TjTeamSettleBo bo, PageQuery pageQuery);

    /**
     * 查询体检单位结账列表
     */
    List<TjTeamSettleVo> queryList(TjTeamSettleBo bo);

    /**
     * 新增体检单位结账
     */
    Boolean insertByBo(TjTeamSettleBo bo);

    /**
     * 体检单位结账开票
     */
    Boolean teamInvoice(TjTeamSettleBo bo);

    /**
     * 体检单位结账发票作废
     */
    Boolean teamInvalidInvoice(TjTeamSettleBo bo);

    /**
     * 体检单位结账作废
     */
    Boolean teamInvalidSettle(TjTeamSettleBo bo);

    /**
     * 体检单位结账任务折扣
     */
    Boolean teamTaskDiscount(TjTeamTaskDiscountSealBo bo);

    /**
     * 体检单位结账封账
     */
    Boolean teamSettleSeal(TjTeamTaskDiscountSealBo bo);

    /**
     * 体检单位结账解除封账
     */
    Boolean teamSettleUnseal(TjTeamTaskDiscountSealBo bo);

    /**
     * 体检单位结账审核通过
     */
    Boolean teamSettleCheckPass(TjTeamSettleBo bo);

    /**
     * 体检单位结账审核驳回
     */
    Boolean teamSettleCheckReject(TjTeamSettleBo bo);

    /**
     * 校验并批量删除体检单位结账信息
     */
    Boolean deleteWithValidByIds(TjTeamSettleBo bo, Boolean isValid);
}
