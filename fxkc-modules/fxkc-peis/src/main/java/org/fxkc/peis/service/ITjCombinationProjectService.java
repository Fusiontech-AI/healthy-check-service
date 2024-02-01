package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjCombinationProjectAddBo;
import org.fxkc.peis.domain.bo.TjCombinationProjectBo;
import org.fxkc.peis.domain.bo.TjCompulsoryInspectionProjectBo;
import org.fxkc.peis.domain.bo.TjOtherCompulsoryInspectionBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjCombinationProjectVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检组合项目Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjCombinationProjectService {

    /**
     * 查询体检组合项目
     */
    TjCombinationProjectVo queryById(Long id);

    /**
     * 查询体检组合项目列表
     */
    TableDataInfo<TjCombinationProjectListVo> queryPageList(TjCombinationProjectBo bo, PageQuery pageQuery);

    /**
     * 查询体检组合项目列表
     */
    List<TjCombinationProjectVo> queryList(TjCombinationProjectBo bo);

    /**
     * 新增体检组合项目
     */
    Boolean insertByBo(TjCombinationProjectAddBo bo);

    /**
     * 修改体检组合项目
     */
    Boolean updateByBo(TjCombinationProjectAddBo bo);

    /**
     * 校验并批量删除体检组合项目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<TjCombinationProjectListVo> queryCompulsoryInspectionProject(TjCompulsoryInspectionProjectBo bo);

    List<TjCombinationProjectListVo> queryOtherCompulsoryInspection(TjOtherCompulsoryInspectionBo bo);

    String selectCombinationNameById(Long id);
}
