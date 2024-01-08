package org.fxkc.auth.domain.convert;

import io.github.linpeilie.BaseMapper;
import org.fxkc.auth.domain.vo.TenantListVo;
import org.fxkc.system.api.domain.vo.RemoteTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 租户vo转换器
 * @author zhujie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TenantVoConvert extends BaseMapper<RemoteTenantVo, TenantListVo> {

}
