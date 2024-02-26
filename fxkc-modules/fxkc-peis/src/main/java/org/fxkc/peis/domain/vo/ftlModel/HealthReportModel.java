package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HealthReportModel implements Serializable {
    /**
     * 体检号
     */
    private String healthyCheckCode;

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
     * 工作单位
     */
    private String teamName;

    /**
     * 体检日期
     */
    private String healthyCheckTime;

    /**
     * 体检电话
     */
    private String tjPhone;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 总检综述
     */
    private String peConclusion;

    /**
     * 结论与建议
     */
    private String peAdvice;

    /**
     * 总检医生
     */
    private String generalReviewDoctor;

    /**
     * 总检时间
     */
    private String generalReviewTime;

    /**
     * 科普说明
     */
    private List<SciencePopularizeVo> sciencePopularizeList;

    /**
     * 检查结果
     */
    private List<CheckItemResultVo> checkItemList;

    /**
     * pdf渲染页
     */
    private List<String> pdfList;

    /**
     * 复查项目
     */
    private List<ReviewItemVo> reviewItem;

    /**
     * 检查项目
     */
    private List<String> itemDeptList;

    /**
     * 条形码
     */
    private String healthyBarCode;

    /**
     * 医院图标--首页显示
     */
    private String hospitalHomeIcon;

    /**
     * 页眉图标
     */
    private String hospitalIcon;
}
