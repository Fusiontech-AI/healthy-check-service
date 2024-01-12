package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检基础项目对象 tj_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_basic_project")
public class TjBasicProject extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 基础项目编码
     */
    private String basicProjectCode;

    /**
     * 基础项目名称
     */
    private String basicProjectName;

    /**
     * 基础项目简称
     */
    private String basicSimpleName;

    /**
     * 所属科室id
     */
    private Long ksId;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 结果类型 0数字 1文本
     */
    private String resultType;

    /**
     * 结果获取方式 0手工 1导入
     */
    private String resultGetWay;

    /**
     * 适用性别 0男 1女 2不限
     */
    private String suitSex;

    /**
     * 项目默认值
     */
    private String defaultValue;

    /**
     * 基础项目显示序号
     */
    private Long projectSort;

    /**
     * 进入小结 0是 1否
     */
    private String enterSummary;

    /**
     * 进入报告 0是 1否
     */
    private String enterReport;

    /**
     * 对照lis编码
     */
    private String lisCode;

    /**
     * 对照pacs编码
     */
    private String pacsCode;

    /**
     * 对照his编码
     */
    private String hisCode;

    /**
     * 对照职业病编码
     */
    private String zybCode;

    /**
     * 基础项目状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
