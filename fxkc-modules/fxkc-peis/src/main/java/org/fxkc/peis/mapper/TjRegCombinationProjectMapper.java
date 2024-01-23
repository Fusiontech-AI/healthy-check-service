package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectListBo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 体检人员综合项目信息Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
public interface TjRegCombinationProjectMapper extends BaseMapperPlus<TjRegCombinationProject, TjRegCombinationProjectVo> {

    Page<TjRegCombinationProjectListVo> selectPage(@Param("page")Page<TjRegCombinationProjectListVo> page, @Param("bo")TjRegCombinationProjectListBo bo);
}
