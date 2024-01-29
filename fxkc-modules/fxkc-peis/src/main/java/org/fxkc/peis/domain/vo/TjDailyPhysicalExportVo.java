package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class TjDailyPhysicalExportVo {

    /**
     * 体检团体
     */
    @ExcelProperty("体检团体")
    private String teamName;

    /**
     * 总人数
     */
    @ExcelProperty("总人数")
    private Long totalNum;

    /**
     * 总金额合计
     */
    @ExcelProperty({"金额(元)","总金额合计"})
    private BigDecimal totalAmount;

    /**
     * 平均金额
     */
    @ExcelProperty({"金额(元)","平均金额"})
    private BigDecimal avgAmount;

    /**
     * 个人金额
     */
    @ExcelProperty({"金额(元)","个人金额合计"})
    private BigDecimal totalPersonalAmount;

    /**
     * 团体金额
     */
    @ExcelProperty({"金额(元)","团体金额合计"})
    private BigDecimal totalTeamAmount;

    /**
     * 男性人数
     */
    @ExcelProperty({"性别","男性"})
    private Long manCount;

    /**
     * 女性人数
     */
    @ExcelProperty({"性别","女性"})
    private Long womanCount;

    /**
     * 未回收指引单
     */
    @ExcelProperty({"指引单","未回收"})
    private Long noRecycledGuideSheet;

    /**
     * 已回收指引单
     */
    @ExcelProperty({"指引单","已回收"})
    private Long recycledGuideSheet;

    /**
     * 未总检
     */
    @ExcelProperty({"总检","未总检"})
    private Long noChecked;

    /**
     * 已总检
     */
    @ExcelProperty({"总检","已总检"})
    private Long hasChecked;

    /**
     * 未打印
     */
    @ExcelProperty({"打印报告","未打印"})
    private Long noPrintReport;

    /**
     * 已打印
     */
    @ExcelProperty({"打印报告","已打印"})
    private Long hasPrintReport;

    private String regType;

    private String gender;

    private String guideSheetReceived;

    private String insMark;

    private String reportPrintFlag;

    private BigDecimal teamAmount;

    private BigDecimal personAmount;

    private List<TjDailyPhysicalExportVo> children;

}
