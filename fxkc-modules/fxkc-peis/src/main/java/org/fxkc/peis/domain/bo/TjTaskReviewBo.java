package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TjTaskReviewBo {

    /**
     * 任务id(单次审核传)
     */
    private Long id;

    /**
     * 任务id集合(批量审核传)
     */
    private List<Long> idList;

    /**
     *审核结论(0:待审1:通过2:驳回)
     */
    @NotBlank(message = "审核结论不能为空")
    private String reviewResult;
}
