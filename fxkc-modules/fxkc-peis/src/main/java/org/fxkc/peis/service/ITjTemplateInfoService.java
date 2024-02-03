package org.fxkc.peis.service;

import org.fxkc.peis.domain.vo.TjTemplateInfoVo;
import org.fxkc.peis.domain.bo.TjTemplateInfoBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检报告模板Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
public interface ITjTemplateInfoService {

    /**
     * 查询体检报告模板
     */
    TjTemplateInfoVo queryById(String id);

    /**
     * 查询体检报告模板列表
     */
    TableDataInfo<TjTemplateInfoVo> queryPageList(TjTemplateInfoBo bo, PageQuery pageQuery);

    /**
     * 查询体检报告模板列表
     */
    List<TjTemplateInfoVo> queryList(TjTemplateInfoBo bo);

    /**
     * 新增体检报告模板
     */
    Boolean insertByBo(TjTemplateInfoBo bo);

    /**
     * 修改体检报告模板
     */
    Boolean updateByBo(TjTemplateInfoBo bo);

    /**
     * 校验并批量删除体检报告模板信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

}
