package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTjxmKeywordsType;
import org.fxkc.peis.domain.bo.TjTjxmKeywordTypeQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsTypeBo;
import org.fxkc.peis.mapper.TjTjxmKeywordsTypeMapper;
import org.fxkc.peis.service.ITjTjxmKeywordsTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 体检项目关键字分类Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
public class TjTjxmKeywordsTypeServiceImpl implements ITjTjxmKeywordsTypeService {

    private final TjTjxmKeywordsTypeMapper baseMapper;


    @Override
    public TableDataInfo<TjTjxmKeywordsType> queryTjxmKeywordTypePages(TjTjxmKeywordTypeQueryBo dto, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTjxmKeywordsType> lqw = new LambdaQueryWrapper<TjTjxmKeywordsType>();
        lqw.eq(dto.getStatus() != null, TjTjxmKeywordsType::getStatus, dto.getStatus());
        lqw.like(StringUtils.isNotEmpty(dto.getName()), TjTjxmKeywordsType::getName, dto.getName());
        Page<TjTjxmKeywordsType> result = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public String addTjxmKeywordType(TjTjxmKeywordsTypeBo dto) {
        TjTjxmKeywordsType tjTjxmKeywords = getTjxmKeywordByName(dto.getName());
        Assert.isNull(tjTjxmKeywords,"关键词类型["+dto.getName()+"]，已存在！");
        TjTjxmKeywordsType keywordsType = MapstructUtils.convert(dto, TjTjxmKeywordsType.class);
        baseMapper.insert(keywordsType);
        return keywordsType.getId();
    }

    @Override
    public Boolean updateTjxmKeywordType(TjTjxmKeywordsTypeBo dto) {
        TjTjxmKeywordsType oldTjTjxmKeywords = baseMapper.selectById(dto.getId());
        Assert.notNull(oldTjTjxmKeywords,"根据关键词类型id["+dto.getId()+"]未找到相应的记录信息！");
        TjTjxmKeywordsType tjTjxmKeywords = getTjxmKeywordByName(dto.getName());
        if(tjTjxmKeywords!=null && !Objects.equals(tjTjxmKeywords.getId(),oldTjTjxmKeywords.getId())){
            throw new RuntimeException("根据关键词["+dto.getName()+"]已存在关键词类型信息，不能修改！");
        }
        TjTjxmKeywordsType keywordsType = MapstructUtils.convert(dto, TjTjxmKeywordsType.class);
        return baseMapper.updateById(keywordsType)>0;
    }

    private TjTjxmKeywordsType getTjxmKeywordByName(String name) {
        List<TjTjxmKeywordsType> tjTjxmKeywords = baseMapper.selectList(new LambdaQueryWrapper<TjTjxmKeywordsType>()
            .eq(TjTjxmKeywordsType::getName, name)
        );
        if(CollUtil.isNotEmpty(tjTjxmKeywords)){
            return tjTjxmKeywords.get(0);
        }
        return null;
    }
}
