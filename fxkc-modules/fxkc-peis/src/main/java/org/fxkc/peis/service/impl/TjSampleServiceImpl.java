package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjSample;
import org.fxkc.peis.domain.TjSampleInfo;
import org.fxkc.peis.domain.bo.TjSampleBatchUpdateBo;
import org.fxkc.peis.domain.bo.TjSampleBo;
import org.fxkc.peis.domain.bo.TjSamplePageBo;
import org.fxkc.peis.domain.bo.TjSampleUpdateBo;
import org.fxkc.peis.domain.vo.TjSampleInfoListVo;
import org.fxkc.peis.domain.vo.TjSampleVo;
import org.fxkc.peis.mapper.TjSampleInfoMapper;
import org.fxkc.peis.mapper.TjSampleMapper;
import org.fxkc.peis.service.ITjSampleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 体检样本Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjSampleServiceImpl implements ITjSampleService {

    private final TjSampleMapper baseMapper;

    private final TjSampleInfoMapper tjSampleInfoMapper;

    /**
     * 查询体检样本
     */
    @Override
    public TjSampleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检样本列表
     */
    @Override
    public TableDataInfo<TjSampleVo> queryPageList(TjSamplePageBo bo, PageQuery pageQuery) {
        Page<TjSampleVo> result = baseMapper.selectSamplePage(pageQuery.build(), bo);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检样本列表
     */
    @Override
    public List<TjSampleVo> queryList(TjSampleBo bo) {
        LambdaQueryWrapper<TjSample> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjSample> buildQueryWrapper(TjSampleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjSample> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSampleCode()), TjSample::getSampleCode, bo.getSampleCode());
        lqw.like(StringUtils.isNotBlank(bo.getSampleName()), TjSample::getSampleName, bo.getSampleName());
        lqw.eq(StringUtils.isNotBlank(bo.getSampleCategory()), TjSample::getSampleCategory, bo.getSampleCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getSampleType()), TjSample::getSampleType, bo.getSampleType());
        lqw.eq(StringUtils.isNotBlank(bo.getBarCodeType()), TjSample::getBarCodeType, bo.getBarCodeType());
        lqw.eq(bo.getPrintSort() != null, TjSample::getPrintSort, bo.getPrintSort());
        lqw.eq(bo.getPrintNumber() != null, TjSample::getPrintNumber, bo.getPrintNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getPrintFlag()), TjSample::getPrintFlag, bo.getPrintFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getPrintApplyFlag()), TjSample::getPrintApplyFlag, bo.getPrintApplyFlag());
        lqw.eq(bo.getPrintApplyNumber() != null, TjSample::getPrintApplyNumber, bo.getPrintApplyNumber());
        lqw.orderByAsc(TjSample::getPrintSort).orderByDesc(TjSample::getCreateTime);
        return lqw;
    }

    /**
     * 新增体检样本
     */
    @Override
    public Boolean insertByBo(TjSampleBo bo) {
        TjSample add = MapstructUtils.convert(bo, TjSample.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检样本
     */
    @Override
    public Boolean updateByBo(TjSampleBo bo) {
        TjSample update = MapstructUtils.convert(bo, TjSample.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjSample entity){
        if(StringUtils.isNotEmpty(entity.getSampleCode()) && !checkCodeUnique(entity)){
            throw new ServiceException("项目样本编码'" + entity.getSampleCode() + "'已存在!");
        }

        if(StringUtils.isNotEmpty(entity.getSampleName()) && !checkNameUnique(entity)){
            throw new ServiceException("项目样本名称'" + entity.getSampleName() + "'已存在!");
        }
    }

    /**
     * 判断项目样本编码是否唯一
     */
    private boolean checkCodeUnique(TjSample entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjSample tjSample = baseMapper.selectOne(new LambdaQueryWrapper<TjSample>()
            .eq(TjSample::getDelFlag, CommonConstants.NORMAL)
            .eq(TjSample::getSampleCode, entity.getSampleCode())

        );
        if (ObjectUtil.isNotNull(tjSample) && tjSample.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 判断项目样本名称是否唯一
     */
    private boolean checkNameUnique(TjSample entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjSample tjSample = baseMapper.selectOne(new LambdaQueryWrapper<TjSample>()
            .eq(TjSample::getDelFlag, CommonConstants.NORMAL)
            .eq(TjSample::getSampleName, entity.getSampleName())

        );
        if (ObjectUtil.isNotNull(tjSample) && tjSample.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 批量删除体检样本
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<TjSampleInfoListVo> getCombinProjectBySampleId(Long id) {
        return baseMapper.getCombinProjectBySampleId(id);
    }

    @Override
    public Boolean updateCombinProjectBySampleId(TjSampleUpdateBo tjSampleUpdateBo) {
        //先根据样本主键id进行删除操作
        tjSampleInfoMapper.delete(new LambdaQueryWrapper<TjSampleInfo>()
            .eq(TjSampleInfo::getDelFlag,CommonConstants.NORMAL)
            .eq(TjSampleInfo::getSampleId,tjSampleUpdateBo.getId())
        );

        List<TjSampleInfoListVo> sampleInfoListVos = tjSampleUpdateBo.getSampleInfoListVos();
        if(CollUtil.isNotEmpty(sampleInfoListVos)){
            List<TjSampleInfo> tjSampleInfos = sampleInfoListVos.stream().map(m -> {
                TjSampleInfo tjSampleInfo = new TjSampleInfo();
                tjSampleInfo.setSampleId(tjSampleUpdateBo.getId());
                tjSampleInfo.setCombinProjectId(m.getCombinProjectId());
                return tjSampleInfo;
            }).collect(Collectors.toList());
            tjSampleInfoMapper.insertBatch(tjSampleInfos);
        }
        return true;
    }

    @Override
    public Boolean batchDisable(List<Long> ids) {
        List<TjSample> buidList = ids.stream().map(m -> {
            TjSample tjSample = new TjSample();
            tjSample.setId(m);
            tjSample.setStatus(CommonConstants.DISABLE);
            return tjSample;
        }).collect(Collectors.toList());
        return baseMapper.updateBatchById(buidList);
    }

    @Override
    public Boolean batchUpdateCategory(TjSampleBatchUpdateBo bo) {
        List<Long> ids = bo.getIds();
        String sampleCategory = bo.getSampleCategory();
        List<TjSample> buidList = ids.stream().map(m -> {
            TjSample tjSample = new TjSample();
            tjSample.setId(m);
            tjSample.setSampleCategory(sampleCategory);
            return tjSample;
        }).collect(Collectors.toList());
        return baseMapper.updateBatchById(buidList);
    }
}
