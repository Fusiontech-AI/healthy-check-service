package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegisterPageBo;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjRegisterPageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.vo.TjTaskRegisterExportVo;

import java.util.List;

/**
 * 体检人员登记信息Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
public interface TjRegisterMapper extends BaseMapperPlus<TjRegister, TjRegisterVo> {

    Page<TjRegisterPageVo> selectPage(@Param("bo") TjRegisterPageBo bo,@Param("page") Page<TjRegisterPageVo> page);

    @Select("SELECT check_code_sequence.NEXTVAL FROM DUAL")
    String nextHealthyCode();

    void updateTjRegisterTeamSettleNull(@Param("teamSettleIds") List<Long> teamSettleIds);

    List<TjRegisterVo> getByIds(@Param("regIdList") List<Long> regIdList);

    void updateGuideSheetPrint(@Param("bo") ReportPrintBO bo);

    Page<TjTaskRegisterExportVo> queryTaskRegisterExportById(@Param("page") Page<Object> build, @Param("taskId") Long taskId);
}
