package org.fxkc.peis.service.impl;

import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjCombinationProjectInfoBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectInfoVo;
import org.fxkc.peis.domain.TjCombinationProjectInfo;
import org.fxkc.peis.mapper.TjCombinationProjectInfoMapper;
import org.fxkc.peis.service.ITjCombinationProjectInfoService;

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
public class TjCombinationProjectInfoServiceImpl implements ITjCombinationProjectInfoService {

    private final TjCombinationProjectInfoMapper baseMapper;

    /**
     * 查询体检组合项目详细信息
     */
    @Override
    public TjCombinationProjectInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检组合项目详细信息列表
     */
    @Override
    public TableDataInfo<TjCombinationProjectInfoVo> queryPageList(TjCombinationProjectInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjCombinationProjectInfo> lqw = buildQueryWrapper(bo);
        Page<TjCombinationProjectInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检组合项目详细信息列表
     */
    @Override
    public List<TjCombinationProjectInfoVo> queryList(TjCombinationProjectInfoBo bo) {
        LambdaQueryWrapper<TjCombinationProjectInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjCombinationProjectInfo> buildQueryWrapper(TjCombinationProjectInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjCombinationProjectInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBasicProjectId() != null, TjCombinationProjectInfo::getBasicProjectId, bo.getBasicProjectId());
        lqw.eq(bo.getCombinProjectId() != null, TjCombinationProjectInfo::getCombinProjectId, bo.getCombinProjectId());
        return lqw;
    }

    /**
     * 新增体检组合项目详细信息
     */
    @Override
    public Boolean insertByBo(TjCombinationProjectInfoBo bo) {
        TjCombinationProjectInfo add = MapstructUtils.convert(bo, TjCombinationProjectInfo.class);
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
    public Boolean updateByBo(TjCombinationProjectInfoBo bo) {
        TjCombinationProjectInfo update = MapstructUtils.convert(bo, TjCombinationProjectInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjCombinationProjectInfo entity){
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

    @Override
    public List<TjCombinationProjectInfoVo> queryBasicListByCombinIds(List<Long> combinIds) {
        List<TjCombinationProjectInfoVo> tjCombinationProjectInfoVos = baseMapper.selectVoList(new LambdaQueryWrapper<TjCombinationProjectInfo>()
            .in(TjCombinationProjectInfo::getCombinProjectId, combinIds));
        return tjCombinationProjectInfoVos;
    }
}
