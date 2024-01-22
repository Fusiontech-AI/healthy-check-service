package org.fxkc.peis.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.enums.RegisterStatusEnum;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjRegisterBo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.mapper.TjRegisterMapper;
import org.fxkc.peis.service.ITjRegisterService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检人员登记信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@RequiredArgsConstructor
@Service
public class TjRegisterServiceImpl implements ITjRegisterService {

    private final TjRegisterMapper baseMapper;

    /**
     * 查询体检人员登记信息
     */
    @Override
    public TjRegisterVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检人员登记信息列表
     */
    @Override
    public TableDataInfo<TjRegisterVo> queryPageList(TjRegisterBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjRegister> lqw = buildQueryWrapper(bo);
        Page<TjRegisterVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检人员登记信息列表
     */
    @Override
    public List<TjRegisterVo> queryList(TjRegisterBo bo) {
        LambdaQueryWrapper<TjRegister> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjRegister> buildQueryWrapper(TjRegisterBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjRegister> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getHealthyCheckCode()), TjRegister::getHealthyCheckCode, bo.getHealthyCheckCode());
        lqw.eq(StringUtils.isNotBlank(bo.getRecordCode()), TjRegister::getRecordCode, bo.getRecordCode());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TjRegister::getName, bo.getName());
        lqw.eq(bo.getAge() != null, TjRegister::getAge, bo.getAge());
        lqw.eq(StringUtils.isNotBlank(bo.getGender()), TjRegister::getGender, bo.getGender());
        lqw.eq(StringUtils.isNotBlank(bo.getMarriageStatus()), TjRegister::getMarriageStatus, bo.getMarriageStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getCredentialType()), TjRegister::getCredentialType, bo.getCredentialType());
        lqw.eq(StringUtils.isNotBlank(bo.getCredentialNumber()), TjRegister::getCredentialNumber, bo.getCredentialNumber());
        lqw.eq(bo.getBirthday() != null, TjRegister::getBirthday, bo.getBirthday());
        lqw.eq(StringUtils.isNotBlank(bo.getNation()), TjRegister::getNation, bo.getNation());
        lqw.eq(StringUtils.isNotBlank(bo.getPhysicalType()), TjRegister::getPhysicalType, bo.getPhysicalType());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), TjRegister::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getUserImage()), TjRegister::getUserImage, bo.getUserImage());
        lqw.eq(StringUtils.isNotBlank(bo.getNeedGeneralReview()), TjRegister::getNeedGeneralReview, bo.getNeedGeneralReview());
        lqw.eq(StringUtils.isNotBlank(bo.getRecipient()), TjRegister::getRecipient, bo.getRecipient());
        lqw.eq(StringUtils.isNotBlank(bo.getReceiptPhone()), TjRegister::getReceiptPhone, bo.getReceiptPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getPostAddress()), TjRegister::getPostAddress, bo.getPostAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getBusinessType()), TjRegister::getBusinessType, bo.getBusinessType());
        lqw.eq(StringUtils.isNotBlank(bo.getGuideSheetRecived()), TjRegister::getGuideSheetRecived, bo.getGuideSheetRecived());
        lqw.eq(StringUtils.isNotBlank(bo.getFreezeStatus()), TjRegister::getFreezeStatus, bo.getFreezeStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getHealthyCheckStatus()), TjRegister::getHealthyCheckStatus, bo.getHealthyCheckStatus());
        lqw.eq(bo.getTeamId() != null, TjRegister::getTeamId, bo.getTeamId());
        lqw.eq(bo.getTeamGroupId() != null, TjRegister::getTeamGroupId, bo.getTeamGroupId());
        lqw.eq(bo.getTeamDeptId() != null, TjRegister::getTeamDeptId, bo.getTeamDeptId());
        lqw.eq(bo.getIntroducer() != null, TjRegister::getIntroducer, bo.getIntroducer());
        lqw.eq(bo.getReporter() != null, TjRegister::getReporter, bo.getReporter());
        lqw.eq(bo.getGeneralReviewDoctor() != null, TjRegister::getGeneralReviewDoctor, bo.getGeneralReviewDoctor());
        lqw.eq(bo.getGeneralReviewTime() != null, TjRegister::getGeneralReviewTime, bo.getGeneralReviewTime());
        lqw.eq(bo.getHealthyCheckTime() != null, TjRegister::getHealthyCheckTime, bo.getHealthyCheckTime());
        lqw.eq(bo.getAuditDoctor() != null, TjRegister::getAuditDoctor, bo.getAuditDoctor());
        lqw.eq(bo.getAuditTime() != null, TjRegister::getAuditTime, bo.getAuditTime());
        lqw.eq(bo.getFinishTime() != null, TjRegister::getFinishTime, bo.getFinishTime());
        lqw.eq(bo.getCancelRegisterTime() != null, TjRegister::getCancelRegisterTime, bo.getCancelRegisterTime());
        lqw.eq(bo.getCancelRegisterOperator() != null, TjRegister::getCancelRegisterOperator, bo.getCancelRegisterOperator());
        return lqw;
    }

    /**
     * 新增体检人员登记信息
     */
    @Override
    public Boolean insertByBo(TjRegisterBo bo) {
        TjRegister add = MapstructUtils.convert(bo, TjRegister.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检人员登记信息
     */
    @Override
    public Boolean updateByBo(TjRegisterBo bo) {
        TjRegister update = MapstructUtils.convert(bo, TjRegister.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjRegister entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检人员登记信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 批量取消体检人员登记
     * @param ids
     * @return
     */
    @Override
    public Boolean cancelRegistration(Collection<Long> ids) {
        LambdaQueryWrapper<TjRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TjRegister::getId,ids)
            .eq(TjRegister::getDelFlag, CommonConstants.NORMAL)
            .in(TjRegister::getHealthyCheckStatus, Arrays.asList(HealthyCheckTypeEnum.预约.getCode(),HealthyCheckTypeEnum.登记.getCode()));
        Long actualCount = baseMapper.selectCount(wrapper);
        if(ids.size() - actualCount != 0){
            throw new ServiceException("仅限预约、登记状态人员可取消登记，请重新选择！");
        }
        //TODO 逻辑删除该人员登记时的项目信息

        return baseMapper.update(TjRegister.builder()
                .status(RegisterStatusEnum.取消登记.getCode())
                .delFlag(CommonConstants.DISABLE)
            .cancelRegisterOperator(LoginHelper.getLoginUser().getUserId())
            .cancelRegisterTime(DateUtil.date()).build()
            ,Wrappers.lambdaQuery(TjRegister.class).in(TjRegister::getId,ids))>0;
    }

    @Override
    public Boolean reRegistration(Collection<Long> ids) {
        //TODO 恢复该人员登记时的项目信息

        return baseMapper.update(TjRegister.builder()
                .status(RegisterStatusEnum.正常.getCode()).delFlag(CommonConstants.NORMAL).build()
            ,Wrappers.lambdaQuery(TjRegister.class).in(TjRegister::getId,ids))>0;
    }
}
