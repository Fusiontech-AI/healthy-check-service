package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjRegBasicProject;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;

import java.util.List;

/**
 * 体检登记基础项目关联Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
public interface TjRegBasicProjectMapper extends BaseMapperPlus<TjRegBasicProject, TjRegBasicProjectVo> {

    @Select("select t1.*,t3.check_doctor_name,t3.check_time from tj_reg_basic_project t1 left join tj_basic_project t2 on t1.basic_project_id=t2.id" +
        " left join  tj_reg_combination_project t3  on t1.reg_item_id = t3.id where t1.reg_item_id =#{id} and t1.del_flag='0' order by t2.project_sort ")
    List<TjRegBasicProjectVo> queryRegBasicProjectList(@Param("id") Long id);

}
