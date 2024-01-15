package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.enums.SexEnum;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjBasicProjectRef;
import org.fxkc.peis.domain.bo.TjBasicProjectRefBo;
import org.fxkc.peis.domain.vo.TjBasicProjectRefVo;
import org.fxkc.peis.mapper.TjBasicProjectRefMapper;
import org.fxkc.peis.service.ITjBasicProjectRefService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 体检基础项目参考信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjBasicProjectRefServiceImpl implements ITjBasicProjectRefService {

    private final TjBasicProjectRefMapper baseMapper;

    /**
     * 查询体检基础项目参考信息
     */
    @Override
    public TjBasicProjectRefVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检基础项目参考信息列表
     */
    @Override
    public TableDataInfo<TjBasicProjectRefVo> queryPageList(TjBasicProjectRefBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjBasicProjectRef> lqw = buildQueryWrapper(bo);
        Page<TjBasicProjectRefVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检基础项目参考信息列表
     */
    @Override
    public List<TjBasicProjectRefVo> queryList(TjBasicProjectRefBo bo) {
        LambdaQueryWrapper<TjBasicProjectRef> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjBasicProjectRef> buildQueryWrapper(TjBasicProjectRefBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjBasicProjectRef> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBasicProjectId() != null, TjBasicProjectRef::getBasicProjectId, bo.getBasicProjectId());
        lqw.eq(StringUtils.isNotBlank(bo.getSex()), TjBasicProjectRef::getSex, bo.getSex());
        lqw.eq(bo.getAgeStart() != null, TjBasicProjectRef::getAgeStart, bo.getAgeStart());
        lqw.eq(bo.getAgeEnd() != null, TjBasicProjectRef::getAgeEnd, bo.getAgeEnd());
        lqw.eq(bo.getHealthReferStart() != null, TjBasicProjectRef::getHealthReferStart, bo.getHealthReferStart());
        lqw.eq(bo.getHealthReferEnd() != null, TjBasicProjectRef::getHealthReferEnd, bo.getHealthReferEnd());
        lqw.eq(bo.getCareerReferStart() != null, TjBasicProjectRef::getCareerReferStart, bo.getCareerReferStart());
        lqw.eq(bo.getCareerReferEnd() != null, TjBasicProjectRef::getCareerReferEnd, bo.getCareerReferEnd());
        return lqw;
    }

    /**
     * 新增体检基础项目参考信息
     */
    @Override
    public Boolean insertByBo(TjBasicProjectRefBo bo) {
        TjBasicProjectRef add = MapstructUtils.convert(bo, TjBasicProjectRef.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检基础项目参考信息
     */
    @Override
    public Boolean updateByBo(TjBasicProjectRefBo bo) {
        TjBasicProjectRef update = MapstructUtils.convert(bo, TjBasicProjectRef.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjBasicProjectRef entity){
        if(!checkSexScope(entity)){
            throw new ServiceException("基础项目编码'" + entity.getBasicProjectId() + "'已存在相应性别范围配置记录!");
        }
    }

    /**
     * 判断基础项目配置性别互斥性
     */
    private boolean checkSexScope(TjBasicProjectRef entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        List<TjBasicProjectRef> tjBasicProjectRefs = baseMapper.selectList(new LambdaQueryWrapper<TjBasicProjectRef>()
            .eq(TjBasicProjectRef::getDelFlag, CommonConstants.NORMAL)
            .eq(TjBasicProjectRef::getBasicProjectId, entity.getBasicProjectId())
        );
        if(CollUtil.isEmpty(tjBasicProjectRefs)){
            return true;
        }

        //存在了配置记录 还在添加或修改不是当前记录 为不限性别的直接失败
        Optional<TjBasicProjectRef> any = tjBasicProjectRefs.stream().filter(m -> Objects.equals(SexEnum.不限.getCode(), m.getSex())).findAny();
        if(any.isPresent() && any.get().getId()!=id){
            return false;
        }

        //新增操作时  存在了配置记录 还在添加不限性别的直接失败
        if(Objects.equals(SexEnum.不限,entity.getSex()) && id == -1l ){
            return false;
        }

        Optional<TjBasicProjectRef> any2 = tjBasicProjectRefs.stream().filter(m -> Objects.equals(entity.getSex(), m.getSex())).findAny();
        if(any2.isPresent() && any2.get().getId()!=id){
            return false;
        }

        return true;
    }
    /**
     * 批量删除体检基础项目参考信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
