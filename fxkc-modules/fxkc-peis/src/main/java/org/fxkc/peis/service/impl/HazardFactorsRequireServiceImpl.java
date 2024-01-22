package org.fxkc.peis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.HazardFactorsItem;
import org.fxkc.peis.domain.TjBasicProject;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.peis.domain.bo.HazardFactorsRequireSaveBo;
import org.fxkc.peis.enums.AssociationTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.HazardFactorsItemMapper;
import org.fxkc.peis.mapper.TjBasicProjectMapper;
import org.fxkc.peis.mapper.TjOccupationalDictMapper;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.mapper.HazardFactorsRequireMapper;
import org.fxkc.peis.service.IHazardFactorsRequireService;
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
public class HazardFactorsRequireServiceImpl extends ServiceImpl<HazardFactorsRequireMapper, HazardFactorsRequire> implements IHazardFactorsRequireService {


    private final HazardFactorsItemMapper hazardFactorsItemMapper;

    private final TjBasicProjectMapper tjBasicProjectMapper;

    private final TjOccupationalDictMapper tjOccupationalDictMapper;


    @Override
    public HazardFactorsRequireVo hazardFactorsQuery(HazardFactorsRequireBo bo, PageQuery pageQuery) {
        HazardFactorsRequireVo hazardFactorsRequireVo = new HazardFactorsRequireVo();
        if(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode())) {
            Page<HazardFactorsRequireVo.HazardFactorsRequireQueryVo> page = baseMapper.hazardFactorsQueryPage(
                pageQuery.build(), bo);
            hazardFactorsRequireVo.setPageVo(TableDataInfo.build(page));
        }else {
            HazardFactorsRequire hazardFactorsRequire = baseMapper.selectOne(Wrappers.lambdaQuery(HazardFactorsRequire.class)
                .eq(HazardFactorsRequire::getHazardFactorsCode, bo.getHazardFactorsCode())
                .eq(HazardFactorsRequire::getAssociationType, bo.getAssociationType()));
            HazardFactorsRequireVo.HazardFactorsRequireQueryVo vo = BeanUtil.toBean(hazardFactorsRequire, HazardFactorsRequireVo.HazardFactorsRequireQueryVo.class);
            hazardFactorsRequireVo.setVo(vo);
        }
        return hazardFactorsRequireVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(HazardFactorsRequireSaveBo bo) {
        HazardFactorsRequire hazardFactorsRequire = BeanUtil.toBean(bo, HazardFactorsRequire.class);
        if(ObjectUtil.notEqual(AssociationTypeEnum.EVALUATION_CRITERION.getCode(), bo.getAssociationType())
            && StrUtil.isBlank(bo.getDutyStatus())) {
            throw new PeisException(ErrorCodeConstants.PEIS_HAZARD_STATUS_NOT_EMPTY);
        }
        if(StrUtil.isBlank(bo.getId())) {
            String msg = StrUtil.EMPTY;
            LambdaQueryWrapper<HazardFactorsRequire> wrapper =  Wrappers.lambdaQuery(HazardFactorsRequire.class)
                .eq(HazardFactorsRequire::getHazardFactorsCode, bo.getHazardFactorsCode())
                .eq(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode()),
                    HazardFactorsRequire::getDutyStatus, bo.getDutyStatus())
                .eq(HazardFactorsRequire::getAssociationType, bo.getAssociationType());
            if(ObjectUtil.notEqual(bo.getAssociationType(), AssociationTypeEnum.EVALUATION_CRITERION.getCode())) {
                msg = "在岗状态";
            }
            if(Objects.equals("14", bo.getSortCode())) {
                wrapper.eq(HazardFactorsRequire::getShineSource, bo.getShineSource())
                    .eq(HazardFactorsRequire::getShineType, bo.getShineType());
                msg = msg.concat("、照射源、职业照射种类");
            }
            long count = baseMapper.selectCount(wrapper);
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_HAZARD_STATUS_ISEXIST, msg);
            }
            baseMapper.insert(hazardFactorsRequire);
            //必检项目、目标职业病、职业禁忌症
            if(AssociationTypeEnum.getCheckList().contains(bo.getAssociationType())) {
                bo.getItemList().forEach(k -> hazardFactorsItemMapper.insert(new HazardFactorsItem().setItemId(k)
                    .setFactorsId(hazardFactorsRequire.getId())));
            }
        }else{
            baseMapper.updateById(hazardFactorsRequire);
            if(AssociationTypeEnum.getCheckList().contains(bo.getAssociationType())) {
                List<HazardFactorsItem> list = hazardFactorsItemMapper.selectList(Wrappers.lambdaQuery(HazardFactorsItem.class)
                    .eq(HazardFactorsItem::getFactorsId, hazardFactorsRequire.getId()));
                List<Long> itemIdList = list.stream().map(HazardFactorsItem::getItemId).collect(Collectors.toList());
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
                insList.forEach(k -> hazardFactorsItemMapper.insert(new HazardFactorsItem().setFactorsId(hazardFactorsRequire.getId())
                    .setItemId(k)));
                //删除
                List<Long> delList = list.stream().filter(e -> !bo.getItemList().contains(e.getItemId()))
                    .map(HazardFactorsItem::getId).toList();
                delList.forEach(hazardFactorsItemMapper::deleteById);
                //更新
                List<HazardFactorsItem> upList = list.stream().filter(e -> bo.getItemList().contains(e.getItemId())).toList();
                upList.forEach(hazardFactorsItemMapper::updateById);
            }
        }
    }

    @Override
    public HazardFactorsRequireVo hazardFactorsDetail(String id) {
        HazardFactorsRequire hazardFactorsRequire = baseMapper.selectById(id);
        HazardFactorsRequireVo.HazardFactorsRequireQueryVo vo = BeanUtil.toBean(hazardFactorsRequire, HazardFactorsRequireVo.HazardFactorsRequireQueryVo.class);
        List<HazardFactorsItem> list = hazardFactorsItemMapper.selectList(Wrappers.lambdaQuery(HazardFactorsItem.class)
            .eq(HazardFactorsItem::getFactorsId, id));
        if(CollUtil.isNotEmpty(list)) {
            List<HazardFactorsRequireVo.ItemBody>  bodyList = new ArrayList<>();
            if(ObjectUtil.equal(hazardFactorsRequire.getAssociationType(), AssociationTypeEnum.REQUIRED_ITEM.getCode())) {
                List<TjBasicProject> itemList = tjBasicProjectMapper.selectBatchIds(list.stream()
                    .map(HazardFactorsItem::getItemId).collect(Collectors.toList()));
                itemList.forEach(k -> bodyList.add(new HazardFactorsRequireVo.ItemBody()
                    .setItemId(k.getId())
                    .setName(k.getZybCode().concat(k.getBasicProjectName()))));
            }else if(ObjectUtil.equal(hazardFactorsRequire.getAssociationType(),
                AssociationTypeEnum.TARGET_OCCUPATIONAL.getCode()) ||
                ObjectUtil.equal(hazardFactorsRequire.getAssociationType(),
                    AssociationTypeEnum.OCCUPATIONAL_CONTRAINDICATIONS.getCode())){
                List<TjOccupationalDict> dictList = tjOccupationalDictMapper.selectBatchIds(list.stream()
                    .map(HazardFactorsItem::getItemId).collect(Collectors.toList()));
                dictList.forEach(k -> bodyList.add(new HazardFactorsRequireVo.ItemBody()
                    .setItemId(k.getId())
                    .setName(k.getCode().concat(k.getValue()))));
            }
            vo.setItemList(bodyList);
        }
        return new HazardFactorsRequireVo().setVo(vo);
    }

    @Override
    public Boolean deleteById(Long id) {
        hazardFactorsItemMapper.delete(Wrappers.lambdaUpdate(new HazardFactorsItem().setFactorsId(id)));
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDeleteByIds(List<Long> list) {
        list.forEach(k -> hazardFactorsItemMapper.delete(Wrappers.lambdaUpdate(HazardFactorsItem.class)
                .eq(HazardFactorsItem::getFactorsId, k)));
        return baseMapper.deleteBatchIds(list) > 0;
    }
}
