package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.List;

@Data
public class TjCompulsoryInspectionProjectBo extends PageQuery {


    /**
     * 必检基础项目id
     */
    @NotEmpty(message = "必检基础项目不能为空")
    private List<Long> itemIdList;

    /**
     * 组合项目名称
     */
    private String combinProjectName;

}
