package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTjxmKeywordsType;
import org.fxkc.peis.domain.bo.TjTjxmKeywordTypeQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsTypeBo;

/**
 * 体检项目关键字分类Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface ITjTjxmKeywordsTypeService {

    TableDataInfo<TjTjxmKeywordsType> queryTjxmKeywordTypePages(TjTjxmKeywordTypeQueryBo dto, PageQuery pageQuery);

    String addTjxmKeywordType(TjTjxmKeywordsTypeBo dto);

    Boolean updateTjxmKeywordType(TjTjxmKeywordsTypeBo dto);
}
