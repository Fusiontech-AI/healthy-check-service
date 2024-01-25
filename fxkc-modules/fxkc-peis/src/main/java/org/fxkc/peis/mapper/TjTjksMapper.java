package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjTjks;
import org.fxkc.peis.domain.vo.TjTjksVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 体检科室Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
public interface TjTjksMapper extends BaseMapperPlus<TjTjks, TjTjksVo> {

    @Select("SELECT tj_tjks_sequence.NEXTVAL FROM DUAL")
    String nextTjKsCode();
}
