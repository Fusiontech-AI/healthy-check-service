package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 体检档案业务对象 tj_archives
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
public class TjArchivesBo {

    /**
     * 是否自动合并(0:是)
     */
    private String isAuto;

    /**
     * 选中数据
     */
    @NotEmpty(message = "所选档案数据不能为空")
    private List<TjArchivesData> dataList;

    @Data
    public static class TjArchivesData {

        /**
         * 档案号
         */
        @NotBlank(message = "档案号不能为空")
        private String recordCode;

        /**
         * 是否选中(0:是)
         */
        private String isChoose;
    }
}
