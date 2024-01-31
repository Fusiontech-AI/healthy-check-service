package org.fxkc.peis.service.impl;

import cn.hutool.core.util.StrUtil;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamGroupItemBo;
import org.fxkc.peis.domain.vo.TjTeamGroupItemVo;
import org.fxkc.peis.domain.TjTeamGroupItem;
import org.fxkc.peis.mapper.TjTeamGroupItemMapper;
import org.fxkc.peis.service.ITjTeamGroupItemService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 团检分组对应项目Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@RequiredArgsConstructor
@Service
public class TjTeamGroupItemServiceImpl implements ITjTeamGroupItemService {

    private final TjTeamGroupItemMapper baseMapper;

    /**
     * 查询团检分组对应项目
     */
    @Override
    public TjTeamGroupItemVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询团检分组对应项目列表
     */
    @Override
    public TableDataInfo<TjTeamGroupItemVo> queryPageList(TjTeamGroupItemBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamGroupItem> lqw = buildQueryWrapper(bo);
        Page<TjTeamGroupItemVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询团检分组对应项目列表
     */
    @Override
    public List<TjTeamGroupItemVo> queryList(TjTeamGroupItemBo bo) {
        LambdaQueryWrapper<TjTeamGroupItem> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamGroupItem> buildQueryWrapper(TjTeamGroupItemBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTeamGroupItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getGroupId() != null, TjTeamGroupItem::getGroupId, bo.getGroupId());
        lqw.eq(bo.getItemId() != null, TjTeamGroupItem::getItemId, bo.getItemId());
        lqw.like(StrUtil.isNotBlank(bo.getItemName()), TjTeamGroupItem::getItemName, bo.getItemName());
        lqw.eq(bo.getStandardPrice() != null, TjTeamGroupItem::getStandardPrice, bo.getStandardPrice());
        lqw.eq(bo.getActualPrice() != null, TjTeamGroupItem::getActualPrice, bo.getActualPrice());
        lqw.eq(bo.getDiscount() != null, TjTeamGroupItem::getDiscount, bo.getDiscount());
        lqw.eq(StrUtil.isNotBlank(bo.getInclude()), TjTeamGroupItem::getInclude, bo.getInclude());
        lqw.eq(bo.getIsRequired() != null, TjTeamGroupItem::getIsRequired, bo.getIsRequired());
        return lqw;
    }

    /**
     * 新增团检分组对应项目
     */
    @Override
    public Boolean insertByBo(TjTeamGroupItemBo bo) {
        TjTeamGroupItem add = MapstructUtils.convert(bo, TjTeamGroupItem.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        return flag;
    }

    /**
     * 修改团检分组对应项目
     */
    @Override
    public Boolean updateByBo(TjTeamGroupItemBo bo) {
        TjTeamGroupItem update = MapstructUtils.convert(bo, TjTeamGroupItem.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamGroupItem entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除团检分组对应项目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
