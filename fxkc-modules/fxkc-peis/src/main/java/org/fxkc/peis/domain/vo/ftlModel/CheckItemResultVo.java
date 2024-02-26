package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckItemResultVo implements Serializable {

    /**
     * 组合项目名称
     */
    private String itemName;

    /**
     * 显示类型
     */
    private String showType;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    private String checkType;

    /**
     * 小结
     */
    private String summary;

    /**
     * 检查医生
     */
    private String checkDoctor;

    /**
     * 检查日期
     */
    private String checkTime;

    /**
     * 检查状态
     */
    private String checkStatus;

    /**
     * 项目明细结果
     */
    private List<CheckBasicItemVo> basicItemList;
}
