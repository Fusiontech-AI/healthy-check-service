package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjBasicCommonResult;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectListBo;
import org.fxkc.peis.domain.vo.TjBasicCommonResultVo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.domain.vo.ftlModel.CheckItemResultVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetItemVo;
import org.fxkc.peis.domain.vo.ftlModel.TxmModel;
import org.fxkc.peis.enums.CheckStatusEnum;
import org.fxkc.peis.mapper.TjBasicCommonResultMapper;
import org.fxkc.peis.mapper.TjRegBasicProjectMapper;
import org.fxkc.peis.mapper.TjRegCombinationProjectMapper;
import org.fxkc.peis.service.ITjRegCombinationProjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 体检人员综合项目信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@RequiredArgsConstructor
@Service
public class TjRegCombinationProjectServiceImpl implements ITjRegCombinationProjectService {

    private final TjRegCombinationProjectMapper baseMapper;

    private final TjRegBasicProjectMapper tjRegBasicProjectMapper;

    private final TjBasicCommonResultMapper tjBasicCommonResultMapper;


    /**
     * 查询体检人员综合项目信息列表
     */
    @Override
    public TableDataInfo<TjRegCombinationProjectListVo> queryPageList(TjRegCombinationProjectListBo bo, PageQuery pageQuery) {
        Page<TjRegCombinationProjectListVo> page = baseMapper.selectPage(pageQuery.build(), bo);
        return TableDataInfo.build(page);
    }


    private LambdaQueryWrapper<TjRegCombinationProject> buildQueryWrapper(TjRegCombinationProjectBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjRegCombinationProject> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRegisterId() != null, TjRegCombinationProject::getRegisterId, bo.getRegisterId());
        lqw.eq(bo.getCombinationProjectId() != null, TjRegCombinationProject::getCombinationProjectId, bo.getCombinationProjectId());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectType()), TjRegCombinationProject::getProjectType, bo.getProjectType());
        lqw.eq(bo.getStandardAmount() != null, TjRegCombinationProject::getStandardAmount, bo.getStandardAmount());
        lqw.eq(bo.getDiscount() != null, TjRegCombinationProject::getDiscount, bo.getDiscount());
        lqw.eq(bo.getReceivableAmount() != null, TjRegCombinationProject::getReceivableAmount, bo.getReceivableAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getPayStatus()), TjRegCombinationProject::getPayStatus, bo.getPayStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getPayMode()), TjRegCombinationProject::getPayMode, bo.getPayMode());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckStatus()), TjRegCombinationProject::getCheckStatus, bo.getCheckStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectRequiredType()), TjRegCombinationProject::getProjectRequiredType, bo.getProjectRequiredType());
        lqw.eq(bo.getAbandonTime() != null, TjRegCombinationProject::getAbandonTime, bo.getAbandonTime());
        lqw.eq(bo.getDelayTime() != null, TjRegCombinationProject::getDelayTime, bo.getDelayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getDelayReason()), TjRegCombinationProject::getDelayReason, bo.getDelayReason());
        lqw.eq(bo.getCheckDoctor() != null, TjRegCombinationProject::getCheckDoctor, bo.getCheckDoctor());
        lqw.eq(bo.getCheckTime() != null, TjRegCombinationProject::getCheckTime, bo.getCheckTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckResult()), TjRegCombinationProject::getCheckResult, bo.getCheckResult());
        return lqw;
    }

    /**
     * 批量删除体检人员综合项目信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean abandonProjects(Collection<Long> ids) {
        Long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjRegCombinationProject.class).in(TjRegCombinationProject::getId, ids)
            .eq(TjRegCombinationProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjRegCombinationProject::getCheckStatus, CheckStatusEnum.已检查.getCode()));
        if(count > 0){
            throw new ServiceException("已检查项目不可弃捡，请重试！");
        }

    return baseMapper.update(TjRegCombinationProject.builder()
            .abandonTime(DateUtil.date())
            .checkStatus(CheckStatusEnum.弃捡.getCode()).build(),
            Wrappers.lambdaQuery(TjRegCombinationProject.class).in(TjRegCombinationProject::getId,ids)) >0;
    }

    @Override
    public Boolean restoreProjects(Collection<Long> ids) {
        return baseMapper.update(TjRegCombinationProject.builder()
                .checkStatus(CheckStatusEnum.未检查.getCode()).build(),
            Wrappers.lambdaQuery(TjRegCombinationProject.class).in(TjRegCombinationProject::getId,ids)) >0;
    }

    @Override
    public Boolean delayProjects(TjRegCombinationProjectDelayBo delayBo) {
        Long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjRegCombinationProject.class).in(TjRegCombinationProject::getId, delayBo.getIds())
            .eq(TjRegCombinationProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjRegCombinationProject::getCheckStatus, CheckStatusEnum.已检查.getCode()));
        if(count > 0){
            throw new ServiceException("已检查项目不可弃捡，请重试！");
        }

        return baseMapper.update(TjRegCombinationProject.builder()
                .delayTime(delayBo.getDelayTime())
                .delayReason(delayBo.getDelayReason())
                .checkStatus(CheckStatusEnum.延期.getCode()).build(),
            Wrappers.lambdaQuery(TjRegCombinationProject.class).in(TjRegCombinationProject::getId,delayBo.getIds())) >0;
    }

    @Override
    public List<GuideSheetItemVo> queryGuideItemByIds(List<Long> regIdList) {
        return this.baseMapper.queryGuideItemByIds(regIdList);
    }

    @Override
    public List<TjRegBasicProjectVo> queryRegBasicProjectList(Long id) {
        List<TjRegBasicProjectVo> tjRegBasicProjectVos = tjRegBasicProjectMapper.queryRegBasicProjectList(id);
        if(CollUtil.isNotEmpty(tjRegBasicProjectVos)){
            List<Long> basicIds = tjRegBasicProjectVos.stream().map(m -> m.getBasicProjectId()).collect(Collectors.toList());
            List<TjBasicCommonResultVo> tjBasicCommonResultVos = tjBasicCommonResultMapper.selectVoList(new LambdaQueryWrapper<TjBasicCommonResult>()
                .in(TjBasicCommonResult::getBasicProjectId, basicIds)
                .orderByAsc(TjBasicCommonResult::getSort)
            );
            if(CollUtil.isNotEmpty(tjBasicCommonResultVos)){
                tjRegBasicProjectVos.stream().forEach(m->{
                    List<TjBasicCommonResultVo> vos = tjBasicCommonResultVos.stream().filter(f -> Objects.equals(m.getBasicProjectId(), f.getBasicProjectId())).collect(Collectors.toList());
                    m.setBasicCommonResultVos(vos);
                });
            }
        }
        return tjRegBasicProjectVos;
    }

    @Override
    public List<TjRegCombinationProjectVo> queryRegCombinProjectList(Long id) {
        return baseMapper.queryRegCombinProjectList(id);
    }

    @Override
    public List<CheckItemResultVo> queryReportModel(Long regId) {
        return this.baseMapper.queryReportModel(regId);
    }

    @Override
    public List<TxmModel> queryByTxmModel(Long regId) {
        return this.baseMapper.queryByTxmModel(regId);
    }
}
