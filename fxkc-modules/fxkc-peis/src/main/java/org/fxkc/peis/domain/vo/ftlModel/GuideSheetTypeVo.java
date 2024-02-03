package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 指引单数据展示模板
 * @author pw
 */
@Data
public class GuideSheetTypeVo implements Serializable {

    /**
     * 检查类型名称
     */
    private String checkTypeName;

    /**
     * 检查项目
     */
    private List<GuideSheetItemVo> itemList;
}
