package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjCombinationProject;
import org.fxkc.peis.domain.bo.TjCombinationProjectInfoItemBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectVo;

import java.util.List;

/**
 * 体检组合项目Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface TjCombinationProjectMapper extends BaseMapperPlus<TjCombinationProject, TjCombinationProjectVo> {
    List<TjCombinationProjectInfoItemBo> getBasicProjectBycombinIds(@Param("combinIds")List<Long> combinIds);
}
