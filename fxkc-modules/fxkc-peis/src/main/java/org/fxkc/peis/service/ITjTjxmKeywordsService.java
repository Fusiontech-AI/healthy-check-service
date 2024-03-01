package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTjxmKeywords;
import org.fxkc.peis.domain.bo.TjTjxmKeywordBoundingBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsBo;
import org.fxkc.peis.domain.vo.TjTjxmKeywordsVo;

import java.util.List;

/**
 * 体检项目关键字库Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface ITjTjxmKeywordsService {

    TableDataInfo<TjTjxmKeywords> queryTjxmKeywordPages(TjTjxmKeywordQueryBo dto, PageQuery pageQuery);

    String addTjxmKeyword(TjTjxmKeywordsBo dto);

    Boolean updateTjxmKeyword(TjTjxmKeywordsBo dto);

    List<TjTjxmKeywords> queryTjxmKeywordList(TjTjxmKeywordQueryBo dto);

    Boolean bindingTjxmKeyword(TjTjxmKeywordBoundingBo dto);

    Boolean updateTjxmKeywordRelation(String ids);

    List<TjTjxmKeywordsVo> getZdJyInfoByKeywords(String keyword);

}
