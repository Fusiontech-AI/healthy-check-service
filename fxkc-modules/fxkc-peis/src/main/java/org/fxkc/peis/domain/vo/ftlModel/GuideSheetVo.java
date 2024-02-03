package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 指引单数据展示模板
 * @author pw
 */
@Data
public class GuideSheetVo implements Serializable {
    /**
     * 登记日期
     */
    private String registerTime;
    /**
     * 体检日期
     */
    private String healthyCheckTime;

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
     * 手机号
     */
    private String phone;

    /**
     * 联系地址
     */
    private String contactAddress;

    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 工作单位
     */
    private String teamName;

    /**
     * 体检次数
     */
    private String peTimes;

    /**
     * 打印日期
     */
    private String printTime;

    /**
     * 体检地址
     */
    private String tjAddress;

    /**
     * 体检电话
     */
    private String tjPhone;

    /**
     * 体检备注
     */
    private String remark;

    /**
     * 用户照片
     */
    private String userImage;

    /**
     * 条形码
     */
    private String healthyBarCode;

    /**
     * 缴费二维码
     */
    private String payQrCode;

    /**
     * 是否补打 0否1是
     */
    private String isReprint;

    /**
     * 打印人
     */
    private String printUser;

    /**
     * 总检医生
     */
    private String generalReviewDoctor;

    /**
     * 温馨提示
     */
    private String warmTips;

    /**
     * 检查项目详情
     */
    private List<GuideSheetTypeVo> checkItemList;
}
