package org.fxkc.peis.controller;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.bo.template.TjTemplateQueryBO;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.fxkc.peis.service.ITjTemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.common.core.domain.R;

import java.util.List;


/**
 * 体检报告模板
 * 前端访问路由地址为:/peis/templateInfo
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/templateInfo")
public class TjTemplateInfoController extends BaseController {

    private final ITjTemplateService tjTemplateService;

    /**
     * 根据条件查询list
     * @param bo 查询条件
     * @return
     */
    @GetMapping("/getList")
    public R<List<TjTemplateVo>> getList(TjTemplateQueryBO bo){
        return R.ok(tjTemplateService.getList(bo));
    }
    /**
     * 下载模板
     */
    @GetMapping("/downloadTemplate/{id}")
    public void downloadTemplate(@PathVariable("id") String id,HttpServletResponse response) throws Exception {
        tjTemplateService.downloadTemplate(id,response);
    }

}
