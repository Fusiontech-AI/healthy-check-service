package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 知识库关键词库查询请求dto
 */
@Data
public class TjTjxmKeywordQueryBo {

    /**
     * 关键词
     */
    private String name;

    /**
     * 启用状态 0启用
     */
    private Integer status;

    /**
     * 关键词类型Id
     */
    private String keyTypeId;
}
