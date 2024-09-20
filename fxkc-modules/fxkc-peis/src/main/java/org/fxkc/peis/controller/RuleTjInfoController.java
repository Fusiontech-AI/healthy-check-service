package org.fxkc.peis.controller;

import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.RuleTjInfoBo;
import org.fxkc.peis.domain.bo.RuleTjInfoQueryBo;
import org.fxkc.peis.domain.vo.RuleTjInfoVo;
import org.fxkc.peis.service.IRuleTjInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 体检项目规则项
 * 前端访问路由地址为:/peis/tjInfo
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjInfo")
public class RuleTjInfoController extends BaseController {

    private final IRuleTjInfoService ruleTjInfoService;

    /**
     * 规则项查询
     */
    @GetMapping("/queryRuleTjInfoList")
    public TableDataInfo<RuleTjInfoVo> queryRuleTjInfoList(RuleTjInfoQueryBo dto, PageQuery pageQuery) {
        return ruleTjInfoService.queryRuleTjInfoList(dto,pageQuery);
    }

    /**
     * 规则项新增
     */
    @PostMapping("/addRuleTjInfo")
    public R<Long> addRuleTjInfo(@RequestBody @Validated(AddGroup.class) RuleTjInfoBo dto) {
        return R.ok(ruleTjInfoService.addRuleTjInfo(dto));
    }


    /**
     *规则项修改
     */
    @PostMapping("/updateRuleTjInfo")
    public R<Boolean> updateRuleTjInfo(@RequestBody @Validated(EditGroup.class) RuleTjInfoBo dto) {
        return R.ok(ruleTjInfoService.updateRuleTjInfo(dto));
    }

    /**
     *规则项删除
     */
    @PostMapping("/deleteRuleTjInfo")
    public R<Boolean> deleteRuleTjInfo(@RequestBody RuleTjInfoBo dto) {
        return R.ok(ruleTjInfoService.deleteRuleTjInfo(dto));
    }
}
