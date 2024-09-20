package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTjxmKeywords;
import org.fxkc.peis.domain.TjTjxmKeywordsRelation;
import org.fxkc.peis.domain.bo.TjTjxmKeywordBoundingBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordQueryBo;
import org.fxkc.peis.domain.bo.TjTjxmKeywordsBo;
import org.fxkc.peis.domain.vo.TjTjxmKeywordsVo;
import org.fxkc.peis.mapper.TjTjxmKeywordsMapper;
import org.fxkc.peis.mapper.TjTjxmKeywordsRelationMapper;
import org.fxkc.peis.service.ITjTjxmKeywordsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检项目关键字库Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TjTjxmKeywordsServiceImpl implements ITjTjxmKeywordsService {

    private final TjTjxmKeywordsMapper baseMapper;

    private final TjTjxmKeywordsRelationMapper relationMapper;

    @Override
    public TableDataInfo<TjTjxmKeywords> queryTjxmKeywordPages(TjTjxmKeywordQueryBo dto, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTjxmKeywords> lqw = new LambdaQueryWrapper<TjTjxmKeywords>();
        lqw.eq(StringUtils.isNotEmpty(dto.getKeyTypeId()), TjTjxmKeywords::getKeyTypeId, dto.getKeyTypeId());
        lqw.eq(dto.getStatus() != null, TjTjxmKeywords::getStatus, dto.getStatus());
        lqw.like(StringUtils.isNotEmpty(dto.getName()), TjTjxmKeywords::getName, dto.getName());
        Page<TjTjxmKeywords> result = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public String addTjxmKeyword(TjTjxmKeywordsBo dto) {
        TjTjxmKeywords tjTjxmKeywords = getTjxmKeywordByName(dto.getName());
        Assert.isNull(tjTjxmKeywords,"关键词["+dto.getName()+"]，已存在！");
        TjTjxmKeywords tjxmKeywords = MapstructUtils.convert(dto, TjTjxmKeywords.class);
        baseMapper.insert(tjxmKeywords);
        return tjxmKeywords.getId();
    }

    @Override
    public Boolean updateTjxmKeyword(TjTjxmKeywordsBo dto) {
        TjTjxmKeywords oldTjTjxmKeywords = baseMapper.selectById(dto.getId());
        Assert.notNull(oldTjTjxmKeywords,"根据关键词库id["+dto.getId()+"]未找到相应的记录信息！");
        TjTjxmKeywords tjTjxmKeywords = getTjxmKeywordByName(dto.getName());
        if(tjTjxmKeywords!=null && !Objects.equals(tjTjxmKeywords.getId(),oldTjTjxmKeywords.getId())){
            throw new RuntimeException("根据关键词["+dto.getName()+"]已存在关键词库信息，不能修改！");
        }
        TjTjxmKeywords tjxmKeywords = MapstructUtils.convert(dto, TjTjxmKeywords.class);
        return baseMapper.updateById(tjxmKeywords)>0;
    }

    @Override
    public List<TjTjxmKeywords> queryTjxmKeywordList(TjTjxmKeywordQueryBo dto) {
        QueryWrapper<TjTjxmKeywords> wrapper = new QueryWrapper<>();
        wrapper.eq("t1.DEL_FLAG", CommonConstants.NORMAL)
            .eq("t1.status", CommonConstants.NORMAL)
            .notExists("select 1 from tj_tjxm_keywords_relation t2 where t2.keyword_id = t1.id and t2.is_delete=0")
            .like(StringUtils.isNotBlank(dto.getName()), "t1.NAME", dto.getName())
            .orderByAsc("t1.CREATE_TIME");
        List<TjTjxmKeywords> tjTjxmKeywords = baseMapper.selectNoBindKeywords(wrapper);
        return tjTjxmKeywords;
    }

    @Override
    public Boolean bindingTjxmKeyword(TjTjxmKeywordBoundingBo dto) {
        List<TjTjxmKeywordsRelation> addList = new ArrayList<>();
        List<String> keywordIds = dto.getKeywordIds();
        for (int i = 0; i < keywordIds.size(); i++) {
            TjTjxmKeywordsRelation tjTjxmKeywordsRelation = new TjTjxmKeywordsRelation();
            tjTjxmKeywordsRelation.setKeywordId(keywordIds.get(i));
            tjTjxmKeywordsRelation.setZdjyId(dto.getZdjyId());
            addList.add(tjTjxmKeywordsRelation);
        }
        if(CollUtil.isEmpty(addList)){
            return false;
        }

        return relationMapper.insertBatch(addList);
    }

    @Override
    public Boolean updateTjxmKeywordRelation(String ids) {
        String[] split = ids.split(",");
        return relationMapper.deleteBatchIds(Arrays.asList(split))>0;
    }

    @Override
    public List<TjTjxmKeywordsVo> getZdJyInfoByKeywords(String keyword) {
        //这里走包含 且 精度大于0.85的逻辑
        List<TjTjxmKeywords> tjxmKeywords = baseMapper.selectList(new LambdaQueryWrapper<TjTjxmKeywords>()
            .eq(TjTjxmKeywords::getStatus, CommonConstants.NORMAL)
            .orderByDesc(TjTjxmKeywords::getPriority)
        );
        String s = keyword.replaceAll("[\n\r]", "").replace((char) 12288, ' ').trim();
        log.info("原始关键词["+keyword+"],经过处理空格后s为"+s);
        List<TjTjxmKeywordsVo> respList = new ArrayList<>();
        //根据关键词类型id进行分组  每个类型下找到一条就直接返回
        Map<String, List<TjTjxmKeywords>> collect = tjxmKeywords.stream().collect(Collectors.groupingBy(TjTjxmKeywords::getKeyTypeId));
        for(String key:collect.keySet()){
            Optional<TjTjxmKeywords> first = collect.get(key).stream().filter(m -> s.contains(m.getName())).findFirst();
            if(first.isPresent()){
                TjTjxmKeywords keywords = first.get();
                log.info("匹配到类型"+key+"下关键词库中第一条记录为["+ JSON.toJSONString(keywords) +"]");
                List<TjTjxmKeywordsRelation> keywordsRelations = relationMapper.selectList(new LambdaQueryWrapper<TjTjxmKeywordsRelation>()
                    .eq(TjTjxmKeywordsRelation::getKeywordId, keywords.getId())
                );
                if(CollUtil.isNotEmpty(keywordsRelations)){
                    TjTjxmKeywordsRelation keywordsRelation = keywordsRelations.get(0);
                    TjTjxmKeywordsVo respVo = new TjTjxmKeywordsVo();
                    BeanUtils.copyProperties(keywords,respVo);
                    respVo.setZdjyId(keywordsRelation.getZdjyId());
                    respList.add(respVo);
                    log.info("单个根据类型"+key+"下关键词["+keyword+"],匹配对应的建议诊断信息成功！"+keywordsRelation.getZdjyId());
                }
            }
        }
        log.info("最终根据关键词["+keyword+"],匹配对应的建议诊断信息数量为"+respList.size());

        return respList;
    }

    private TjTjxmKeywords getTjxmKeywordByName(String name) {
        List<TjTjxmKeywords> tjTjxmKeywords = this.baseMapper.selectList(new LambdaQueryWrapper<TjTjxmKeywords>()
            .eq(TjTjxmKeywords::getName, name)
        );
        if(CollUtil.isNotEmpty(tjTjxmKeywords)){
            return tjTjxmKeywords.get(0);
        }
        return null;
    }
}
