package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjCombinationProject;
import org.fxkc.peis.domain.TjCombinationProjectInfo;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.TjCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjCombinationProjectVo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjCombinationProjectInfoMapper;
import org.fxkc.peis.mapper.TjCombinationProjectMapper;
import org.fxkc.peis.service.ITjCombinationProjectService;
import org.fxkc.peis.service.ITjHazardFactorsRequireService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 体检组合项目Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjCombinationProjectServiceImpl implements ITjCombinationProjectService {

    private final TjCombinationProjectMapper baseMapper;

    private final TjCombinationProjectInfoMapper combinationProjectInfoMapper;

    private final ITjHazardFactorsRequireService iTjHazardFactorsRequireService;

    /**
     * 查询体检组合项目
     */
    @Override
    public TjCombinationProjectVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检组合项目列表
     */
    @Override
    public TableDataInfo<TjCombinationProjectListVo> queryPageList(TjCombinationProjectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjCombinationProject> lqw = buildQueryWrapper(bo);
        Page<TjCombinationProjectVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<TjCombinationProjectVo> resultRecords = result.getRecords();
        List<TjCombinationProjectListVo> convert = MapstructUtils.convert(resultRecords, TjCombinationProjectListVo.class);
        if(CollUtil.isNotEmpty(resultRecords)){
            //组合项目id集合
            List<Long> combinIds = resultRecords.stream().map(m -> m.getId()).collect(Collectors.toList());
            List<TjCombinationProjectInfoItemBo> basicProjectBycombinIds = baseMapper.getBasicProjectBycombinIds(combinIds);
            Map<Long, List<TjCombinationProjectInfoItemBo>> listMap = basicProjectBycombinIds.stream().collect(Collectors.groupingBy(TjCombinationProjectInfoItemBo::getCombinProjectId,
                Collectors.mapping(Function.identity(), Collectors.toList())));
            convert.stream().forEach(m->{
                m.setInfoItemBos(listMap.get(m.getId()));
            });
        }

        Page<TjCombinationProjectListVo> listVoPage = new Page<>(result.getCurrent(),result.getSize(),result.getTotal());
        listVoPage.setRecords(convert);
        return TableDataInfo.build(listVoPage);
    }

    /**
     * 查询体检组合项目列表
     */
    @Override
    public List<TjCombinationProjectVo> queryList(TjCombinationProjectBo bo) {
        LambdaQueryWrapper<TjCombinationProject> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjCombinationProject> buildQueryWrapper(TjCombinationProjectBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjCombinationProject> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCombinProjectCode()), TjCombinationProject::getCombinProjectCode, bo.getCombinProjectCode());
        lqw.like(StringUtils.isNotBlank(bo.getCombinProjectName()), TjCombinationProject::getCombinProjectName, bo.getCombinProjectName());
        lqw.like(StringUtils.isNotBlank(bo.getCombinSimpleName()), TjCombinationProject::getCombinSimpleName, bo.getCombinSimpleName());
        lqw.eq(StringUtils.isNotBlank(bo.getPySimpleCode()), TjCombinationProject::getPySimpleCode, bo.getPySimpleCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckType()), TjCombinationProject::getCheckType, bo.getCheckType());
        lqw.eq(bo.getKsId() != null, TjCombinationProject::getKsId, bo.getKsId());
        lqw.eq(StringUtils.isNotBlank(bo.getSampleType()), TjCombinationProject::getSampleType, bo.getSampleType());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecimenNeedFlag()), TjCombinationProject::getSpecimenNeedFlag, bo.getSpecimenNeedFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecimenType()), TjCombinationProject::getSpecimenType, bo.getSpecimenType());
        lqw.eq(bo.getStandardAmount() != null, TjCombinationProject::getStandardAmount, bo.getStandardAmount());
        lqw.eq(bo.getDiscount() != null, TjCombinationProject::getDiscount, bo.getDiscount());
        lqw.eq(StringUtils.isNotBlank(bo.getLisCode()), TjCombinationProject::getLisCode, bo.getLisCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPacsCode()), TjCombinationProject::getPacsCode, bo.getPacsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getHisCode()), TjCombinationProject::getHisCode, bo.getHisCode());
        lqw.eq(StringUtils.isNotBlank(bo.getZybCode()), TjCombinationProject::getZybCode, bo.getZybCode());
        lqw.eq(bo.getProjectSort() != null, TjCombinationProject::getProjectSort, bo.getProjectSort());
        lqw.eq(StringUtils.isNotBlank(bo.getSuitSex()), TjCombinationProject::getSuitSex, bo.getSuitSex());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectType()), TjCombinationProject::getProjectType, bo.getProjectType());
        lqw.eq(StringUtils.isNotBlank(bo.getFinancialType()), TjCombinationProject::getFinancialType, bo.getFinancialType());
        lqw.eq(StringUtils.isNotBlank(bo.getOutFlag()), TjCombinationProject::getOutFlag, bo.getOutFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getOutAddress()), TjCombinationProject::getOutAddress, bo.getOutAddress());
        lqw.eq(bo.getUseLimit() != null, TjCombinationProject::getUseLimit, bo.getUseLimit());
        lqw.eq(StringUtils.isNotBlank(bo.getGuideNotice()), TjCombinationProject::getGuideNotice, bo.getGuideNotice());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectClinicalMean()), TjCombinationProject::getProjectClinicalMean, bo.getProjectClinicalMean());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectDescribe()), TjCombinationProject::getProjectDescribe, bo.getProjectDescribe());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjCombinationProject::getStatus, bo.getStatus());
        lqw.orderByAsc(TjCombinationProject::getProjectSort).orderByDesc(TjCombinationProject::getCreateTime);
        return lqw;
    }

    /**
     * 新增体检组合项目
     */
    @Override
    public Boolean insertByBo(TjCombinationProjectAddBo bo) {
        TjCombinationProject add = MapstructUtils.convert(bo, TjCombinationProject.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        List<TjCombinationProjectInfoItemBo> infoItemBos = bo.getInfoItemBos();
        if(CollUtil.isNotEmpty(infoItemBos)){
            insertCombinationInfo(infoItemBos,bo);
        }
        return flag;
    }

    /**
     * 修改体检组合项目
     */
    @Override
    public Boolean updateByBo(TjCombinationProjectAddBo bo) {
        TjCombinationProject update = MapstructUtils.convert(bo, TjCombinationProject.class);
        validEntityBeforeSave(update);
        List<TjCombinationProjectInfoItemBo> infoItemBos = bo.getInfoItemBos();
        //先删除 再新增
        combinationProjectInfoMapper.delete(new LambdaQueryWrapper<TjCombinationProjectInfo>()
            .eq(TjCombinationProjectInfo::getCombinProjectId,bo.getId()));
        if(CollUtil.isNotEmpty(infoItemBos)){
            insertCombinationInfo(infoItemBos,bo);
        }
        return baseMapper.updateById(update) > 0;
    }

    public void insertCombinationInfo(List<TjCombinationProjectInfoItemBo> infoItemBos,TjCombinationProjectAddBo bo){
        List<TjCombinationProjectInfo> convert = MapstructUtils.convert(infoItemBos, TjCombinationProjectInfo.class);
        convert.stream().forEach(m->{m.setCombinProjectId(bo.getId());});
        combinationProjectInfoMapper.insertBatch(convert);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjCombinationProject entity){
        if(StringUtils.isNotEmpty(entity.getCombinProjectCode()) && !checkCodeUnique(entity)){
            throw new ServiceException("组合项目编码'" + entity.getCombinProjectCode() + "'已存在!");
        }

        if(StringUtils.isNotEmpty(entity.getCombinProjectName()) && !checkNameUnique(entity)){
            throw new ServiceException("组合项目名称'" + entity.getCombinProjectName() + "'已存在!");
        }
    }

    /**
     * 判断组合项目编码是否唯一
     */
    private boolean checkCodeUnique(TjCombinationProject entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjCombinationProject tjCombinationProject = baseMapper.selectOne(new LambdaQueryWrapper<TjCombinationProject>()
            .eq(TjCombinationProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjCombinationProject::getCombinProjectCode, entity.getCombinProjectCode())

        );
        if (ObjectUtil.isNotNull(tjCombinationProject) && tjCombinationProject.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 判断组合项目名称是否唯一
     */
    private boolean checkNameUnique(TjCombinationProject entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjCombinationProject tjCombinationProject = baseMapper.selectOne(new LambdaQueryWrapper<TjCombinationProject>()
            .eq(TjCombinationProject::getDelFlag, CommonConstants.NORMAL)
            .eq(TjCombinationProject::getCombinProjectName, entity.getCombinProjectName())

        );
        if (ObjectUtil.isNotNull(tjCombinationProject) && tjCombinationProject.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 批量删除体检组合项目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<TjCombinationProjectListVo> queryCompulsoryInspectionProject(TjCompulsoryInspectionProjectBo bo) {
        List<TjCombinationProjectVo> voList = baseMapper.selectVoList(Wrappers.lambdaQuery(TjCombinationProject.class)
            .eq(TjCombinationProject::getStatus, CommonConstants.NORMAL)
            .like(StrUtil.isNotBlank(bo.getCombinProjectName()), TjCombinationProject::getCombinProjectName, bo.getCombinProjectName()));
        List<TjCombinationProjectInfo> infoList = combinationProjectInfoMapper.selectList(Wrappers.lambdaQuery(TjCombinationProjectInfo.class)
            .in(TjCombinationProjectInfo::getBasicProjectId, bo.getItemIdList()));
        List<Long> combinIds = StreamUtils.toList(infoList, TjCombinationProjectInfo::getCombinProjectId);
        List<TjCombinationProjectListVo> convert = MapstructUtils.convert(voList, TjCombinationProjectListVo.class);
        if(CollUtil.isNotEmpty(combinIds)) {
            List<TjCombinationProjectInfoItemBo> basicProjectBycombinIds = baseMapper.getBasicProjectBycombinIds(combinIds);
            Map<Long, List<Long>> combinMap = basicProjectBycombinIds.stream().collect(Collectors.groupingBy(TjCombinationProjectInfoItemBo::getCombinProjectId,
                Collectors.mapping(TjCombinationProjectInfoItemBo::getBasicProjectId, Collectors.toList())));
            Map<Long, List<TjCombinationProjectInfoItemBo>> listMap = basicProjectBycombinIds.stream().collect(Collectors.groupingBy(TjCombinationProjectInfoItemBo::getCombinProjectId,
                Collectors.mapping(Function.identity(), Collectors.toList())));
            Map<Long, List<Long>> sortedMap = new LinkedHashMap<>();
            combinMap.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().size()))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
            List<Long> respCombinIds = findMinCompositeProjects(bo.getItemIdList(), sortedMap);
            convert.forEach(k -> k.setIsRequired(respCombinIds.contains(k.getId()))
                .setInfoItemBos(listMap.get(k.getId())));
        }
        return convert;
    }

    @Override
    public List<TjCombinationProjectListVo> queryOtherCompulsoryInspection(TjOtherCompulsoryInspectionBo bo) {
        List<TjCombinationProjectInfo> infoList = combinationProjectInfoMapper.selectList(Wrappers.lambdaQuery(TjCombinationProjectInfo.class)
            .eq(TjCombinationProjectInfo::getCombinProjectId, bo.getCombinProjectId()));
        List<TjHazardFactorsRequireVo.ItemBody> hazardList = iTjHazardFactorsRequireService
            .queryItemByFactorsCodeAndDutyStatus(MapstructUtils.convert(bo, TjHazardFactorsCodeBo.class));
        List<String> hazardItemList = hazardList.stream().map(TjHazardFactorsRequireVo.ItemBody::getItemId).toList();
        infoList = StreamUtils.filter(infoList, e -> hazardItemList.contains(String.valueOf(e.getBasicProjectId())));
        //获取此组合项目下面所有必检的基础项目
        List<String> infoItemList = StreamUtils.toList(infoList, e -> String.valueOf(e.getBasicProjectId()));
        bo.setItemList(infoItemList);
        List<String> itemList = baseMapper.queryOtherByCombinationProjectId(bo);
        //查询组合项目下面所有必检的基础项目所有组合项目
        List<TjCombinationProjectInfo> infoListNew = baseMapper.queryTjCombinationProjectInfoByBasicProjectId(itemList);
        Map<Long, List<TjCombinationProjectInfo>> groups = StreamUtils.groupByKey(infoListNew, TjCombinationProjectInfo::getCombinProjectId);
        List<Long> otherIdList = CollUtil.newArrayList();
        groups.forEach((k,v) -> {
            List<String> basicProjectIdList = StreamUtils.toList(v, e -> String.valueOf(e.getBasicProjectId()));
            if(!bo.getCombinProjectIdList().contains(k) && v.size() >= infoItemList.size() &&
                CollUtil.newHashSet(basicProjectIdList).containsAll(infoItemList)) {
                otherIdList.add(k);
            }
        });
        if(CollUtil.isEmpty(otherIdList)) {
            throw new PeisException(ErrorCodeConstants.PEIS_COMBINATION_NOT_EXIST);
        }
        List<TjCombinationProjectVo> voList = baseMapper.selectVoList(Wrappers.lambdaQuery(TjCombinationProject.class)
            .eq(TjCombinationProject::getStatus, CommonConstants.NORMAL)
            .in(TjCombinationProject::getId, otherIdList));
        List<TjCombinationProjectListVo> convert = MapstructUtils.convert(voList, TjCombinationProjectListVo.class);
        if(CollUtil.isEmpty(convert)) {
            throw new PeisException(ErrorCodeConstants.PEIS_COMBINATION_NOT_EXIST);
        }
        return convert;
    }

    @Override
    public String selectCombinationNameById(Long id) {
        TjCombinationProject tjCombinationProject = baseMapper.selectById(id);
        return Objects.isNull(tjCombinationProject) ? null : tjCombinationProject.getCombinProjectName();
    }

    @Override
    public String selectCombinationCodeById(Long id) {
        TjCombinationProject tjCombinationProject = baseMapper.selectById(id);
        return Objects.isNull(tjCombinationProject) ? null : tjCombinationProject.getCombinProjectCode();

    }

    private static List<Long> findMinCompositeProjects(List<Long> baseProjects, Map<Long, List<Long>> compositeProjects) {
        List<Long> minCompositeProjects = CollUtil.newArrayList();
        Set<Long> remainingBaseProjects = CollUtil.newHashSet(baseProjects);
        List<Long> currentCompositeProjects = CollUtil.newArrayList();
        dfs(compositeProjects, remainingBaseProjects, currentCompositeProjects, minCompositeProjects);
        return minCompositeProjects;
    }

    // 深度优先搜索
    private static void dfs(Map<Long, List<Long>> compositeProjects, Set<Long> remainingBaseProjects,
                            List<Long> currentCompositeProjects, List<Long> minCompositeProjects) {
        if (remainingBaseProjects.isEmpty()) {
            if (minCompositeProjects.isEmpty() || currentCompositeProjects.size() < minCompositeProjects.size()) {
                minCompositeProjects.clear();
                minCompositeProjects.addAll(currentCompositeProjects);
            }
            return;
        }
        for (Map.Entry<Long, List<Long>> entry : compositeProjects.entrySet()) {
            Long compositeProject = entry.getKey();
            List<Long> projects = entry.getValue();
            // 检查当前组合项目是否能够覆盖剩余的基础项目
            Set<Long> coveredBaseProjects = CollUtil.newHashSet(remainingBaseProjects);
            coveredBaseProjects.retainAll(projects);
            if (!coveredBaseProjects.isEmpty()) {
                // 更新剩余的基础项目和当前组合项目列表
                remainingBaseProjects.removeAll(coveredBaseProjects);
                currentCompositeProjects.add(compositeProject);
                // 递归调用dfs
                dfs(compositeProjects, remainingBaseProjects, currentCompositeProjects, minCompositeProjects);
                // 恢复剩余的基础项目和当前组合项目列表，进行下一个组合项目的尝试
                remainingBaseProjects.addAll(coveredBaseProjects);
                currentCompositeProjects.remove(compositeProject);
            }
        }
    }
}
