package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjTemplateExtend;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;

import java.util.List;

/**
 * 体检报告扩展Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Mapper
public interface TjTemplateExtendMapper extends BaseMapperPlus<TjTemplateExtend, TjTemplateExtendVo> {

    List<TjTemplateExtendVo> getListByInfoId(@Param("templateIdList") List<Long> templateIdList);
}
