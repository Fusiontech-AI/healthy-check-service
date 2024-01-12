package org.fxkc.peis.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.exception.PeisException;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.peis.domain.vo.TjTeamSettleVo;
import org.fxkc.peis.domain.TjTeamSettle;
import org.fxkc.peis.mapper.TjTeamSettleMapper;
import org.fxkc.peis.service.ITjTeamSettleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检单位结账信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@RequiredArgsConstructor
@Service
public class TjTeamSettleServiceImpl implements ITjTeamSettleService {

    private final TjTeamSettleMapper baseMapper;

    /**
     * 查询体检单位结账信息
     */
    @Override
    public TjTeamSettleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检单位结账信息列表
     */
    @Override
    public TableDataInfo<TjTeamSettleVo> queryPageList(TjTeamSettleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamSettle> lqw = buildQueryWrapper(bo);
        Page<TjTeamSettleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位结账信息列表
     */
    @Override
    public List<TjTeamSettleVo> queryList(TjTeamSettleBo bo) {
        LambdaQueryWrapper<TjTeamSettle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamSettle> buildQueryWrapper(TjTeamSettleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTeamSettle> lqw = Wrappers.lambdaQuery();
        lqw.eq(ObjectUtil.isNotNull(bo.getTeamId()), TjTeamSettle::getTeamId, bo.getTeamId());
        lqw.eq(ObjectUtil.isNotNull(bo.getTeamTaskId()), TjTeamSettle::getTeamTaskId, bo.getTeamTaskId());
        return lqw;
    }

    /**
     * 新增体检单位结账信息
     */
    @Override
    public Boolean insertByBo(TjTeamSettleBo bo) {
        TjTeamSettle add = MapstructUtils.convert(bo, TjTeamSettle.class);
        validEntityBeforeSave(add);
        add.setChargeNumber("1");
        add.setSettleOfficer(LoginHelper.getLoginUser().getNickname());
        return baseMapper.insert(add) > 0;
    }

    @Override
    public Boolean teamInvoice(Collection<Long> ids) {
        TjTeamSettle update = new TjTeamSettle();
        validEntityBeforeSave(update);
        update.setPrintInvoice("1");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    @Override
    public Boolean teamInvalidInvoice(Collection<Long> ids) {
        TjTeamSettle update = new TjTeamSettle();
        validEntityBeforeSave(update);
        update.setPrintInvoice("0");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    @Override
    public Boolean teamInvalidSettle(Collection<Long> ids) {
        TjTeamSettle update = new TjTeamSettle();
        validEntityBeforeSave(update);
        update.setStatus("2");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    @Override
    public Boolean teamSettleCheckPass(Collection<Long> ids) {
        validEntityBeforeCheck(ids);
        TjTeamSettle update = new TjTeamSettle();
        update.setCheckStatus("1");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    @Override
    public Boolean teamSettleCheckReject(Collection<Long> ids) {
        validEntityBeforeCheck(ids);
        TjTeamSettle update = new TjTeamSettle();
        update.setCheckStatus("2");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    /**
     * 结账审核前的数据校验
     */
    private void validEntityBeforeCheck(Collection<Long> ids){
        List<TjTeamSettle> tjTeamSettleList = baseMapper.selectList(new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids));
        long notNormalStatusCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getStatus(),CommonConstants.NORMAL)).count();
        if(notNormalStatusCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_STATUS_REFRESH);
        }
        long notNormalCheckStatusCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getCheckStatus(),CommonConstants.NORMAL)).count();
        if(notNormalCheckStatusCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_CHECKSTATUS_REFRESH);
        }
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamSettle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检单位结账信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            Long count = baseMapper.selectCount(new LambdaUpdateWrapper<TjTeamSettle>()
                .in(TjTeamSettle::getId,ids)
                .eq(TjTeamSettle::getStatus, CommonConstants.NORMAL));
            if(count > 0){
                throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_FIRST_VOID);
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
