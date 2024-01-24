package org.fxkc.peis.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TjHazardFactorsTreeVo {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String value;

    /**
     * 小类编码
     */
    private String sortCode;

    /**
     * 子类
     */
    private List<TjHazardFactorsTreeVo> children;
}
