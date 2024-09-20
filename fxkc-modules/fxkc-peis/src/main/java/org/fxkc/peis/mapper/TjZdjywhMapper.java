package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.domain.bo.TjZdjywhQueryBo;
import org.fxkc.peis.domain.vo.TjZdjywhVo;

/**
 * 诊断建议主Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface TjZdjywhMapper extends BaseMapperPlus<TjZdjywh, TjZdjywhVo> {

    Page<TjZdjywh> selectPage(@Param("page") Page<TjZdjywh> page, @Param("bo") TjZdjywhQueryBo bo);
}
