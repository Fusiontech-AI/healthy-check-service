package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;
import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 职业病字典Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
public interface ITjOccupationalDictService {

    /**
     * 查询职业病字典
     */
    TjOccupationalDictVo queryById(Long id);

    /**
     * 查询职业病字典列表
     */
    TableDataInfo<TjOccupationalDictVo> queryPageList(TjOccupationalDictBo bo, PageQuery pageQuery);

    /**
     * 查询职业病字典列表
     */
    List<TjOccupationalDictVo> queryList(TjOccupationalDictBo bo);

    /**
     * 新增职业病字典
     */
    Boolean insertByBo(TjOccupationalDictBo bo);

    /**
     * 修改职业病字典
     */
    Boolean updateByBo(TjOccupationalDictBo bo);

    /**
     * 校验并批量删除职业病字典信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
