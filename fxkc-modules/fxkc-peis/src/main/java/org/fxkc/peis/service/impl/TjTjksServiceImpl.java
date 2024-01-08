package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjTjks;
import org.fxkc.peis.domain.bo.TjTjksBo;
import org.fxkc.peis.domain.vo.TjTjksVo;
import org.fxkc.peis.mapper.TjTjksMapper;
import org.fxkc.peis.service.ITjTjksService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 体检科室Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
public class TjTjksServiceImpl implements ITjTjksService {

    private final TjTjksMapper baseMapper;

    /**
     * 查询体检科室
     */
    @Override
    public TjTjksVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检科室列表
     */
    @Override
    public TableDataInfo<TjTjksVo> queryPageList(TjTjksBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTjks> lqw = buildQueryWrapper(bo);
        Page<TjTjksVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检科室列表
     */
    @Override
    public List<TjTjksVo> queryList(TjTjksBo bo) {
        LambdaQueryWrapper<TjTjks> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTjks> buildQueryWrapper(TjTjksBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTjks> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getKsCode()), TjTjks::getKsCode, bo.getKsCode());
        lqw.like(StringUtils.isNotBlank(bo.getKsName()), TjTjks::getKsName, bo.getKsName());
        lqw.eq(StringUtils.isNotBlank(bo.getKsSimplePy()), TjTjks::getKsSimplePy, bo.getKsSimplePy());
        lqw.eq(StringUtils.isNotBlank(bo.getPrintFlag()), TjTjks::getPrintFlag, bo.getPrintFlag());
        lqw.eq(bo.getKsSort() != null, TjTjks::getKsSort, bo.getKsSort());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjTjks::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增体检科室
     */
    @Override
    public Boolean insertByBo(TjTjksBo bo) {
        TjTjks add = MapstructUtils.convert(bo, TjTjks.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检科室
     */
    @Override
    public Boolean updateByBo(TjTjksBo bo) {
        TjTjks update = MapstructUtils.convert(bo, TjTjks.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTjks entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检科室
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
