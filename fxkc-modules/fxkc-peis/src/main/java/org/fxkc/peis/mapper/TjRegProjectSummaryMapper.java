package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjRegProjectSummary;
import org.fxkc.peis.domain.vo.TjRegProjectSummaryVo;

import java.util.List;

/**
 * 体检登记基础项目小结Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
public interface TjRegProjectSummaryMapper extends BaseMapperPlus<TjRegProjectSummary, TjRegProjectSummaryVo> {

    @Select(" select * from tj_reg_project_summary t ${ew.customSqlSegment} ")
    List<TjRegProjectSummaryVo> summaryHistoryList(@Param(Constants.WRAPPER)QueryWrapper<TjRegProjectSummary> eq);
}
