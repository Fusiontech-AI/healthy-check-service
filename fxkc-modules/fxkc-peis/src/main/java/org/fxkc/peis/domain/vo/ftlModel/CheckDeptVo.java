package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckDeptVo implements Serializable {

    /**
     * 检查科室名称
     */
    private String deptName;

    /**
     * 检查科室名称
     */
    private List<CheckItemResultVo> checkItemList;
}
