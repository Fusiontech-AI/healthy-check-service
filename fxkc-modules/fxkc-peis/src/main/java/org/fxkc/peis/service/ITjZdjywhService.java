package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.domain.bo.TjZdjywhBo;
import org.fxkc.peis.domain.bo.TjZdjywhQueryBo;
import org.fxkc.peis.domain.vo.TjZdjywhVo;

import java.util.Collection;
import java.util.List;

/**
 * 诊断建议主Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface ITjZdjywhService {

    /**
     * 查询诊断建议主
     */
    TjZdjywhVo queryById(Long id);

    /**
     * 查询诊断建议主列表
     */
    TableDataInfo<TjZdjywh> queryPageList(TjZdjywhQueryBo bo, PageQuery pageQuery);

    /**
     * 查询诊断建议主列表
     */
    List<TjZdjywhVo> queryList(TjZdjywhBo bo);

    /**
     * 新增诊断建议主
     */
    Boolean insertByBo(TjZdjywhBo bo);

    /**
     * 修改诊断建议主
     */
    Boolean updateByBo(TjZdjywhBo bo);

    /**
     * 校验并批量删除诊断建议主信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
