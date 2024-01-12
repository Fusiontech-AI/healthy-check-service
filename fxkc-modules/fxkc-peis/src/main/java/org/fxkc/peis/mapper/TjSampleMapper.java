package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjSample;
import org.fxkc.peis.domain.vo.TjSampleInfoListVo;
import org.fxkc.peis.domain.vo.TjSampleVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 体检样本Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface TjSampleMapper extends BaseMapperPlus<TjSample, TjSampleVo> {

    @Select("select t1.combin_project_id,t2.combin_project_code,t2.combin_project_name from tj_sample_info t1 left join tj_combination_project t2 on t1.combin_project_id=t2.id where t1.del_flag='0' and t1.sample_id = #{id}")
    List<TjSampleInfoListVo> getCombinProjectBySampleId(@Param("id") Long id);

}
