package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjRegProjectSummaryBo;
import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.vo.TjRegProjectSummaryVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检登记基础项目小结Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
public interface ITjRegProjectSummaryService {

    /**
     * 体检登记基础项目小结list信息查询
     */
    List<TjRegProjectSummaryVo> summaryList(TjRegProjectListBo bo);

    /**
     * 新增体检登记基础项目小结
     */
    Boolean insertByBo(TjRegProjectSummaryBo bo);

    /**
     * 修改体检登记基础项目小结
     */
    Boolean updateByBo(TjRegProjectSummaryBo bo);

    /**
     * 校验并批量删除体检登记基础项目小结信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
