package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjRecordLogBo;
import org.fxkc.peis.domain.vo.TjRecordLogVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检操作记录日志Service接口
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
public interface ITjRecordLogService {

    /**
     * 查询体检操作记录日志
     */
    TjRecordLogVo queryById(Long id);

    /**
     * 查询体检操作记录日志列表
     */
    TableDataInfo<TjRecordLogVo> queryPageList(TjRecordLogBo bo, PageQuery pageQuery);

    /**
     * 查询体检操作记录日志列表
     */
    List<TjRecordLogVo> queryList(TjRecordLogBo bo);

    /**
     * 新增体检操作记录日志
     */
    Boolean insertByBo(TjRecordLogBo bo);

    /**
     * 修改体检操作记录日志
     */
    Boolean updateByBo(TjRecordLogBo bo);

    /**
     * 校验并批量删除体检操作记录日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
