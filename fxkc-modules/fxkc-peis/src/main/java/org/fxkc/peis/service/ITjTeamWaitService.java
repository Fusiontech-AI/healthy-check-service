package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjTeamWaitSwitchGroupBo;
import org.fxkc.peis.domain.vo.*;

import java.util.List;

/**
 * 体检单位待检人员Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
public interface ITjTeamWaitService {

    /**
     * 查询体检单位待检人员详情任务分组树
     */
    List<TjTeamWaitTaskGroupVo> teamSettleWaitTaskGroupTree(Long teamId);

    /**
     * 体检单位待检人员批量换组
     */
    Boolean batchSwitchGroup(TjTeamWaitSwitchGroupBo bo);

}
