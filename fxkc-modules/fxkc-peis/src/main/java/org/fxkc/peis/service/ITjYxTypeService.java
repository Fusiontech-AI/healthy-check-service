package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjYxType;
import org.fxkc.peis.domain.bo.TjYxTypeBo;
import org.fxkc.peis.domain.bo.TjYxTypeListQueryBo;
import org.fxkc.peis.domain.vo.TjYxTypeVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检阳性分类Service接口
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
public interface ITjYxTypeService {

    /**
     * 查询体检阳性分类
     */
    TjYxTypeVo queryById(Long id);

    /**
     * 查询体检阳性分类列表
     */
    TableDataInfo<TjYxTypeVo> queryPageList(TjYxTypeBo bo, PageQuery pageQuery);

    /**
     * 查询体检阳性分类列表
     */
    List<TjYxTypeVo> queryList(TjYxTypeBo bo);

    /**
     * 新增体检阳性分类
     */
    Boolean insertByBo(TjYxTypeBo bo);

    /**
     * 修改体检阳性分类
     */
    Boolean updateByBo(TjYxTypeBo bo);

    /**
     * 校验并批量删除体检阳性分类信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<TjYxType> getTjYxTypedList(TjYxTypeListQueryBo bo);
}
