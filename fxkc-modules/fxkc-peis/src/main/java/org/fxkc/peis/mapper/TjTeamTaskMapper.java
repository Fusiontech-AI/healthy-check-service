package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.peis.domain.vo.TjTeamTaskVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.vo.TjTeamWaitTaskGroupVo;

import java.util.List;

/**
 * 团检任务管理Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
public interface TjTeamTaskMapper extends BaseMapperPlus<TjTeamTask, TjTeamTaskVo> {

    @Select("select tj_task_number_seq.nextval from dual")
    String queryTaskNumber();

    List<TjTeamWaitTaskGroupVo> teamSettleWaitTaskGroupTree(@Param("teamId") Long teamId);

}
