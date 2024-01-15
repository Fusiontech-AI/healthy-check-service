package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.domain.TjPackageInfo;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.mapper.TjPackageInfoMapper;
import org.fxkc.peis.mapper.TjPackageMapper;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检套餐Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@RequiredArgsConstructor
@Service
public class TjPackageServiceImpl implements ITjPackageService {

    private final TjPackageMapper baseMapper;

    private final TjPackageInfoMapper tjPackageInfoMapper;


    /**
     * 查询体检套餐
     */
    @Override
    public TjPackageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检套餐列表
     */
    @Override
    public TableDataInfo<TjPackageVo> queryPageList(TjPackageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjPackage> lqw = buildQueryWrapper(bo);
        Page<TjPackageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检套餐列表
     */
    @Override
    public List<TjPackageVo> queryList(TjPackageBo bo) {
        LambdaQueryWrapper<TjPackage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjPackage> buildQueryWrapper(TjPackageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjPackage> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTjType()), TjPackage::getTjType, bo.getTjType());
        lqw.eq(StringUtils.isNotBlank(bo.getSuitSex()), TjPackage::getSuitSex, bo.getSuitSex());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), TjPackage::getPackageName, bo.getPackageName());
        lqw.like(StringUtils.isNotBlank(bo.getPackageSimpleName()), TjPackage::getPackageSimpleName, bo.getPackageSimpleName());
        lqw.eq(StringUtils.isNotBlank(bo.getPySimpleCode()), TjPackage::getPySimpleCode, bo.getPySimpleCode());
        lqw.eq(bo.getPackageSort() != null, TjPackage::getPackageSort, bo.getPackageSort());
        lqw.eq(bo.getStandardAmount() != null, TjPackage::getStandardAmount, bo.getStandardAmount());
        lqw.eq(bo.getDiscount() != null, TjPackage::getDiscount, bo.getDiscount());
        lqw.eq(bo.getReceivableAmount() != null, TjPackage::getReceivableAmount, bo.getReceivableAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjPackage::getStatus, bo.getStatus());
        lqw.orderByAsc(TjPackage::getPackageSort).orderByDesc(TjPackage::getCreateTime);
        return lqw;
    }

    /**
     * 新增体检套餐
     */
    @Override
    public Boolean insertByBo(TjPackageAddBo bo) {
        TjPackage add = MapstructUtils.convert(bo, TjPackage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        List<TjPackageInfoItemBo> infoItemBos = bo.getTjPackageInfoItemBos();
        if(CollUtil.isNotEmpty(infoItemBos)){
            insertPackageInfo(infoItemBos,bo);
        }
        return flag;
    }

    /**
     * 修改体检套餐
     */
    @Override
    public Boolean updateByBo(TjPackageAddBo bo) {
        TjPackage update = MapstructUtils.convert(bo, TjPackage.class);
        validEntityBeforeSave(update);
        List<TjPackageInfoItemBo> infoItemBos = bo.getTjPackageInfoItemBos();
        if(CollUtil.isNotEmpty(infoItemBos)){
            //先删除 再新增
            tjPackageInfoMapper.delete(new LambdaQueryWrapper<TjPackageInfo>()
                .eq(TjPackageInfo::getPackageId,bo.getId()));
            insertPackageInfo(infoItemBos,bo);
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjPackage entity){
        if(StringUtils.isNotEmpty(entity.getPackageName()) && !checkNameUnique(entity)){
            throw new ServiceException("套餐名称'" + entity.getPackageName() + "'已存在!");
        }
    }

    /**
     * 判断套餐名称是否唯一
     */
    private boolean checkNameUnique(TjPackage entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjPackage tjPackage = baseMapper.selectOne(new LambdaQueryWrapper<TjPackage>()
            .eq(TjPackage::getPackageName, entity.getPackageName())
        );
        if (ObjectUtil.isNotNull(tjPackage) && tjPackage.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 批量删除体检套餐
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 体检套餐动态算费(可复用)
     */
    @Override
    public TjPackageBillBo dynamicBilling(TjPackageBillBo bo) {
        List<TjPackageBillItemBo> tjPackageBillItemBos = bo.getTjPackageBillItemBos();
        //合计的标准价格 直接各个单项标准价格相加
        bo.setStandardAmount(tjPackageBillItemBos.stream().map(m->m.getStandardAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
        if(Objects.equals(bo.getChangeType(),"1")){
            //找出当前修改的单项记录信息
            tjPackageBillItemBos.stream().filter(m -> Objects.equals(m.getSort(), bo.getSort())).forEach(m->{
                //修改单项时  只计算当前修改的单项折扣或应收   然后根据应收相加计算合计应收和折扣
                if(Objects.equals("1",bo.getInputType())){
                    //根据标准金额和折扣 计算应收金额
                    BigDecimal receivableAmount = getReceivableAmountByDiscount(m.getStandardAmount(),m.getDiscount());
                    m.setReceivableAmount(receivableAmount);
                }else{
                    //根据标准金额和应收金额 计算折扣
                    BigDecimal realityDiscount = getDiscountByReceivableAmount(m.getStandardAmount(),m.getReceivableAmount());
                    m.setDiscount(realityDiscount);
                }

            });

            bo.setReceivableAmount(tjPackageBillItemBos.stream().map(m->m.getReceivableAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            bo.setDiscount(getDiscountByReceivableAmount(bo.getStandardAmount(),bo.getReceivableAmount()));
            bo.setTjPackageBillItemBos(tjPackageBillItemBos);
        }else{
            //单项需要计算的对应折扣
            BigDecimal discount = bo.getDiscount();
            if(Objects.equals("1",bo.getInputType())){
                //根据标准金额和折扣 计算应收金额
                BigDecimal receivableAmount = getReceivableAmountByDiscount(bo.getStandardAmount(),bo.getDiscount());
                bo.setReceivableAmount(receivableAmount);
            }else{
                //根据标准金额和应收金额 计算折扣
                BigDecimal realityDiscount = getDiscountByReceivableAmount(bo.getStandardAmount(),bo.getReceivableAmount());
                discount = realityDiscount;
                bo.setDiscount(realityDiscount);
            }

            //根据合计的折扣 去计算除了标准金额最高的单项信息
            List<TjPackageBillItemBo> collect = tjPackageBillItemBos.stream().sorted(Comparator.comparing(TjPackageBillItemBo::getStandardAmount)).collect(Collectors.toList());
            BigDecimal addAmount = new BigDecimal("0");
            for (int i = 0; i <collect.size() ; i++) {
                if(i==collect.size()-1){
                    //最后一项时 通过合计的应收金额 减去前面的应收金额和
                    collect.get(i).setReceivableAmount(bo.getReceivableAmount().subtract(addAmount));
                    collect.get(i).setDiscount(getDiscountByReceivableAmount(collect.get(i).getStandardAmount(),collect.get(i).getReceivableAmount()));
                }else{
                    //计算单项的折扣 和  应收金额
                    collect.get(i).setDiscount(discount);
                    collect.get(i).setReceivableAmount(getReceivableAmountByDiscount(collect.get(i).getStandardAmount(),discount));
                    addAmount = addAmount.add(collect.get(i).getReceivableAmount());
                }
            }
            bo.setTjPackageBillItemBos(collect.stream().sorted(Comparator.comparingInt(TjPackageBillItemBo::getSort)).collect(Collectors.toList()));
        }

        return bo;
    }


    /**
     * 根据标准金额和折扣 计算应收金额
     * @param standardAmount
     * @param discount
     * @return
     */
    private BigDecimal getReceivableAmountByDiscount(BigDecimal standardAmount,BigDecimal discount){
        return standardAmount.multiply(discount).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 根据标准金额和应收金额 计算折扣
     * @param standardAmount
     * @param receivableAmount
     * @return
     */
    private BigDecimal getDiscountByReceivableAmount(BigDecimal standardAmount,BigDecimal receivableAmount){
        if(standardAmount.compareTo(new BigDecimal("0"))==0){
            return new BigDecimal("0");
        }
        return receivableAmount.divide(standardAmount, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }


    public void insertPackageInfo(List<TjPackageInfoItemBo> infoItemBos, TjPackageAddBo bo){
        List<TjPackageInfo> convert = MapstructUtils.convert(infoItemBos, TjPackageInfo.class);
        convert.stream().forEach(m->{m.setPackageId(bo.getId());});
        tjPackageInfoMapper.insertBatch(convert);
    }
}
