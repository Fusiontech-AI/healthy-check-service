package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.bo.TjRegProjectPositiveBo;
import org.fxkc.peis.domain.vo.TjRegProjectPositiveVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检登记基础项目阳性记录Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
public interface ITjRegProjectPositiveService {

    /**
     * 查询体检登记基础项目阳性记录列表
     */
    List<TjRegProjectPositiveVo> positiveList(TjRegProjectListBo bo);

    /**
     * 新增体检登记基础项目阳性记录
     */
    Boolean insertByBo(TjRegProjectPositiveBo bo);

    /**
     * 修改体检登记基础项目阳性记录
     */
    Boolean updateByBo(TjRegProjectPositiveBo bo);

    /**
     * 校验并批量删除体检登记基础项目阳性记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
