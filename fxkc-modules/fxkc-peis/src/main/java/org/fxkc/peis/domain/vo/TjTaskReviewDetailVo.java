package org.fxkc.peis.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjTeamTask;

@Data
@AutoMapper(target = TjTeamTask.class)
public class TjTaskReviewDetailVo {

    /**
     * 任务id
     */
    private Long id;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 单位名称
     */
    @Translation(type = TransConstant.TEAM_ID_TO_NAME, mapper = "teamId")
    private String teamName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    private String physicalType;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 体检联系人电话
     */
    private String contactPhone;

    /**
     * 销售负责人
     */
    private String saleHead;

    /**
     * 编制人
     */
    private String preparedName;

    /**
     * 审核状态0:已审1:待审
     */
    private String isReview;
}
