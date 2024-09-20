package org.fxkc.peis.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjBasicCommonResult;
import org.fxkc.peis.domain.bo.TjBasicCommonResultBo;
import org.fxkc.peis.domain.vo.TjBasicCommonResultVo;
import org.fxkc.peis.mapper.TjBasicCommonResultMapper;
import org.fxkc.peis.service.ITjBasicCommonResultService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 体检基础项目常见结果Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-03-05
 */
@RequiredArgsConstructor
@Service
public class TjBasicCommonResultServiceImpl implements ITjBasicCommonResultService {

    private final TjBasicCommonResultMapper baseMapper;

    /**
     * 查询体检基础项目常见结果
     */
    @Override
    public TjBasicCommonResultVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检基础项目常见结果列表
     */
    @Override
    public TableDataInfo<TjBasicCommonResultVo> queryPageList(TjBasicCommonResultBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjBasicCommonResult> lqw = buildQueryWrapper(bo);
        Page<TjBasicCommonResultVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检基础项目常见结果列表
     */
    @Override
    public List<TjBasicCommonResultVo> queryList(TjBasicCommonResultBo bo) {
        LambdaQueryWrapper<TjBasicCommonResult> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjBasicCommonResult> buildQueryWrapper(TjBasicCommonResultBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjBasicCommonResult> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBasicProjectId() != null, TjBasicCommonResult::getBasicProjectId, bo.getBasicProjectId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TjBasicCommonResult::getName, bo.getName());
        lqw.eq(bo.getSort() != null, TjBasicCommonResult::getSort, bo.getSort());
        lqw.orderByAsc(TjBasicCommonResult::getSort);
        return lqw;
    }

    /**
     * 新增体检基础项目常见结果
     */
    @Override
    public Boolean insertByBo(TjBasicCommonResultBo bo) {
        TjBasicCommonResult add = MapstructUtils.convert(bo, TjBasicCommonResult.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检基础项目常见结果
     */
    @Override
    public Boolean updateByBo(TjBasicCommonResultBo bo) {
        TjBasicCommonResult update = MapstructUtils.convert(bo, TjBasicCommonResult.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjBasicCommonResult entity){
        if(StringUtils.isNotEmpty(entity.getName()) && !checkNameUnique(entity)){
            throw new ServiceException("常见结果名称'" + entity.getName() + "'已存在!");
        }
    }

    /**
     * 批量删除体检基础项目常见结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    /**
     * 判断基础项目下常见结果名称是否唯一
     */
    private boolean checkNameUnique(TjBasicCommonResult entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjBasicCommonResult tjBasicCommonResult = baseMapper.selectOne(new LambdaQueryWrapper<TjBasicCommonResult>()
            .eq(TjBasicCommonResult::getName, entity.getName())
            .eq(TjBasicCommonResult::getBasicProjectId,entity.getBasicProjectId())
        );
        if (ObjectUtil.isNotNull(tjBasicCommonResult) && tjBasicCommonResult.getId() != id) {
            return false;
        }
        return true;
    }
}
