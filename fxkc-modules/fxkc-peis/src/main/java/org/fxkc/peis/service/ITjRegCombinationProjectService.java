package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetItemVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检人员综合项目信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
public interface ITjRegCombinationProjectService {

    /**
     * 校验并批量删除体检人员综合项目信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean abandonProjects(Collection<Long> ids);

    Boolean restoreProjects(Collection<Long> ids);

    Boolean delayProjects(TjRegCombinationProjectDelayBo delayBo);

    List<GuideSheetItemVo> queryGuideItemByIds(List<Long> regIdList);

    List<TjRegBasicProjectVo> queryRegBasicProjectList(Long id);

    List<TjRegCombinationProjectVo> queryRegCombinProjectList(Long id);
}
