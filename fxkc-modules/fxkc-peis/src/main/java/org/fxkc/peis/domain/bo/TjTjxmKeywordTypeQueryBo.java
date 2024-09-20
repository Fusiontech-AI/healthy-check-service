package org.fxkc.peis.domain.bo;

import lombok.Builder;
import lombok.Data;

/**
 * 知识库关键词类型查询请求dto
 */
@Data
@Builder
public class TjTjxmKeywordTypeQueryBo {

    /**
     * 关键词类型名称
     */
    private String name;

    /**
     * 启用状态 0启用
     */
    private Integer status;
}
