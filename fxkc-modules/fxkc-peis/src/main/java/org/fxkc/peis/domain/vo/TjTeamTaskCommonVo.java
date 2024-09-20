package org.fxkc.peis.domain.vo;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.fxkc.peis.domain.TjTeamGroup;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 团检分组信息视图对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelIgnoreUnannotated
public class TjTeamTaskCommonVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 分组列表
     */
    private List<TjTeamGroupVo> groupList;

}
