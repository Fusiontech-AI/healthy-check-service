package org.fxkc.peis.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.peis.service.ITjOccupationalDictCacheService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;
import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.peis.service.ITjOccupationalDictService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 职业病字典
 * 前端访问路由地址为:/peis/occupationalDict
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/occupationalDict")
public class TjOccupationalDictController extends BaseController {

    private final ITjOccupationalDictService tjOccupationalDictService;

    private final ITjOccupationalDictCacheService iTjOccupationalDictCacheService;



    /**
     * 查询职业病字典列表(分页字典)
     */
    @SaCheckPermission("peis:occupationalDict:list")
    @GetMapping("/list")
    public TableDataInfo<TjOccupationalDictVo> list(TjOccupationalDictBo bo, PageQuery pageQuery) {
        return tjOccupationalDictService.queryPageList(bo, pageQuery);
    }


    /**
     * 查询职业病字典列表(非分页字典)
     */
    @GetMapping("/dictList")
    public List<TjOccupationalDictVo> dictList(TjOccupationalDictBo bo) {
        return iTjOccupationalDictCacheService.queryList(bo);
    }

    /**
     * 导出职业病字典列表
     */
    @SaCheckPermission("peis:occupationalDict:export")
    @Log(title = "职业病字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjOccupationalDictBo bo, HttpServletResponse response) {
        List<TjOccupationalDictVo> list = iTjOccupationalDictCacheService.queryList(bo);
        ExcelUtil.exportExcel(list, "职业病字典", TjOccupationalDictVo.class, response);
    }

    /**
     * 获取职业病字典详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:occupationalDict:query")
    @GetMapping("/{id}")
    public R<TjOccupationalDictVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjOccupationalDictService.queryById(id));
    }

    /**
     * 新增职业病字典
     */
    @SaCheckPermission("peis:occupationalDict:add")
    @Log(title = "职业病字典", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjOccupationalDictBo bo) {
        return toAjax(tjOccupationalDictService.insertByBo(bo));
    }

    /**
     * 修改职业病字典
     */
    @SaCheckPermission("peis:occupationalDict:edit")
    @Log(title = "职业病字典", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjOccupationalDictBo bo) {
        return toAjax(tjOccupationalDictService.updateByBo(bo));
    }

    /**
     * 删除职业病字典
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:occupationalDict:remove")
    @Log(title = "职业病字典", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjOccupationalDictService.deleteWithValidByIds(List.of(ids), true));
    }
}
