package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectListBo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectListVo;
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
     * 查询体检人员综合项目信息
     */
    TjRegCombinationProjectVo queryById(Long id);

    /**
     * 查询体检人员综合项目信息列表
     */
    TableDataInfo<TjRegCombinationProjectListVo> queryPageList(TjRegCombinationProjectListBo bo, PageQuery pageQuery);

    /**
     * 查询体检人员综合项目信息列表
     */
    List<TjRegCombinationProjectVo> queryList(TjRegCombinationProjectBo bo);

    /**
     * 新增体检人员综合项目信息
     */
    Boolean insertByBo(TjRegCombinationProjectBo bo);

    /**
     * 修改体检人员综合项目信息
     */
    Boolean updateByBo(TjRegCombinationProjectBo bo);

    /**
     * 校验并批量删除体检人员综合项目信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean abandonProjects(Collection<Long> ids);

    Boolean restoreProjects(Collection<Long> ids);

    Boolean delayProjects(TjRegCombinationProjectDelayBo delayBo);

    List<GuideSheetItemVo> queryGuideItemByIds(List<Long> regIdList);

    List<TjRegBasicProjectVo> queryRegBasicProjectList(Long id);
}
