package org.fxkc.peis.domain.bo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 知识库关键词库绑定查询请求dto
 */
@Data
@Builder
public class TjTjxmKeywordBoundingBo {

    /**
     * 诊断建议id
     */
    private Long zdjyId;

    /**
     * 关键词库Ids
     */
    private List<String> keywordIds;
}
