package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectListBo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetItemVo;

import java.util.List;

/**
 * 体检人员综合项目信息Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
public interface TjRegCombinationProjectMapper extends BaseMapperPlus<TjRegCombinationProject, TjRegCombinationProjectVo> {

    Page<TjRegCombinationProjectListVo> selectPage(@Param("page")Page<TjRegCombinationProjectListVo> page, @Param("bo")TjRegCombinationProjectListBo bo);

    List<GuideSheetItemVo> queryGuideItemByIds(@Param("regIdList")List<Long> regIdList);

    @Select("select t1.* from tj_reg_combination_project t1 left join tj_combination_project t2 on t1.combination_project_id=t2.id where t1.del_flag='0' and t1.register_id= #{id} order by t2.project_sort")
    List<TjRegCombinationProjectVo> queryRegCombinProjectList(@Param("id") Long id);
}
