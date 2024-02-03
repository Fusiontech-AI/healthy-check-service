package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjTemplateInfo;
import org.fxkc.peis.domain.vo.TjTemplateInfoVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;

import java.util.List;

/**
 * 体检报告模板Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Mapper
public interface TjTemplateInfoMapper extends BaseMapperPlus<TjTemplateInfo, TjTemplateInfoVo> {

    List<TjTemplateVo> getValidTemplate(@Param("tenantId") String tenantId);
}
