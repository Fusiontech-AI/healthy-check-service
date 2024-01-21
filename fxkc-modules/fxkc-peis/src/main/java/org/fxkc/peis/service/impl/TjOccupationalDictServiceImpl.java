package org.fxkc.peis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CacheNames;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.redis.utils.CacheUtils;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.peis.domain.vo.TjOccupationalDictTreeVo;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;
import org.fxkc.peis.enums.OccupationalDictEnum;
import org.fxkc.peis.mapper.TjOccupationalDictMapper;
import org.fxkc.peis.service.ITjOccupationalDictCacheService;
import org.fxkc.peis.service.ITjOccupationalDictService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 职业病字典Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@RequiredArgsConstructor
@Service
public class TjOccupationalDictServiceImpl extends ServiceImpl<TjOccupationalDictMapper, TjOccupationalDict> implements ITjOccupationalDictService {

    private final ITjOccupationalDictCacheService iTjOccupationalDictCacheService;

    /**
     * 查询职业病字典
     */
    @Override
    public TjOccupationalDictVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询职业病字典列表
     */
    @Override
    public TableDataInfo<TjOccupationalDictVo> queryPageList(TjOccupationalDictBo bo, PageQuery pageQuery) {
        Page<TjOccupationalDictVo> page = pageQuery.build();
        List<TjOccupationalDictVo> result = iTjOccupationalDictCacheService.queryList(bo);
        page.setTotal(result.size());
        page.setRecords(result.stream().skip((page.getCurrent() - 1) * page.getSize())
            .limit(page.getSize())
            .collect(Collectors.toList()));
        return TableDataInfo.build(page);
    }





    /**
     * 新增职业病字典
     */
    @Override
    @CachePut(cacheNames = CacheNames.TJ_OCCUPATIONAL_DICT_KEY, key = "#bo.type")
    public Boolean insertByBo(TjOccupationalDictBo bo) {
        TjOccupationalDict add = MapstructUtils.convert(bo, TjOccupationalDict.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改职业病字典
     */
    @Override
    @CachePut(cacheNames = CacheNames.TJ_OCCUPATIONAL_DICT_KEY, key = "#bo.type")
    public Boolean updateByBo(TjOccupationalDictBo bo) {
        TjOccupationalDict update = MapstructUtils.convert(bo, TjOccupationalDict.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjOccupationalDict entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除职业病字典
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        ids.forEach(k -> {
            TjOccupationalDict dict = baseMapper.selectById(k);
            CacheUtils.evict(CacheNames.TJ_OCCUPATIONAL_DICT_KEY, dict.getType());
        });
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<TjOccupationalDictTreeVo> getHazardFactorsTree(String value) {
        List<TjOccupationalDictVo> dictVoList = iTjOccupationalDictCacheService.queryList(
            new TjOccupationalDictBo().setType(OccupationalDictEnum.WHYS.getCode()));
        if(StrUtil.isNotBlank(value)) {
            dictVoList = StreamUtils.filter(dictVoList, e -> e.getValue().contains(value));
        }
        dictVoList = StreamUtils.sorted(dictVoList, Comparator.comparing(TjOccupationalDictVo::getId));
        Map<String, List<TjOccupationalDictVo>> groups = StreamUtils.groupByKey(dictVoList, e -> String.format("%s_%s", e.getSortCode(), e.getSort()));
        List<TjOccupationalDictTreeVo> voList = CollUtil.newArrayList();
        groups.forEach((k, v) -> {
            String[] str = k.split("_");
            voList.add(new TjOccupationalDictTreeVo().setCode(str[0])
                .setValue(str[1])
                .setChildren(BeanUtil.copyToList(v, TjOccupationalDictTreeVo.class)));
        });
        return voList;
    }
}
