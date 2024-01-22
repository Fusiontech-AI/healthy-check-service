package org.fxkc.peis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjHazardFactorsItem;
import org.fxkc.peis.domain.TjBasicProject;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireSaveBo;
import org.fxkc.peis.enums.AssociationTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjHazardFactorsItemMapper;
import org.fxkc.peis.mapper.TjBasicProjectMapper;
import org.fxkc.peis.mapper.TjOccupationalDictMapper;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.peis.domain.TjHazardFactorsRequire;
import org.fxkc.peis.mapper.TjHazardFactorsRequireMapper;
import org.fxkc.peis.service.ITjHazardFactorsRequireService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 危害因素必检项目主Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@RequiredArgsConstructor
@Service
public class TjHazardFactorsRequireServiceImpl extends ServiceImpl<TjHazardFactorsRequireMapper, TjHazardFactorsRequire> implements ITjHazardFactorsRequireService {


    private final TjHazardFactorsItemMapper tjHazardFactorsItemMapper;

    private final TjBasicProjectMapper tjBasicProjectMapper;

    private final TjOccupationalDictMapper tjOccupationalDictMapper;


    @Override
    public TjHazardFactorsRequireVo hazardFactorsQuery(TjHazardFactorsRequireBo bo, PageQuery pageQuery) {
        TjHazardFactorsRequireVo hazardFactorsRequireVo = new TjHazardFactorsRequireVo();
        if(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode())) {
            Page<TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo> page = baseMapper.hazardFactorsQueryPage(
                pageQuery.build(), bo);
            hazardFactorsRequireVo.setPageVo(TableDataInfo.build(page));
        }else {
            TjHazardFactorsRequire hazardFactorsRequire = baseMapper.selectOne(Wrappers.lambdaQuery(TjHazardFactorsRequire.class)
                .eq(TjHazardFactorsRequire::getHazardFactorsCode, bo.getHazardFactorsCode())
                .eq(TjHazardFactorsRequire::getAssociationType, bo.getAssociationType()));
            TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo vo = BeanUtil.toBean(hazardFactorsRequire, TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo.class);
            hazardFactorsRequireVo.setVo(vo);
        }
        return hazardFactorsRequireVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(TjHazardFactorsRequireSaveBo bo) {
        TjHazardFactorsRequire hazardFactorsRequire = BeanUtil.toBean(bo, TjHazardFactorsRequire.class);
        if(ObjectUtil.notEqual(AssociationTypeEnum.EVALUATION_CRITERION.getCode(), bo.getAssociationType())
            && StrUtil.isBlank(bo.getDutyStatus())) {
            throw new PeisException(ErrorCodeConstants.PEIS_HAZARD_STATUS_NOT_EMPTY);
        }
        if(StrUtil.isBlank(bo.getId())) {
            String msg = StrUtil.EMPTY;
            LambdaQueryWrapper<TjHazardFactorsRequire> wrapper =  Wrappers.lambdaQuery(TjHazardFactorsRequire.class)
                .eq(TjHazardFactorsRequire::getHazardFactorsCode, bo.getHazardFactorsCode())
                .eq(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode()),
                    TjHazardFactorsRequire::getDutyStatus, bo.getDutyStatus())
                .eq(TjHazardFactorsRequire::getAssociationType, bo.getAssociationType());
            if(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode())) {
                msg = "在岗状态";
            }
            if(Objects.equals("14", bo.getSortCode())) {
                wrapper.eq(TjHazardFactorsRequire::getShineSource, bo.getShineSource())
                    .eq(TjHazardFactorsRequire::getShineType, bo.getShineType());
                msg = msg.concat("、照射源、职业照射种类");
            }
            long count = baseMapper.selectCount(wrapper);
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_HAZARD_STATUS_ISEXIST, msg);
            }
            baseMapper.insert(hazardFactorsRequire);
            //必检项目、目标职业病、职业禁忌症
            if(AssociationTypeEnum.getCheckList().contains(bo.getAssociationType())) {
                bo.getItemList().forEach(k -> tjHazardFactorsItemMapper.insert(new TjHazardFactorsItem().setItemId(k)
                    .setFactorsId(hazardFactorsRequire.getId())));
            }
        }else{
            baseMapper.updateById(hazardFactorsRequire);
            if(AssociationTypeEnum.getCheckList().contains(bo.getAssociationType())) {
                List<TjHazardFactorsItem> list = tjHazardFactorsItemMapper.selectList(Wrappers.lambdaQuery(TjHazardFactorsItem.class)
                    .eq(TjHazardFactorsItem::getFactorsId, hazardFactorsRequire.getId()));
                List<Long> itemIdList = list.stream().map(TjHazardFactorsItem::getItemId).collect(Collectors.toList());
                if(!CollectionUtils.isEqualCollection(itemIdList, bo.getItemList()) &&
                    ObjectUtil.equal(AssociationTypeEnum.REQUIRED_ITEM.getCode(), bo.getAssociationType())) {
                    long count = baseMapper.queryIsExistRequiredItem(bo);
                    if(count > 0) {
                        throw new PeisException(ErrorCodeConstants.PEIS_HAZARD_PACKAGE_ISEXIST);
                    }
                }
                //新增
                List<Long> insList = bo.getItemList().stream()
                    .filter(e -> !itemIdList.contains(e)).toList();
                insList.forEach(k -> tjHazardFactorsItemMapper.insert(new TjHazardFactorsItem().setFactorsId(hazardFactorsRequire.getId())
                    .setItemId(k)));
                //删除
                List<Long> delList = list.stream().filter(e -> !bo.getItemList().contains(e.getItemId()))
                    .map(TjHazardFactorsItem::getId).toList();
                delList.forEach(tjHazardFactorsItemMapper::deleteById);
                //更新
                List<TjHazardFactorsItem> upList = list.stream().filter(e -> bo.getItemList().contains(e.getItemId())).toList();
                upList.forEach(tjHazardFactorsItemMapper::updateById);
            }
        }
    }

    @Override
    public TjHazardFactorsRequireVo hazardFactorsDetail(String id) {
        TjHazardFactorsRequire hazardFactorsRequire = baseMapper.selectById(id);
        TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo vo = BeanUtil.toBean(hazardFactorsRequire, TjHazardFactorsRequireVo.HazardFactorsRequireQueryVo.class);
        List<TjHazardFactorsItem> list = tjHazardFactorsItemMapper.selectList(Wrappers.lambdaQuery(TjHazardFactorsItem.class)
            .eq(TjHazardFactorsItem::getFactorsId, id));
        if(CollUtil.isNotEmpty(list)) {
            List<TjHazardFactorsRequireVo.ItemBody>  bodyList = new ArrayList<>();
            if(ObjectUtil.equal(hazardFactorsRequire.getAssociationType(), AssociationTypeEnum.REQUIRED_ITEM.getCode())) {
                List<TjBasicProject> itemList = tjBasicProjectMapper.selectBatchIds(list.stream()
                    .map(TjHazardFactorsItem::getItemId).collect(Collectors.toList()));
                itemList.forEach(k -> bodyList.add(new TjHazardFactorsRequireVo.ItemBody()
                    .setItemId(k.getId())
                    .setName(k.getZybCode().concat(k.getBasicProjectName()))));
            }else if(ObjectUtil.equal(hazardFactorsRequire.getAssociationType(),
                AssociationTypeEnum.TARGET_OCCUPATIONAL.getCode()) ||
                ObjectUtil.equal(hazardFactorsRequire.getAssociationType(),
                    AssociationTypeEnum.OCCUPATIONAL_CONTRAINDICATIONS.getCode())){
                List<TjOccupationalDict> dictList = tjOccupationalDictMapper.selectBatchIds(list.stream()
                    .map(TjHazardFactorsItem::getItemId).collect(Collectors.toList()));
                dictList.forEach(k -> bodyList.add(new TjHazardFactorsRequireVo.ItemBody()
                    .setItemId(k.getId())
                    .setName(k.getCode().concat(k.getValue()))));
            }
            vo.setItemList(bodyList);
        }
        return new TjHazardFactorsRequireVo().setVo(vo);
    }

    @Override
    public Boolean deleteById(Long id) {
        tjHazardFactorsItemMapper.delete(Wrappers.lambdaUpdate(new TjHazardFactorsItem().setFactorsId(id)));
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDeleteByIds(List<Long> list) {
        list.forEach(k -> tjHazardFactorsItemMapper.delete(Wrappers.lambdaUpdate(TjHazardFactorsItem.class)
                .eq(TjHazardFactorsItem::getFactorsId, k)));
        return baseMapper.deleteBatchIds(list) > 0;
    }
}
