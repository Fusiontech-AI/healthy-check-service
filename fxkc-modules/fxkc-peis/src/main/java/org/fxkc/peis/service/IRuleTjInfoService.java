package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.RuleTjInfoBo;
import org.fxkc.peis.domain.bo.RuleTjInfoQueryBo;
import org.fxkc.peis.domain.vo.RuleTjInfoVo;

/**
 * 体检项目规则项Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface IRuleTjInfoService {

    TableDataInfo<RuleTjInfoVo> queryRuleTjInfoList(RuleTjInfoQueryBo dto, PageQuery pageQuery);

    Long addRuleTjInfo(RuleTjInfoBo dto);

    Boolean updateRuleTjInfo(RuleTjInfoBo dto);

    Boolean deleteRuleTjInfo(RuleTjInfoBo dto);
}
