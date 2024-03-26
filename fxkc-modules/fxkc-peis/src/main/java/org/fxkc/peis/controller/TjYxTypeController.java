package org.fxkc.peis.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjYxType;
import org.fxkc.peis.domain.bo.TjYxTypeBo;
import org.fxkc.peis.domain.bo.TjYxTypeListQueryBo;
import org.fxkc.peis.domain.vo.TjYxTypeVo;
import org.fxkc.peis.service.ITjYxTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检阳性分类
 * 前端访问路由地址为:/peis/yxType
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/yxType")
public class TjYxTypeController extends BaseController {

    private final ITjYxTypeService tjYxTypeService;

    /**
     * 查询体检阳性分类列表
     */
    @GetMapping("/list")
    public TableDataInfo<TjYxTypeVo> list(TjYxTypeBo bo, PageQuery pageQuery) {
        return tjYxTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检阳性分类列表
     */
    @Log(title = "体检阳性分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjYxTypeBo bo, HttpServletResponse response) {
        List<TjYxTypeVo> list = tjYxTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检阳性分类", TjYxTypeVo.class, response);
    }

    /**
     * 获取体检阳性分类详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<TjYxTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjYxTypeService.queryById(id));
    }

    /**
     * 新增体检阳性分类
     */
    @Log(title = "体检阳性分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjYxTypeBo bo) {
        return toAjax(tjYxTypeService.insertByBo(bo));
    }

    /**
     * 修改体检阳性分类
     */
    @Log(title = "体检阳性分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjYxTypeBo bo) {
        return toAjax(tjYxTypeService.updateByBo(bo));
    }

    /**
     * 删除体检阳性分类
     *
     * @param ids 主键串
     */
    @Log(title = "体检阳性分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjYxTypeService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 阳性分类List查询
     */
    @PostMapping("/getTjYxTypedList")
    public R<List<TjYxType>> getTjYxTypedList(@RequestBody TjYxTypeListQueryBo bo){
        return R.ok(tjYxTypeService.getTjYxTypedList(bo));
    }
}
