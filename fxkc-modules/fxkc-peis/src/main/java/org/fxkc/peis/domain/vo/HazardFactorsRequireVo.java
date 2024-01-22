package org.fxkc.peis.domain.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.Accessors;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.HazardFactorsRequire;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
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
@AutoMapper(target = HazardFactorsRequire.class)
@Accessors(chain = true)
public class HazardFactorsRequireVo implements Serializable {

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
    public static class HazardFactorsRequireQueryVo extends HazardFactorsRequire{

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
