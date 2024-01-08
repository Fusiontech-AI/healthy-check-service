package org.fxkc.system.domain.convert;

import io.github.linpeilie.BaseMapper;
import org.fxkc.system.api.domain.vo.RemoteTenantVo;
import org.fxkc.system.domain.vo.SysTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 租户转换器
 * @author zhujie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysTenantVoConvert extends BaseMapper<SysTenantVo, RemoteTenantVo> {

}
