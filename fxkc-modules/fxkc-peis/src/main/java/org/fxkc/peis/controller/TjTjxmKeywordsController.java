package org.fxkc.peis.controller;

import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjTjxmKeywords;
import org.fxkc.peis.domain.bo.TjTjxmKeywordBoundingBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsBo;
import org.fxkc.peis.service.ITjTjxmKeywordsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检项目关键字库
 * 前端访问路由地址为:/peis/tjxmKeywords
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjxmKeywords")
public class TjTjxmKeywordsController extends BaseController {

    private final ITjTjxmKeywordsService tjTjxmKeywordsService;

    /**
     * 知识库关键词库分页查询
     */
    @GetMapping("/queryTjxmKeywordPages")
    public TableDataInfo<TjTjxmKeywords> queryTjxmKeywordPages(TjTjxmKeywordQueryBo dto, PageQuery pageQuery) {
        return tjTjxmKeywordsService.queryTjxmKeywordPages(dto,pageQuery);
    }


    /**
     * 知识库关键词库新增
     */
    @PostMapping("/addTjxmKeyword")
    public R<String> addTjxmKeyword(@RequestBody @Validated(AddGroup.class) TjTjxmKeywordsBo dto) {
        return R.ok(tjTjxmKeywordsService.addTjxmKeyword(dto));
    }


    /**
     * 知识库关键词库修改
     */
    @PostMapping("/updateTjxmKeyword")
    public R<Boolean> updateTjxmKeyword(@RequestBody @Validated(EditGroup.class) TjTjxmKeywordsBo dto) {
        return R.ok(tjTjxmKeywordsService.updateTjxmKeyword(dto));
    }


    /**
     * 知识库可绑定关键词库查询
     */
    @PostMapping("/queryTjxmKeywordList")
    public R<List<TjTjxmKeywords>> queryTjxmKeywordList(@RequestBody TjTjxmKeywordQueryBo dto) {
        return R.ok(tjTjxmKeywordsService.queryTjxmKeywordList(dto));
    }


    /**
     * 知识库关键词和诊断建议绑定
     */
    @PostMapping("/bindingTjxmKeyword")
    public R<Boolean> bindingTjxmKeyword(@RequestBody TjTjxmKeywordBoundingBo dto) {
        return R.ok(tjTjxmKeywordsService.bindingTjxmKeyword(dto));
    }

    /**
     * 知识库关键词和诊断建议删除绑定
     */
    @PostMapping("/deleteRelationByIds")
    public R<Boolean> deleteRelationByIds(@RequestBody String ids) {
        return R.ok(tjTjxmKeywordsService.updateTjxmKeywordRelation(ids));
    }
}
