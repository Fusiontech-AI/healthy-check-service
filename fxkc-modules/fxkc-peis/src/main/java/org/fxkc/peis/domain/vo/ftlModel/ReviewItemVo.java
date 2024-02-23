package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewItemVo implements Serializable {

    /**
     * 复查项目
     */
    private String reviewItemName;

    /**
     * 复查原因
     */
    private String reviewReasons;

    /**
     * 复查时间
     */
    private String reviewTime;
}
