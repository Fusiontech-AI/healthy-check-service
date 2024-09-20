package org.fxkc.peis.controller;

import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjTjxmKeywordsType;
import org.fxkc.peis.domain.bo.TjTjxmKeywordTypeQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsTypeBo;
import org.fxkc.peis.service.ITjTjxmKeywordsTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 体检项目关键字分类
 * 前端访问路由地址为:/peis/tjxmKeywordsType
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjxmKeywordsType")
public class TjTjxmKeywordsTypeController extends BaseController {

    private final ITjTjxmKeywordsTypeService tjTjxmKeywordsTypeService;

    /**
     * 知识库关键词类型分页查询
     */
    @GetMapping("/queryTjxmKeywordTypePages")
    public TableDataInfo<TjTjxmKeywordsType> queryTjxmKeywordTypePages(TjTjxmKeywordTypeQueryBo dto, PageQuery pageQuery) {
        return tjTjxmKeywordsTypeService.queryTjxmKeywordTypePages(dto,pageQuery);
    }


    /**
     * 知识库关键词类型新增
     */
    @PostMapping("/addTjxmKeywordType")
    public R<String> addTjxmKeywordType(@RequestBody @Validated(AddGroup.class) TjTjxmKeywordsTypeBo dto) {
        return R.ok(tjTjxmKeywordsTypeService.addTjxmKeywordType(dto));
    }


    /**
     * 知识库关键词类型修改
     */
    @PostMapping("/updateTjxmKeywordType")
    public R<Boolean> updateTjxmKeywordType(@RequestBody @Validated(EditGroup.class) TjTjxmKeywordsTypeBo dto) {
        return R.ok(tjTjxmKeywordsTypeService.updateTjxmKeywordType(dto));
    }
}
