package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.core.utils.TreeBuildUtils;
import org.fxkc.common.json.utils.JsonUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjTeamDept;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.peis.domain.bo.TjTeamDeptBo;
import org.fxkc.peis.domain.vo.TjTeamDeptVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjTeamDeptMapper;
import org.fxkc.peis.mapper.TjTeamInfoMapper;
import org.fxkc.peis.service.ITjTeamDeptService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 单位部门信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjTeamDeptServiceImpl extends ServiceImpl<TjTeamDeptMapper, TjTeamDept> implements ITjTeamDeptService {

    private final TjTeamInfoMapper tjTeamInfoMapper;

    /**
     * 查询单位部门信息
     */
    @Override
    public TjTeamDeptVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询单位部门信息列表
     */
    @Override
    public TableDataInfo<TjTeamDeptVo> queryPageList(TjTeamDeptBo bo, PageQuery pageQuery) {
        Page<TjTeamDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), buildQueryWrapper(bo));
        List<TjTeamInfo> infoList = tjTeamInfoMapper.selectList();
        //递归查询上级单位存入map
        Map<Long, String> map = MapUtil.newHashMap();
        result.getRecords().forEach(k -> {
            if(map.containsKey(k.getTeamId())) {
                k.setTeamName(map.get(k.getTeamId()));
            }else {
                Optional<TjTeamInfo> tjTeamInfo = infoList.stream().filter(e -> Objects.equals(k.getTeamId(), e.getId())).findFirst();
                if(tjTeamInfo.isPresent()) {
                    List<TjTeamInfo> nodeList = findSuperTeamName(infoList, tjTeamInfo.get());
                    String teamName = nodeList.stream().sorted(Comparator.comparing(TjTeamInfo::getCreateTime)).map(TjTeamInfo::getTeamName)
                        .collect(Collectors.joining(StrUtil.SLASH));
                    k.setTeamName(teamName);
                    map.put(k.getTeamId(), teamName);
                }
            }
        });
        return TableDataInfo.build(result);
    }

    private static List<TjTeamInfo> findSuperTeamName(List<TjTeamInfo> infoList, TjTeamInfo tjTeamInfo) {
        List<TjTeamInfo> respList = CollUtil.newArrayList();
        respList.add(tjTeamInfo);
        if(!Objects.equals(0L, tjTeamInfo.getParentId())) {
            Optional<TjTeamInfo> info = infoList.stream().filter(e ->
                Objects.equals(e.getId(), tjTeamInfo.getParentId())).findFirst();
            info.ifPresent(teamInfo -> respList.addAll(findSuperTeamName(infoList, teamInfo)));
        }
        return respList;
    }


    /**
     * 查询单位部门信息列表
     */
    @Override
    public List<TjTeamDeptVo> queryList(TjTeamDeptBo bo) {
        LambdaQueryWrapper<TjTeamDept> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamDept> buildQueryWrapper(TjTeamDeptBo bo) {
        LambdaQueryWrapper<TjTeamDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(Objects.nonNull(bo.getTeamId()), TjTeamDept::getTeamId, bo.getTeamId());
        lqw.eq(StrUtil.isNotBlank(bo.getDeptNo()), TjTeamDept::getDeptNo, bo.getDeptNo());
        lqw.like(StrUtil.isNotBlank(bo.getDeptName()), TjTeamDept::getDeptName, bo.getDeptName());
        lqw.eq(StrUtil.isNotBlank(bo.getDeptManager()), TjTeamDept::getDeptManager, bo.getDeptManager());
        return lqw;
    }

    /**
     * 新增单位部门信息
     */
    @Override
    public Boolean insertByBo(TjTeamDeptBo bo) {
        TjTeamDept add = MapstructUtils.convert(bo, TjTeamDept.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改单位部门信息
     */
    @Override
    public Boolean updateByBo(TjTeamDeptBo bo) {
        TjTeamDept update = MapstructUtils.convert(bo, TjTeamDept.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamDept entity){
        //TODO 做一些数据校验,如唯一约束
        long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamDept.class)
            .eq(TjTeamDept::getTeamId, entity.getTeamId())
            .eq(TjTeamDept::getDeptName, entity.getDeptName()));
        if(count > 0) {
            throw new PeisException(ErrorCodeConstants.PEIS_DEPTNAME_ISEXIST, entity.getDeptName());
        }
    }

    /**
     * 批量删除单位部门信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public String getDeptNoById(Long id) {
        TjTeamInfo tjTeamInfo = tjTeamInfoMapper.selectById(id);
        if(Objects.isNull(tjTeamInfo)) {
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMINFO_NOT_EMPTY);
        }
        long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamDept.class)
            .eq(TjTeamDept::getTeamId, id));
        return tjTeamInfo.getTeamNo().concat(StringUtils.zeroPrefix(String.valueOf(count + 1), 2));
    }
}
