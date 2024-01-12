package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjPackageInfo;
import org.fxkc.peis.domain.vo.TjPackageInfoVo;
import org.fxkc.peis.domain.bo.TjPackageInfoBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 体检组合项目详细信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface ITjPackageInfoService {

    /**
     * 查询体检组合项目详细信息
     */
    TjPackageInfoVo queryById(Long id);

    /**
     * 查询体检组合项目详细信息列表
     */
    TableDataInfo<TjPackageInfoVo> queryPageList(TjPackageInfoBo bo, PageQuery pageQuery);

    /**
     * 查询体检组合项目详细信息列表
     */
    List<TjPackageInfoVo> queryList(TjPackageInfoBo bo);

    /**
     * 新增体检组合项目详细信息
     */
    Boolean insertByBo(TjPackageInfoBo bo);

    /**
     * 修改体检组合项目详细信息
     */
    Boolean updateByBo(TjPackageInfoBo bo);

    /**
     * 校验并批量删除体检组合项目详细信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
