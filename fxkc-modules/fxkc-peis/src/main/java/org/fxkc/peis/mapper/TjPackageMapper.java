package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.domain.vo.PackageAndProjectVo;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 体检套餐Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface TjPackageMapper extends BaseMapperPlus<TjPackage, TjPackageVo> {

    Page<PackageAndProjectVo> queryPackageAndProjectPages(@Param("page") Page<Object> page,@Param("name")  String name);

    List<PackageAndProjectVo> queryProjectByPackageId(@Param("packageId") String packageId);

    List<TjPackageVo> queryListByIds(@Param("packageList") List<Long> packageList);
}
