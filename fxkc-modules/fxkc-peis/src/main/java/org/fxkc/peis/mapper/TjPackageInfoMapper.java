package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjPackageInfo;
import org.fxkc.peis.domain.bo.TjPackageInfoBo;
import org.fxkc.peis.domain.vo.TjPackageInfoVo;

/**
 * 体检组合项目详细信息Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface TjPackageInfoMapper extends BaseMapperPlus<TjPackageInfo, TjPackageInfoVo> {

    Page<TjPackageInfoVo> queryPackageInfoPages(@Param("page") Page<Object> page, @Param("bo") TjPackageInfoBo bo);

}
