package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.RuleExecuteRequestBo;
import org.fxkc.peis.domain.bo.RuleTjSetBo;
import org.fxkc.peis.domain.bo.RuleTjSetQueryBo;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;
import org.fxkc.peis.domain.vo.RuleTjSetVo;

import java.util.List;

/**
 * 体检项目规则集Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface IRuleTjSetService {

    TableDataInfo<RuleTjSetVo> queryRuleTjSetPages(RuleTjSetQueryBo dto, PageQuery pageQuery);

    Long addRuleTjSet(RuleTjSetBo dto);

    Boolean updateRuleTjSet(RuleTjSetBo dto);

    Boolean refreshRuleTjSet(Long rulesetId);

    /*List<RuleTjInfoExcuteResultVo> executeRuleTjSet(RuleTjSetExecuteBo dto);*/

    List<RuleTjInfoExcuteResultVo> executeRule(RuleExecuteRequestBo ruleExecuteRequestBo);
}
