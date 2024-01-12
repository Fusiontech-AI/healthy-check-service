package org.fxkc.peis.service.impl;

import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjSampleInfoBo;
import org.fxkc.peis.domain.vo.TjSampleInfoVo;
import org.fxkc.peis.domain.TjSampleInfo;
import org.fxkc.peis.mapper.TjSampleInfoMapper;
import org.fxkc.peis.service.ITjSampleInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检样本配置信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
@RequiredArgsConstructor
@Service
public class TjSampleInfoServiceImpl implements ITjSampleInfoService {

    private final TjSampleInfoMapper baseMapper;

    /**
     * 查询体检样本配置信息
     */
    @Override
    public TjSampleInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检样本配置信息列表
     */
    @Override
    public TableDataInfo<TjSampleInfoVo> queryPageList(TjSampleInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjSampleInfo> lqw = buildQueryWrapper(bo);
        Page<TjSampleInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检样本配置信息列表
     */
    @Override
    public List<TjSampleInfoVo> queryList(TjSampleInfoBo bo) {
        LambdaQueryWrapper<TjSampleInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjSampleInfo> buildQueryWrapper(TjSampleInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjSampleInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSampleId() != null, TjSampleInfo::getSampleId, bo.getSampleId());
        lqw.eq(bo.getCombinProjectId() != null, TjSampleInfo::getCombinProjectId, bo.getCombinProjectId());
        return lqw;
    }

    /**
     * 新增体检样本配置信息
     */
    @Override
    public Boolean insertByBo(TjSampleInfoBo bo) {
        TjSampleInfo add = MapstructUtils.convert(bo, TjSampleInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检样本配置信息
     */
    @Override
    public Boolean updateByBo(TjSampleInfoBo bo) {
        TjSampleInfo update = MapstructUtils.convert(bo, TjSampleInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjSampleInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检样本配置信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
