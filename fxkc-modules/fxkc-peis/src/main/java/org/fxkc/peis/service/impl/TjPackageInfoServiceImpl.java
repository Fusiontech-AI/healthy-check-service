package org.fxkc.peis.service.impl;

import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjPackageInfoBo;
import org.fxkc.peis.domain.vo.TjPackageInfoVo;
import org.fxkc.peis.domain.TjPackageInfo;
import org.fxkc.peis.mapper.TjPackageInfoMapper;
import org.fxkc.peis.service.ITjPackageInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检组合项目详细信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjPackageInfoServiceImpl implements ITjPackageInfoService {

    private final TjPackageInfoMapper baseMapper;

    /**
     * 查询体检组合项目详细信息
     */
    @Override
    public TjPackageInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检组合项目详细信息列表
     */
    @Override
    public TableDataInfo<TjPackageInfoVo> queryPageList(TjPackageInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjPackageInfo> lqw = buildQueryWrapper(bo);
        Page<TjPackageInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检组合项目详细信息列表
     */
    @Override
    public List<TjPackageInfoVo> queryList(TjPackageInfoBo bo) {
        LambdaQueryWrapper<TjPackageInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjPackageInfo> buildQueryWrapper(TjPackageInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjPackageInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPackageId() != null, TjPackageInfo::getPackageId, bo.getPackageId());
        lqw.eq(bo.getCombinProjectId() != null, TjPackageInfo::getCombinProjectId, bo.getCombinProjectId());
        lqw.eq(bo.getStandardAmount() != null, TjPackageInfo::getStandardAmount, bo.getStandardAmount());
        lqw.eq(bo.getDiscount() != null, TjPackageInfo::getDiscount, bo.getDiscount());
        lqw.eq(bo.getReceivableAmount() != null, TjPackageInfo::getReceivableAmount, bo.getReceivableAmount());
        return lqw;
    }

    /**
     * 新增体检组合项目详细信息
     */
    @Override
    public Boolean insertByBo(TjPackageInfoBo bo) {
        TjPackageInfo add = MapstructUtils.convert(bo, TjPackageInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检组合项目详细信息
     */
    @Override
    public Boolean updateByBo(TjPackageInfoBo bo) {
        TjPackageInfo update = MapstructUtils.convert(bo, TjPackageInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjPackageInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检组合项目详细信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
