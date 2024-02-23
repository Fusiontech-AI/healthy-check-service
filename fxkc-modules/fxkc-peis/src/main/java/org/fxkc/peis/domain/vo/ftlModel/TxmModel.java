package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;

@Data
public class TxmModel implements Serializable {

    /**
     * 记录
     */
    private String id;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 管类型
     */
    private String tubeType;

    /**
     * 团检/个检
     */
    private String regType;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private String age;

    /**
     * 是否补打
     */
    private String isPatch;

    /**
     * 是否补打
     */
    private String barCode;
}
