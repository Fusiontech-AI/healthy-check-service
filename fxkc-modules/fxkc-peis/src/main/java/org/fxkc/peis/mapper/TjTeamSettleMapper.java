package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjTeamSettle;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.peis.domain.vo.TjTeamSettleTaskGroupStatisticsVo;
import org.fxkc.peis.domain.vo.TjTeamSettleTaskGroupVo;
import org.fxkc.peis.domain.vo.TjTeamSettleVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检单位结账Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
public interface TjTeamSettleMapper extends BaseMapperPlus<TjTeamSettle, TjTeamSettleVo> {

    Page<TjTeamSettleTaskGroupVo> teamSettleTaskGroupList(Page page, @Param("bo") TjTeamSettleBo bo);

    TjTeamSettleTaskGroupStatisticsVo teamSettleTaskGroupStatistics(@Param("bo") TjTeamSettleBo bo);

    BigDecimal teamSettledAmount(@Param("bo") TjTeamSettleBo bo);

}
