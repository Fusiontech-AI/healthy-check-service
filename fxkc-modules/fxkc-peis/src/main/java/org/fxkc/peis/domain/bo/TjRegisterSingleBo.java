package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @description: 体检人员单体查询Bo
 * @author: zry
 * @date: 2024-01-24 10:04
 **/
@Data
public class TjRegisterSingleBo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 体检号
     */
    private String healthyCheckCode;
}
