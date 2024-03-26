package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjYxType;
import org.fxkc.peis.domain.vo.TjYxTypeVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 体检阳性分类Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
public interface TjYxTypeMapper extends BaseMapperPlus<TjYxType, TjYxTypeVo> {

    @Select("select *  from tj_yx_type t start with t.id=#{id} connect by prior t.id =  t.parent_id")
    List<TjYxType> selectAllType(@Param("id") Long id);
}
