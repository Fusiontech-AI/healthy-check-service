package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 诊断建议主业务对象 TjZdjywhQueryBo
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
public class TjZdjywhQueryBo {
    /**
     * 主要诊断
     */
    private String zyzd;

    /**
     * 重要程度(1正常2一般3重要4非常重要)
     */
    private String importance;
    /**
     * 体检科室id
     */
    private Long ksId;

}
