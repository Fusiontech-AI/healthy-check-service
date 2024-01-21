package org.fxkc.peis.service.impl;

import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.HazardFactorsItemBo;
import org.fxkc.peis.domain.vo.HazardFactorsItemVo;
import org.fxkc.peis.domain.HazardFactorsItem;
import org.fxkc.peis.mapper.HazardFactorsItemMapper;
import org.fxkc.peis.service.IHazardFactorsItemService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 危害因素必检项目关联Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@RequiredArgsConstructor
@Service
public class HazardFactorsItemServiceImpl implements IHazardFactorsItemService {

    private final HazardFactorsItemMapper baseMapper;

    /**
     * 查询危害因素必检项目关联
     */
    @Override
    public HazardFactorsItemVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询危害因素必检项目关联列表
     */
    @Override
    public TableDataInfo<HazardFactorsItemVo> queryPageList(HazardFactorsItemBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HazardFactorsItem> lqw = buildQueryWrapper(bo);
        Page<HazardFactorsItemVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询危害因素必检项目关联列表
     */
    @Override
    public List<HazardFactorsItemVo> queryList(HazardFactorsItemBo bo) {
        LambdaQueryWrapper<HazardFactorsItem> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HazardFactorsItem> buildQueryWrapper(HazardFactorsItemBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HazardFactorsItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getFactorsId() != null, HazardFactorsItem::getFactorsId, bo.getFactorsId());
        lqw.eq(bo.getItemId() != null, HazardFactorsItem::getItemId, bo.getItemId());
        return lqw;
    }

    /**
     * 新增危害因素必检项目关联
     */
    @Override
    public Boolean insertByBo(HazardFactorsItemBo bo) {
        HazardFactorsItem add = MapstructUtils.convert(bo, HazardFactorsItem.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改危害因素必检项目关联
     */
    @Override
    public Boolean updateByBo(HazardFactorsItemBo bo) {
        HazardFactorsItem update = MapstructUtils.convert(bo, HazardFactorsItem.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HazardFactorsItem entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除危害因素必检项目关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
