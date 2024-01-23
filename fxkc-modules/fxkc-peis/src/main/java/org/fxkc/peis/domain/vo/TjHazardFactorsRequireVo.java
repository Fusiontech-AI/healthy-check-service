package org.fxkc.peis.domain.vo;

import lombok.experimental.Accessors;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjHazardFactorsRequire;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 危害因素必检项目主视图对象 hazard_factors_require
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjHazardFactorsRequire.class)
@Accessors(chain = true)
public class TjHazardFactorsRequireVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 危害因素必检项目分页列表
     */
    private TableDataInfo<HazardFactorsRequireQueryVo> pageVo;

    /**
     * 危害因素其他
     */
    private HazardFactorsRequireQueryVo vo;

    @Data
    public static class HazardFactorsRequireQueryVo extends TjHazardFactorsRequire {

        /**
         * 在岗状态名称
         */
        private String dutyStatusName;

        /**
         * 必检项目
         */
        private String itemName;

        /**
         * 必检项目集合
         */
        private List<ItemBody> itemList;

        /**
         * 更新人名称
         */
        @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "updateBy")
        private String updateByName;
    }

    @Data
    public static class ItemBody {

        /**
         * 项目id
         */
        private Long itemId;

        /**
         * 名称
         */
        private String name;
    }

}
