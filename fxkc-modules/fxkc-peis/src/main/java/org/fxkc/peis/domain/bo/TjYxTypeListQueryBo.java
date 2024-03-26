package org.fxkc.peis.domain.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:阳性分类List查询请求Bo
 * @author chenjunbai
 * @since 2022-10-14
 */
@Data
@Builder
public class TjYxTypeListQueryBo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 分类级别 1,2
         */
        private String level;

        /**
         * 分类父id
         */
        private Long parentId;

}
