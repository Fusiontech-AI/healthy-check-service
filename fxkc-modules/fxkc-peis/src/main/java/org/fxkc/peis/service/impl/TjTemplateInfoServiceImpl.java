package org.fxkc.peis.service.impl;

import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTemplateInfoBo;
import org.fxkc.peis.domain.vo.TjTemplateInfoVo;
import org.fxkc.peis.domain.TjTemplateInfo;
import org.fxkc.peis.mapper.TjTemplateInfoMapper;
import org.fxkc.peis.service.ITjTemplateInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检报告模板Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@RequiredArgsConstructor
@Service
public class TjTemplateInfoServiceImpl implements ITjTemplateInfoService {

    private final TjTemplateInfoMapper baseMapper;

    /**
     * 查询体检报告模板
     */
    @Override
    public TjTemplateInfoVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检报告模板列表
     */
    @Override
    public TableDataInfo<TjTemplateInfoVo> queryPageList(TjTemplateInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTemplateInfo> lqw = buildQueryWrapper(bo);
        Page<TjTemplateInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检报告模板列表
     */
    @Override
    public List<TjTemplateInfoVo> queryList(TjTemplateInfoBo bo) {
        LambdaQueryWrapper<TjTemplateInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTemplateInfo> buildQueryWrapper(TjTemplateInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTemplateInfo> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTemplateName()), TjTemplateInfo::getTemplateName, bo.getTemplateName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhysicalType()), TjTemplateInfo::getPhysicalType, bo.getPhysicalType());
        lqw.eq(StringUtils.isNotBlank(bo.getReportType()), TjTemplateInfo::getReportType, bo.getReportType());
        lqw.eq(StringUtils.isNotBlank(bo.getDescribe()), TjTemplateInfo::getDescribe, bo.getDescribe());
        lqw.eq(StringUtils.isNotBlank(bo.getTemplatePath()), TjTemplateInfo::getTemplatePath, bo.getTemplatePath());
        lqw.eq(StringUtils.isNotBlank(bo.getTemplateData()), TjTemplateInfo::getTemplateData, bo.getTemplateData());
        lqw.eq(StringUtils.isNotBlank(bo.getExtendType()), TjTemplateInfo::getExtendType, bo.getExtendType());
        return lqw;
    }

    /**
     * 新增体检报告模板
     */
    @Override
    public Boolean insertByBo(TjTemplateInfoBo bo) {
        TjTemplateInfo add = MapstructUtils.convert(bo, TjTemplateInfo.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检报告模板
     */
    @Override
    public Boolean updateByBo(TjTemplateInfoBo bo) {
        TjTemplateInfo update = MapstructUtils.convert(bo, TjTemplateInfo.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 批量删除体检报告模板
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
