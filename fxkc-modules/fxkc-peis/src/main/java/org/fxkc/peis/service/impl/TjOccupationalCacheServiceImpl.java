package org.fxkc.peis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CacheNames;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;
import org.fxkc.peis.mapper.TjOccupationalDictMapper;
import org.fxkc.peis.service.ITjOccupationalDictCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职业病字典Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@RequiredArgsConstructor
@Service
public class TjOccupationalCacheServiceImpl implements ITjOccupationalDictCacheService {

    private final TjOccupationalDictMapper baseMapper;

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
}
