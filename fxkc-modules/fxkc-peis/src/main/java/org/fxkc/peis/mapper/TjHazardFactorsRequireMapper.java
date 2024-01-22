package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjHazardFactorsRequire;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireBo;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireSaveBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 危害因素必检项目主Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface TjHazardFactorsRequireMapper extends BaseMapperPlus<TjHazardFactorsRequire, TjHazardFactorsRequireVo> {

    Page<TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo> hazardFactorsQueryPage(@Param("page") Page<?> page,
                                                                                      @Param("bo") TjHazardFactorsRequireBo bo);

    long queryIsExistRequiredItem(TjHazardFactorsRequireSaveBo bo);
}
