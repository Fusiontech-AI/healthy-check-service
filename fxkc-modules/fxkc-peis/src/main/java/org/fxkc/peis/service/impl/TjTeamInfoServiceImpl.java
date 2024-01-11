package org.fxkc.peis.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.peis.domain.bo.TjTeamInfoBo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;
import org.fxkc.peis.enums.TeamLevelEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjTeamInfoMapper;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 体检单位信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjTeamInfoServiceImpl extends ServiceImpl<TjTeamInfoMapper, TjTeamInfo> implements ITjTeamInfoService {

    /**
     * 查询体检单位信息
     */
    @Override
    public TjTeamInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检单位信息列表
     */
    @Override
    public TableDataInfo<TjTeamInfoVo> queryPageList(TjTeamInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamInfo> lqw = buildQueryWrapper(bo);
        Page<TjTeamInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位信息列表
     */
    @Override
    public List<TjTeamInfoVo> queryList(TjTeamInfoBo bo) {
        return baseMapper.selectVoList(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<TjTeamInfo> buildQueryWrapper(TjTeamInfoBo bo) {
        LambdaQueryWrapper<TjTeamInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.and(StrUtil.isNotBlank(bo.getTeamName()), wrapper -> wrapper.like(TjTeamInfo::getTeamName, bo.getTeamName())
                .or().like(TjTeamInfo::getShortName, bo.getTeamName())
                .or().like(TjTeamInfo::getPhoneticCode, bo.getTeamName().toUpperCase()))
            .eq(Objects.nonNull(bo.getTeamLevel()), TjTeamInfo::getTeamLevel, bo.getTeamLevel())
            .select(TjTeamInfo::getId, TjTeamInfo::getTeamName, TjTeamInfo::getParentId)
            .orderByAsc(TjTeamInfo::getId);
        return queryWrapper;
    }

    /**
     * 新增体检单位信息
     */
    @Override
    public TjTeamInfo insertByBo(TjTeamInfoBo bo) {
        TjTeamInfo add = MapstructUtils.convert(bo, TjTeamInfo.class);
        if(Objects.equals(TeamLevelEnum.ONE.getCode(), bo.getTeamLevel())) {
            add.setParentId(0L);
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        return baseMapper.selectById(add.getId());
    }

    /**
     * 修改体检单位信息
     */
    @Override
    public TjTeamInfo updateByBo(TjTeamInfoBo bo) {
        TjTeamInfo update = MapstructUtils.convert(bo, TjTeamInfo.class);
        if(Objects.equals(TeamLevelEnum.ONE.getCode(), bo.getTeamLevel())) {
            update.setParentId(0L);
        }
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        return baseMapper.selectById(update.getId());
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamInfo entity){
        if(Objects.equals(TeamLevelEnum.TWO.getCode(), entity.getTeamLevel())
            && Objects.isNull(entity.getParentId())) {
            throw new PeisException(ErrorCodeConstants.PEIS_PARENTID_NOT_BLANK);
        }
    }

    /**
     * 批量删除体检单位信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public String getTeamNoById(Long id) {
        long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamInfo.class)
            .eq(TjTeamInfo::getParentId, id));
        String maxNo;
        //获取一级单位编码
        if(Objects.equals(0L, id)) {
            maxNo = String.valueOf(DateUtil.year(DateUtil.date()))
                .concat(StringUtils.zeroPrefix(String.valueOf(count + 1), 2));
        }else {
            TjTeamInfo tjTeamInfo = baseMapper.selectById(id);
            if(Objects.isNull(tjTeamInfo)) {
                throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMINFO_NOT_EMPTY);
            }
            maxNo = tjTeamInfo.getTeamNo()
                .concat(StringUtils.zeroPrefix(String.valueOf(count + 1), 2));
        }
        return maxNo;
    }

}
