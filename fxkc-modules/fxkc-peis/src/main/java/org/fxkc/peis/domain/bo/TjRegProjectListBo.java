package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 体检登记基础项目小结查询请求对象 TjRegProjectSummaryListBo
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
public class TjRegProjectListBo {


    /**
     * 体检登记项目记录主键id
     */
    private Long regItemId;

    /**
     * 体检登记id
     */
    private Long regId;

}
