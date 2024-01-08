package org.fxkc.system.domain.convert;

import io.github.linpeilie.BaseMapper;
import org.fxkc.system.api.domain.bo.RemoteSocialBo;
import org.fxkc.system.domain.bo.SysSocialBo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 社交数据转换器
 *
 * @author Michelle.Chung
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysSocialBoConvert extends BaseMapper<RemoteSocialBo, SysSocialBo> {
}
