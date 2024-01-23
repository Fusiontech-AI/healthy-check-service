package org.fxkc.peis.service.impl;

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
import org.fxkc.peis.domain.TjBasicProject;
import org.fxkc.peis.domain.bo.TjBasicProjectBo;
import org.fxkc.peis.domain.vo.TjBasicProjectVo;
import org.fxkc.peis.mapper.TjBasicProjectMapper;
import org.fxkc.peis.service.ITjBasicProjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 体检基础项目Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjBasicProjectServiceImpl implements ITjBasicProjectService {

    private final TjBasicProjectMapper baseMapper;

    /**
     * 查询体检基础项目
     */
    @Override
    public TjBasicProjectVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检基础项目列表
     */
    @Override
    public TableDataInfo<TjBasicProjectVo> queryPageList(TjBasicProjectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjBasicProject> lqw = buildQueryWrapper(bo);
        Page<TjBasicProjectVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检基础项目列表
     */
    @Override
    public List<TjBasicProjectVo> queryList(TjBasicProjectBo bo) {
        LambdaQueryWrapper<TjBasicProject> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjBasicProject> buildQueryWrapper(TjBasicProjectBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjBasicProject> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getBasicProjectCode()), TjBasicProject::getBasicProjectCode, bo.getBasicProjectCode());
        lqw.like(StringUtils.isNotBlank(bo.getBasicProjectName()), TjBasicProject::getBasicProjectName, bo.getBasicProjectName());
        lqw.like(StringUtils.isNotBlank(bo.getBasicSimpleName()), TjBasicProject::getBasicSimpleName, bo.getBasicSimpleName());
        lqw.eq(bo.getKsId() != null, TjBasicProject::getKsId, bo.getKsId());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), TjBasicProject::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getResultType()), TjBasicProject::getResultType, bo.getResultType());
        lqw.eq(StringUtils.isNotBlank(bo.getResultGetWay()), TjBasicProject::getResultGetWay, bo.getResultGetWay());
        lqw.eq(StringUtils.isNotBlank(bo.getSuitSex()), TjBasicProject::getSuitSex, bo.getSuitSex());
        lqw.eq(StringUtils.isNotBlank(bo.getDefaultValue()), TjBasicProject::getDefaultValue, bo.getDefaultValue());
        lqw.eq(bo.getProjectSort() != null, TjBasicProject::getProjectSort, bo.getProjectSort());
        lqw.eq(StringUtils.isNotBlank(bo.getEnterSummary()), TjBasicProject::getEnterSummary, bo.getEnterSummary());
        lqw.eq(StringUtils.isNotBlank(bo.getEnterReport()), TjBasicProject::getEnterReport, bo.getEnterReport());
        lqw.eq(StringUtils.isNotBlank(bo.getLisCode()), TjBasicProject::getLisCode, bo.getLisCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPacsCode()), TjBasicProject::getPacsCode, bo.getPacsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getHisCode()), TjBasicProject::getHisCode, bo.getHisCode());
        lqw.eq(StringUtils.isNotBlank(bo.getZybCode()), TjBasicProject::getZybCode, bo.getZybCode());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjBasicProject::getStatus, bo.getStatus());
        lqw.isNotNull(bo.getIsOccupational(), TjBasicProject::getZybCode);
        return lqw;
    }

    /**
     * 新增体检基础项目
     */
    @Override
    public Boolean insertByBo(TjBasicProjectBo bo) {
        TjBasicProject add = MapstructUtils.convert(bo, TjBasicProject.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检基础项目
     */
    @Override
    public Boolean updateByBo(TjBasicProjectBo bo) {
        TjBasicProject update = MapstructUtils.convert(bo, TjBasicProject.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjBasicProject entity){
        if(StringUtils.isNotEmpty(entity.getBasicProjectCode()) && !checkCodeUnique(entity)){
            throw new ServiceException("基础项目编码'" + entity.getBasicProjectCode() + "'已存在!");
        }

        if(StringUtils.isNotEmpty(entity.getBasicProjectName()) && !checkNameUnique(entity)){
            throw new ServiceException("基础项目名称'" + entity.getBasicProjectName() + "'已存在!");
        }
    }

    /**
     * 判断基础项目编码是否唯一
     */
    private boolean checkCodeUnique(TjBasicProject entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjBasicProject tjBasicProject = baseMapper.selectOne(new LambdaQueryWrapper<TjBasicProject>()
            .eq(TjBasicProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjBasicProject::getBasicProjectCode, entity.getBasicProjectCode())

        );
        if (ObjectUtil.isNotNull(tjBasicProject) && tjBasicProject.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 判断基础项目名称是否唯一
     */
    private boolean checkNameUnique(TjBasicProject entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjBasicProject tjBasicProject = baseMapper.selectOne(new LambdaQueryWrapper<TjBasicProject>()
            .eq(TjBasicProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjBasicProject::getBasicProjectName, entity.getBasicProjectName())

        );
        if (ObjectUtil.isNotNull(tjBasicProject) && tjBasicProject.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 批量删除体检基础项目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
