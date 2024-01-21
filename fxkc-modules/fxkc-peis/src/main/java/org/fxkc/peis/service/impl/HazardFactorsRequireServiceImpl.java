package org.fxkc.peis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.enums.AssociationTypeEnum;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.mapper.HazardFactorsRequireMapper;
import org.fxkc.peis.service.IHazardFactorsRequireService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 危害因素必检项目主Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@RequiredArgsConstructor
@Service
public class HazardFactorsRequireServiceImpl extends ServiceImpl<HazardFactorsRequireMapper, HazardFactorsRequire> implements IHazardFactorsRequireService {


    /**
     * 查询危害因素必检项目主
     */
    @Override
    public HazardFactorsRequireVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询危害因素必检项目主列表
     */
    @Override
    public TableDataInfo<HazardFactorsRequireVo> queryPageList(HazardFactorsRequireBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HazardFactorsRequire> lqw = buildQueryWrapper(bo);
        Page<HazardFactorsRequireVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询危害因素必检项目主列表
     */
    @Override
    public List<HazardFactorsRequireVo> queryList(HazardFactorsRequireBo bo) {
        LambdaQueryWrapper<HazardFactorsRequire> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HazardFactorsRequire> buildQueryWrapper(HazardFactorsRequireBo bo) {
        LambdaQueryWrapper<HazardFactorsRequire> lqw = Wrappers.lambdaQuery();

        return lqw;
    }

    /**
     * 新增危害因素必检项目主
     */
    @Override
    public Boolean insertByBo(HazardFactorsRequireBo bo) {
        HazardFactorsRequire add = MapstructUtils.convert(bo, HazardFactorsRequire.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        return flag;
    }

    /**
     * 修改危害因素必检项目主
     */
    @Override
    public Boolean updateByBo(HazardFactorsRequireBo bo) {
        HazardFactorsRequire update = MapstructUtils.convert(bo, HazardFactorsRequire.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HazardFactorsRequire entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除危害因素必检项目主
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public HazardFactorsRequireVo hazardFactorsQuery(HazardFactorsRequireBo bo, PageQuery pageQuery) {
        HazardFactorsRequireVo hazardFactorsRequireVo = new HazardFactorsRequireVo();
        if(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode())) {
            Page<HazardFactorsRequireVo.HazardFactorsRequireQueryVo> page = baseMapper.hazardFactorsQueryPage(
                pageQuery.build(), bo);
            hazardFactorsRequireVo.setPageVo(TableDataInfo.build(page));
        }else {
            HazardFactorsRequire hazardFactorsRequire = baseMapper.selectOne(Wrappers.lambdaQuery(HazardFactorsRequire.class)
                .eq(HazardFactorsRequire::getHazardFactorsCode, bo.getHazardFactorsCode())
                .eq(HazardFactorsRequire::getAssociationType, bo.getAssociationType()));
            HazardFactorsRequireVo.HazardFactorsRequireQueryVo vo = BeanUtil.toBean(hazardFactorsRequire, HazardFactorsRequireVo.HazardFactorsRequireQueryVo.class);
            hazardFactorsRequireVo.setVo(vo);
        }
        return hazardFactorsRequireVo;
    }
}
