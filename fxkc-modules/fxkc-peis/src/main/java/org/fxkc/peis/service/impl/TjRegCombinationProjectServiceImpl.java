package org.fxkc.peis.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetItemVo;
import org.fxkc.peis.enums.CheckStatusEnum;
import org.fxkc.peis.mapper.TjRegBasicProjectMapper;
import org.fxkc.peis.mapper.TjRegCombinationProjectMapper;
import org.fxkc.peis.service.ITjRegCombinationProjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
        return tjRegBasicProjectMapper.queryRegBasicProjectList(id);
    }

    @Override
    public List<TjRegCombinationProjectVo> queryRegCombinProjectList(Long id) {
        return baseMapper.queryRegCombinProjectList(id);
    }
}
