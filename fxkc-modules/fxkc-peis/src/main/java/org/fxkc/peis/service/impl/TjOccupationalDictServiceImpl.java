package org.fxkc.peis.service.impl;

import cn.hutool.core.util.StrUtil;
import org.fxkc.common.core.constant.CacheConstants;
import org.fxkc.common.core.constant.CacheNames;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.redis.utils.CacheUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.mapper.TjOccupationalDictMapper;
import org.fxkc.peis.service.ITjOccupationalDictService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 职业病字典Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@RequiredArgsConstructor
@Service
public class TjOccupationalDictServiceImpl implements ITjOccupationalDictService {

    private final TjOccupationalDictMapper baseMapper;

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
        Page<TjOccupationalDictVo> page = new Page<>();
        List<TjOccupationalDictVo> result = queryList(bo);
        page.setTotal(result.size());
        page.setRecords(result.stream().skip((long) (pageQuery.getPageNum() - 1) * pageQuery.getPageSize())
            .limit(pageQuery.getPageSize())
            .collect(Collectors.toList()));
        return TableDataInfo.build(page);
    }

    /**
     * 查询职业病字典列表
     */
    @Override
    @Cacheable(value = CacheNames.TJ_OCCUPATIONAL_DICT_KEY, key = "#bo.type", unless = "#result == null")
    public List<TjOccupationalDictVo> queryList(TjOccupationalDictBo bo) {
        LambdaQueryWrapper<TjOccupationalDict> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjOccupationalDict> buildQueryWrapper(TjOccupationalDictBo bo) {
        LambdaQueryWrapper<TjOccupationalDict> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getType()), TjOccupationalDict::getType, bo.getType());
        return lqw;
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
}
