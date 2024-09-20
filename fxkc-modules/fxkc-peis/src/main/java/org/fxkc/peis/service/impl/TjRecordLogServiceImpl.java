package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjRecordLog;
import org.fxkc.peis.domain.bo.TjRecordLogBo;
import org.fxkc.peis.domain.vo.TjRecordLogVo;
import org.fxkc.peis.mapper.TjRecordLogMapper;
import org.fxkc.peis.service.ITjRecordLogService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 体检操作记录日志Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
@RequiredArgsConstructor
@Service
public class TjRecordLogServiceImpl implements ITjRecordLogService {

    private final TjRecordLogMapper baseMapper;

    /**
     * 查询体检操作记录日志
     */
    @Override
    public TjRecordLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检操作记录日志列表
     */
    @Override
    public TableDataInfo<TjRecordLogVo> queryPageList(TjRecordLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjRecordLog> lqw = buildQueryWrapper(bo);
        Page<TjRecordLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检操作记录日志列表
     */
    @Override
    public List<TjRecordLogVo> queryList(TjRecordLogBo bo) {
        LambdaQueryWrapper<TjRecordLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjRecordLog> buildQueryWrapper(TjRecordLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjRecordLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getHealthyCheckCode()), TjRecordLog::getHealthyCheckCode, bo.getHealthyCheckCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCredentialNumber()), TjRecordLog::getCredentialNumber, bo.getCredentialNumber());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TjRecordLog::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getOperType()), TjRecordLog::getOperType, bo.getOperType());
        lqw.eq(StringUtils.isNotBlank(bo.getOperDesc()), TjRecordLog::getOperDesc, bo.getOperDesc());
        return lqw;
    }

    /**
     * 新增体检操作记录日志
     */
    @Override
    public Boolean insertByBo(TjRecordLogBo bo) {
        TjRecordLog add = MapstructUtils.convert(bo, TjRecordLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检操作记录日志
     */
    @Override
    public Boolean updateByBo(TjRecordLogBo bo) {
        TjRecordLog update = MapstructUtils.convert(bo, TjRecordLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjRecordLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检操作记录日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
