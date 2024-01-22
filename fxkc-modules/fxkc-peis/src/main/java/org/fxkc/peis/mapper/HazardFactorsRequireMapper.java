package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.peis.domain.bo.HazardFactorsRequireSaveBo;
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 危害因素必检项目主Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface HazardFactorsRequireMapper extends BaseMapperPlus<HazardFactorsRequire, HazardFactorsRequireVo> {

    Page<HazardFactorsRequireVo.HazardFactorsRequireQueryVo> hazardFactorsQueryPage(@Param("page") Page<?> page,
                                                                                    @Param("bo") HazardFactorsRequireBo bo);

    long queryIsExistRequiredItem(HazardFactorsRequireSaveBo bo);
}
