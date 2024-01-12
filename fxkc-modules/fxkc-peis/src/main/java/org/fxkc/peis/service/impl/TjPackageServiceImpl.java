package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.domain.bo.TjPackageBo;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.mapper.TjPackageMapper;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjPackageServiceImpl implements ITjPackageService {

    private final TjPackageMapper baseMapper;

    /**
     * 查询体检套餐
     */
    @Override
    public TjPackageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检套餐列表
     */
    @Override
    public TableDataInfo<TjPackageVo> queryPageList(TjPackageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjPackage> lqw = buildQueryWrapper(bo);
        Page<TjPackageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检套餐列表
     */
    @Override
    public List<TjPackageVo> queryList(TjPackageBo bo) {
        LambdaQueryWrapper<TjPackage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjPackage> buildQueryWrapper(TjPackageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjPackage> lqw = Wrappers.lambdaQuery();
        lqw.eq(TjPackage::getDelFlag, CommonConstants.NORMAL);
        lqw.eq(StringUtils.isNotBlank(bo.getTjType()), TjPackage::getTjType, bo.getTjType());
        lqw.eq(StringUtils.isNotBlank(bo.getSuitSex()), TjPackage::getSuitSex, bo.getSuitSex());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), TjPackage::getPackageName, bo.getPackageName());
        lqw.like(StringUtils.isNotBlank(bo.getPackageSimpleName()), TjPackage::getPackageSimpleName, bo.getPackageSimpleName());
        lqw.eq(StringUtils.isNotBlank(bo.getPySimpleCode()), TjPackage::getPySimpleCode, bo.getPySimpleCode());
        lqw.eq(bo.getPackageSort() != null, TjPackage::getPackageSort, bo.getPackageSort());
        lqw.eq(bo.getStandardAmount() != null, TjPackage::getStandardAmount, bo.getStandardAmount());
        lqw.eq(bo.getDiscount() != null, TjPackage::getDiscount, bo.getDiscount());
        lqw.eq(bo.getReceivableAmount() != null, TjPackage::getReceivableAmount, bo.getReceivableAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjPackage::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增体检套餐
     */
    @Override
    public Boolean insertByBo(TjPackageBo bo) {
        TjPackage add = MapstructUtils.convert(bo, TjPackage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检套餐
     */
    @Override
    public Boolean updateByBo(TjPackageBo bo) {
        TjPackage update = MapstructUtils.convert(bo, TjPackage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjPackage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检套餐
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
