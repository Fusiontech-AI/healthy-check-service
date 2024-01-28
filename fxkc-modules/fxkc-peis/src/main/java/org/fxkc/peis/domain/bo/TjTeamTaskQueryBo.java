package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TjTeamTaskQueryBo {

    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 签订开始日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date signBeginDate;

    /**
     * 签订结束日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date signEndDate;

    /**
     * 是否审核(0:是1:否)
     */
    private String isReview;
}
