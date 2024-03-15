package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.TjTjksBo;
import org.fxkc.peis.domain.vo.TjTjksBasicNameVo;
import org.fxkc.peis.domain.vo.TjTjksVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检科室Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
public interface ITjTjksService {

    /**
     * 查询体检科室
     */
    TjTjksVo queryById(Long id);

    /**
     * 查询体检科室列表
     */
    TableDataInfo<TjTjksVo> queryPageList(TjTjksBo bo, PageQuery pageQuery);

    /**
     * 查询体检科室列表
     */
    List<TjTjksVo> queryList(TjTjksBo bo);

    /**
     * 新增体检科室
     */
    Boolean insertByBo(TjTjksBo bo);

    /**
     * 修改体检科室
     */
    Boolean updateByBo(TjTjksBo bo);

    /**
     * 校验并批量删除体检科室信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    String getKsCode();

    List<TjTjksBasicNameVo> queryTjKsListByBasicName(String basicProjectName);
}
