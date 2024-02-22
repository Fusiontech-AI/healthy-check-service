package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjTeamSettle;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.peis.domain.vo.TjTeamSettleAmountStatisticsVo;
import org.fxkc.peis.domain.vo.TjTeamSettleTaskGroupStatisticsVo;
import org.fxkc.peis.domain.vo.TjTeamSettleTaskGroupVo;
import org.fxkc.peis.domain.vo.TjTeamSettleVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 体检单位结账Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
public interface TjTeamSettleMapper extends BaseMapperPlus<TjTeamSettle, TjTeamSettleVo> {

    Page<TjTeamSettleTaskGroupVo> teamSettleTaskGroupList(Page page, @Param("bo") TjTeamSettleBo bo);

    TjTeamSettleTaskGroupVo teamSettleTaskNoGroup(@Param("bo") TjTeamSettleBo bo);

    TjTeamSettleTaskGroupStatisticsVo teamSettleTaskGroupStatistics(@Param("bo") TjTeamSettleBo bo);

    TjTeamSettleAmountStatisticsVo teamSettledAmount(@Param("bo") TjTeamSettleBo bo);

}
