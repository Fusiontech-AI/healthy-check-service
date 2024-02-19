package org.fxkc.peis.domain.bo;

import lombok.Data;

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
    private String signBeginDate;

    /**
     * 签订结束日期
     */
    private String signEndDate;

    /**
     * 是否审核(0:是1:否)
     */
    private String isReview;
}
