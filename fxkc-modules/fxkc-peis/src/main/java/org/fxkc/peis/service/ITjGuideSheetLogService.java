package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjGuideSheetLog;
import org.fxkc.peis.domain.vo.TjGuideSheetLogVo;
import org.fxkc.peis.domain.bo.TjGuideSheetLogBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 导检单回收记录Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface ITjGuideSheetLogService {

    /**
     * 查询导检单回收记录
     */
    TjGuideSheetLogVo queryById(Long id);

    /**
     * 查询导检单回收记录列表
     */
    TableDataInfo<TjGuideSheetLogVo> queryPageList(TjGuideSheetLogBo bo, PageQuery pageQuery);

    /**
     * 查询导检单回收记录列表
     */
    List<TjGuideSheetLogVo> queryList(TjGuideSheetLogBo bo);

    /**
     * 新增导检单回收记录
     */
    Boolean insertByBo(TjGuideSheetLogBo bo);

    /**
     * 修改导检单回收记录
     */
    Boolean updateByBo(TjGuideSheetLogBo bo);

    /**
     * 校验并批量删除导检单回收记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
