package org.fxkc.peis.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TjOccupationalDictTreeVo {

    /**
     * 名称
     */
    private String value;

    /**
     * 编码
     */
    private String code;

    /**
     * 子类
     */
    private List<TjOccupationalDictTreeVo> children;
}
