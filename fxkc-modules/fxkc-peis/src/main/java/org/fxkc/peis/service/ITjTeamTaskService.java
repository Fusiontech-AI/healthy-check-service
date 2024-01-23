package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjTeamTaskBo;
import org.fxkc.peis.domain.bo.TjTeamTaskQueryBo;
import org.fxkc.peis.domain.bo.VerifyGroupBo;
import org.fxkc.peis.domain.bo.VerifyGroupPackageBo;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.vo.TjTeamTaskDetailVo;
import org.fxkc.peis.domain.vo.TjTeamTaskVo;
import org.fxkc.peis.domain.vo.VerifyMessageVo;

import java.util.Collection;
import java.util.List;

/**
 * 团检任务管理Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
public interface ITjTeamTaskService {

    /**
     * 查询团检任务管理
     */
    TjTeamTaskDetailVo queryById(Long id);

    /**
     * 查询团检任务管理列表
     */
    TableDataInfo<TjTeamTaskVo> queryPageList(TjTeamTaskQueryBo bo, PageQuery pageQuery);

    /**
     * 查询团检任务管理列表
     */
    List<TjTeamTaskVo> queryList(TjTeamTaskQueryBo bo);

    /**
     * 新增团检任务管理
     */
    List<TjTeamGroupVo> insertByBo(TjTeamTaskBo bo);

    /**
     * 修改团检任务管理
     */
    List<TjTeamGroupVo> updateByBo(TjTeamTaskBo bo);

    /**
     * 校验并批量删除团检任务管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    VerifyMessageVo verifyGroupData(List<VerifyGroupBo> list);

    VerifyMessageVo verifyGroupPackageData(List<VerifyGroupPackageBo> list);
}
