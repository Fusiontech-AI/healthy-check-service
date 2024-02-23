package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;

/**
 * 指引单数据展示模板
 * @author pw
 */
@Data
public class GuideSheetItemVo implements Serializable {

    /**
     * 记录Id
     */
    private Long regId;

    /**
     * 检查类型名称
     */
    private String checkType;

    /**
     * 检查项目
     */
    private String itemName;

    /**
     * 签名
     */
    private String signature;

    /**
     * 提示信息
     */
    private String hint;

    /**
     * 科室地址
     */
    private String deptAddress;
}
