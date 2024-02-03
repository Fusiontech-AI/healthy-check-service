package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjTemplateExtendBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检报告扩展Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
public interface ITjTemplateExtendService {

    /**
     * 查询体检报告扩展
     */
    TjTemplateExtendVo queryById(String id);

    /**
     * 查询体检报告扩展列表
     */
    TableDataInfo<TjTemplateExtendVo> queryPageList(TjTemplateExtendBo bo, PageQuery pageQuery);

    /**
     * 查询体检报告扩展列表
     */
    List<TjTemplateExtendVo> queryList(TjTemplateExtendBo bo);

    /**
     * 新增体检报告扩展
     */
    Boolean insertByBo(TjTemplateExtendBo bo);

    /**
     * 修改体检报告扩展
     */
    Boolean updateByBo(TjTemplateExtendBo bo);

    /**
     * 校验并批量删除体检报告扩展信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    List<TjTemplateExtendVo> getListByInfoId(List<Long> infoIdList);
}
