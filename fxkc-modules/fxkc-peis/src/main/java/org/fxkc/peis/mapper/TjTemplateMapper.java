package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjTemplate;
import org.fxkc.peis.domain.bo.template.TjTemplateQueryBO;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;

import java.util.List;

/**
 * 体检报告维护Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Mapper
public interface TjTemplateMapper extends BaseMapperPlus<TjTemplate, TjTemplateVo> {

    List<TjTemplateVo> getList(@Param("bo") TjTemplateQueryBO bo);

    List<TjTemplateVo> getValidTemplate(@Param("reportType")String reportType);
}
