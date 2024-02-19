package org.fxkc.peis.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springdoc.core.annotations.ParameterObject;
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
import org.fxkc.peis.domain.vo.TjTeamTaskFileVo;
import org.fxkc.peis.domain.bo.TjTeamTaskFileBo;
import org.fxkc.peis.service.ITjTeamTaskFileService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 体检单位任务文件
 * 前端访问路由地址为:/peis/teamTaskFile
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamTaskFile")
public class TjTeamTaskFileController extends BaseController {

    private final ITjTeamTaskFileService tjTeamTaskFileService;

    /**
     * 查询体检单位任务文件列表
     */
    @SaCheckPermission("peis:teamTaskFile:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamTaskFileVo> list(TjTeamTaskFileBo bo, PageQuery pageQuery) {
        return tjTeamTaskFileService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检单位任务文件列表
     */
    @SaCheckPermission("peis:teamTaskFile:export")
    @Log(title = "体检单位任务文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamTaskFileBo bo, HttpServletResponse response) {
        List<TjTeamTaskFileVo> list = tjTeamTaskFileService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检单位任务文件", TjTeamTaskFileVo.class, response);
    }

    /**
     * 获取体检单位任务文件详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public void downLoadTaskFile(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id, HttpServletResponse response) {
       tjTeamTaskFileService.downLoadTaskFile(id, response);
    }

    /**
     * 新增体检单位任务文件
     */
    @SaCheckPermission("peis:teamTaskFile:add")
    @Log(title = "体检单位任务文件", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@RequestPart("file") MultipartFile file, @ParameterObject @Validated(AddGroup.class) TjTeamTaskFileBo bo) {
        return toAjax(tjTeamTaskFileService.insertByBo(file, bo));
    }

    /**
     * 修改体检单位任务文件
     */
    @SaCheckPermission("peis:teamTaskFile:edit")
    @Log(title = "体检单位任务文件", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@RequestPart("file") MultipartFile file, @ParameterObject @Validated(EditGroup.class) TjTeamTaskFileBo bo) {
        return toAjax(tjTeamTaskFileService.updateByBo(file, bo));
    }

    /**
     * 删除体检单位任务文件
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamTaskFile:remove")
    @Log(title = "体检单位任务文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamTaskFileService.deleteWithValidByIds(List.of(ids), true));
    }
}
