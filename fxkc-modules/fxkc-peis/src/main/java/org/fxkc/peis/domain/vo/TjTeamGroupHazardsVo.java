package org.fxkc.peis.domain.vo;

import io.github.linpeilie.annotations.AutoMappers;
import org.fxkc.peis.domain.TjTeamGroupHazards;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.bo.TjRegisterZybHazardBo;

import java.io.Serial;
import java.io.Serializable;


/**
 * 单位分组危害因素视图对象 tj_team_group_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMappers({@AutoMapper(target = TjTeamGroupHazards.class), @AutoMapper(target = TjRegisterZybHazardBo.class)})
public class TjTeamGroupHazardsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 危害因素编码
     */
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    private String hazardFactorsOther;



}
