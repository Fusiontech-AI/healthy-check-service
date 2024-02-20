package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.peis.domain.TjRegProjectPositive;
import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.bo.TjRegProjectPositiveBo;
import org.fxkc.peis.domain.vo.TjRegProjectPositiveVo;
import org.fxkc.peis.mapper.TjRegProjectPositiveMapper;
import org.fxkc.peis.service.ITjRegProjectPositiveService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 体检登记基础项目阳性记录Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@RequiredArgsConstructor
@Service
public class TjRegProjectPositiveServiceImpl implements ITjRegProjectPositiveService {

    private final TjRegProjectPositiveMapper baseMapper;

    /**
     * 查询体检登记基础项目阳性记录列表
     */
    @Override
    public List<TjRegProjectPositiveVo> positiveList(TjRegProjectListBo bo) {
        LambdaQueryWrapper<TjRegProjectPositive> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRegItemId() != null, TjRegProjectPositive::getRegItemId, bo.getRegItemId());
        lqw.eq(bo.getRegId() != null, TjRegProjectPositive::getRegId, bo.getRegId());
        return baseMapper.selectVoList(lqw);
    }


    /**
     * 新增体检登记基础项目阳性记录
     */
    @Override
    public Boolean insertByBo(TjRegProjectPositiveBo bo) {
        TjRegProjectPositive add = MapstructUtils.convert(bo, TjRegProjectPositive.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检登记基础项目阳性记录
     */
    @Override
    public Boolean updateByBo(TjRegProjectPositiveBo bo) {
        TjRegProjectPositive update = MapstructUtils.convert(bo, TjRegProjectPositive.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 批量删除体检登记基础项目阳性记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
