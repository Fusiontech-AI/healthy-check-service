package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckBasicItemVo implements Serializable {

    /**
     * 项目名称
     */
    private String basicItemName;

    /**
     * 检查结果
     */
    private String checkResult;

    /**
     * 检查结果
     */
    private String unit;

    /**
     * 参考值
     */
    private String referenceValue;

    /**
     * 异常标识
     */
    private String abnormalFlag;
}
