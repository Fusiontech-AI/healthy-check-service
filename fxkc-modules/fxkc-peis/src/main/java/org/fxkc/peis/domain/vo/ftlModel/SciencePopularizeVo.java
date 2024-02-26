package org.fxkc.peis.domain.vo.ftlModel;

import lombok.Data;

import java.io.Serializable;

@Data
public class SciencePopularizeVo implements Serializable {
    /**
     * 建议名称
     */
    private String name;
    /**
     * 科普说明
     */
    private String explain;
}
