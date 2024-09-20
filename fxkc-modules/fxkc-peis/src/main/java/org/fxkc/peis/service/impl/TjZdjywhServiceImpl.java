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
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.domain.bo.TjZdjywhBo;
import org.fxkc.peis.domain.bo.TjZdjywhQueryBo;
import org.fxkc.peis.domain.vo.TjZdjywhVo;
import org.fxkc.peis.mapper.TjZdjywhMapper;
import org.fxkc.peis.mapper.TjZdjywhTjksMapper;
import org.fxkc.peis.service.ITjZdjywhService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 诊断建议主Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
public class TjZdjywhServiceImpl implements ITjZdjywhService {

    private final TjZdjywhMapper baseMapper;

    private final TjZdjywhTjksMapper zdjywhTjksMapper;

    /**
     * 查询诊断建议主
     */
    @Override
    public TjZdjywhVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询诊断建议主列表
     */
    @Override
    public TableDataInfo<TjZdjywh> queryPageList(TjZdjywhQueryBo bo, PageQuery pageQuery) {
        Page<TjZdjywh> result = baseMapper.selectPage(pageQuery.build(), bo);
        return TableDataInfo.build(result);
    }

    /**
     * 查询诊断建议主列表
     */
    @Override
    public List<TjZdjywhVo> queryList(TjZdjywhBo bo) {
        LambdaQueryWrapper<TjZdjywh> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjZdjywh> buildQueryWrapper(TjZdjywhBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjZdjywh> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getZyzd()), TjZdjywh::getZyzd, bo.getZyzd());
        lqw.eq(StringUtils.isNotBlank(bo.getJymc()), TjZdjywh::getJymc, bo.getJymc());
        lqw.eq(StringUtils.isNotBlank(bo.getZdms()), TjZdjywh::getZdms, bo.getZdms());
        lqw.eq(StringUtils.isNotBlank(bo.getSfjb()), TjZdjywh::getSfjb, bo.getSfjb());
        lqw.eq(StringUtils.isNotBlank(bo.getCjjb()), TjZdjywh::getCjjb, bo.getCjjb());
        lqw.eq(StringUtils.isNotBlank(bo.getPyjm()), TjZdjywh::getPyjm, bo.getPyjm());
        lqw.eq(StringUtils.isNotBlank(bo.getKpsm()), TjZdjywh::getKpsm, bo.getKpsm());
        lqw.eq(StringUtils.isNotBlank(bo.getZybjy()), TjZdjywh::getZybjy, bo.getZybjy());
        lqw.eq(StringUtils.isNotBlank(bo.getIcdCode()), TjZdjywh::getIcdCode, bo.getIcdCode());
        lqw.eq(StringUtils.isNotBlank(bo.getKzkstjCode()), TjZdjywh::getKzkstjCode, bo.getKzkstjCode());
        lqw.like(StringUtils.isNotBlank(bo.getKzkstjName()), TjZdjywh::getKzkstjName, bo.getKzkstjName());
        lqw.eq(StringUtils.isNotBlank(bo.getImportance()), TjZdjywh::getImportance, bo.getImportance());
        lqw.eq(bo.getDegreeSort() != null, TjZdjywh::getDegreeSort, bo.getDegreeSort());
        return lqw;
    }

    /**
     * 新增诊断建议主
     */
    @Override
    public Boolean insertByBo(TjZdjywhBo bo) {
        TjZdjywh add = MapstructUtils.convert(bo, TjZdjywh.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改诊断建议主
     */
    @Override
    public Boolean updateByBo(TjZdjywhBo bo) {
        TjZdjywh update = MapstructUtils.convert(bo, TjZdjywh.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjZdjywh entity){
        if(StringUtils.isNotEmpty(entity.getZyzd()) && !checkNameUnique(entity)){
            throw new ServiceException("主要诊断名称'" + entity.getZyzd() + "'已存在!");
        }
    }

    /**
     * 批量删除诊断建议主
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    /**
     * 判断主要诊断名称是否唯一
     */
    private boolean checkNameUnique(TjZdjywh entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjZdjywh tjZdjywh = baseMapper.selectOne(new LambdaQueryWrapper<TjZdjywh>()
            .eq(TjZdjywh::getZyzd, entity.getZyzd())

        );
        if (ObjectUtil.isNotNull(tjZdjywh) && tjZdjywh.getId() != id) {
            return false;
        }
        return true;
    }
}
