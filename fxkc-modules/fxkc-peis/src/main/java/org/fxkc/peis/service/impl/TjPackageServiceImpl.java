package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.domain.TjPackageHazards;
import org.fxkc.peis.domain.TjPackageInfo;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.AmountCalculationVo;
import org.fxkc.peis.domain.vo.PackageAndProjectVo;
import org.fxkc.peis.domain.vo.TjPackageHazardsVo;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.enums.PhysicalTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.mapper.TjPackageHazardsMapper;
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
@Slf4j
public class TjPackageServiceImpl implements ITjPackageService {

    private final TjPackageMapper baseMapper;

    private final TjPackageInfoMapper tjPackageInfoMapper;

    private final FlowExecutor flowExecutor;

    private final TjPackageHazardsMapper tjPackageHazardsMapper;

    /**
     * 查询体检套餐
     */
    @Override
    public TjPackageVo queryById(Long id){
        TjPackageVo vo = baseMapper.selectVoById(id);
        List<TjPackageHazardsVo> hazardsVoList = tjPackageHazardsMapper.selectVoList(Wrappers.lambdaQuery(TjPackageHazards.class)
            .eq(TjPackageHazards::getPackageId, id));
        if(CollUtil.isNotEmpty(hazardsVoList)) {
            vo.setTjPackageHazardsVoList(hazardsVoList);
        }
        return vo;
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
        List<TjPackageHazardsBo> tjPackageHazardsBoList = bo.getTjPackageHazardsBoList();
        validEntityBeforeSave(add, tjPackageHazardsBoList);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        List<TjPackageInfoItemBo> infoItemBos = bo.getTjPackageInfoItemBos();
        if(CollUtil.isNotEmpty(infoItemBos)){
            insertPackageInfo(infoItemBos,bo);
        }
        if(CollUtil.isNotEmpty(tjPackageHazardsBoList)) {
            insertPackageHazards(tjPackageHazardsBoList, bo.getId());
        }
        return flag;
    }

    private void insertPackageHazards(List<TjPackageHazardsBo> tjPackageHazardsBoList, Long packageId) {
        List<TjPackageHazards> hazardsList = MapstructUtils.convert(tjPackageHazardsBoList, TjPackageHazards.class);
        hazardsList.forEach(k -> k.setPackageId(packageId));
        tjPackageHazardsMapper.insertBatch(hazardsList);
    }

    /**
     * 修改体检套餐
     */
    @Override
    public Boolean updateByBo(TjPackageAddBo bo) {
        TjPackage update = MapstructUtils.convert(bo, TjPackage.class);
        List<TjPackageHazardsBo> tjPackageHazardsBoList = bo.getTjPackageHazardsBoList();
        validEntityBeforeSave(update, tjPackageHazardsBoList);
        List<TjPackageInfoItemBo> infoItemBos = bo.getTjPackageInfoItemBos();
        //先删除 再新增
        tjPackageInfoMapper.delete(new LambdaQueryWrapper<TjPackageInfo>()
            .eq(TjPackageInfo::getPackageId,bo.getId()));
        if(CollUtil.isNotEmpty(infoItemBos)){
            insertPackageInfo(infoItemBos,bo);
        }
        tjPackageHazardsMapper.delete(Wrappers.lambdaQuery(TjPackageHazards.class)
            .eq(TjPackageHazards::getPackageId, bo.getId()));
        if(CollUtil.isNotEmpty(tjPackageHazardsBoList)) {
            insertPackageHazards(tjPackageHazardsBoList, bo.getId());
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjPackage entity, List<TjPackageHazardsBo> tjPackageHazardsBoList){
        if(StringUtils.isNotEmpty(entity.getPackageName()) && !checkNameUnique(entity)){
            throw new ServiceException("套餐名称'" + entity.getPackageName() + "'已存在!");
        }
        if(PhysicalTypeEnum.isOccupational(entity.getTjType())) {
            Map<String, Object> enumMap = EnumUtil.getNameFieldMap(PhysicalTypeEnum.class, "desc");
            if(StrUtil.isBlank(entity.getDutyStatus())) {
                throw new PeisException(ErrorCodeConstants.PEIS_DUTY_STATUS_NOT_EMPTY, enumMap.get(entity.getTjType()));
            }
            if(CollUtil.isEmpty(tjPackageHazardsBoList)) {
                throw new PeisException(ErrorCodeConstants.PEIS_PACKAGE_HAZARDS_NOT_EMPTY, enumMap.get(entity.getTjType()));
            }
        }
        if(Objects.equals(entity.getTjType(), PhysicalTypeEnum.FSTJ.name())) {
            if(StrUtil.isBlank(entity.getShineSource()) || StrUtil.isBlank(entity.getShineType())) {
                throw new PeisException(ErrorCodeConstants.PEIS_SHINE_NOT_EMPTY);
            }
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

    @Override
    public Boolean batchDisable(List<Long> ids, boolean b) {
        List<TjPackage> packageList = ids.stream().map(m -> {
            TjPackage tjPackage = new TjPackage();
            tjPackage.setId(m);
            tjPackage.setStatus(CommonConstants.DISABLE);
            return tjPackage;
        }).collect(Collectors.toList());
        return baseMapper.updateBatchById(packageList);
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

    @Override
    public AmountCalculationVo commonDynamicBilling(AmountCalculationBo bo) {
        LiteflowResponse response = flowExecutor.execute2Resp("charging", bo, AmountCalculationContext.class);
        Exception cause = response.getCause();
        if(cause!=null){
            log.error("动态算费时出现异常:",cause);
            throw new RuntimeException(cause.getMessage());
        }
        return response.getContextBean(AmountCalculationContext.class).getAmountCalculationVo();
    }


    /**
     * 根据标准金额和折扣 计算应收金额
     * @param standardAmount
     * @param discount
     * @return
     */
    public BigDecimal getReceivableAmountByDiscount(BigDecimal standardAmount,BigDecimal discount){
        return standardAmount.multiply(discount).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 根据标准金额和应收金额 计算折扣
     * @param standardAmount
     * @param receivableAmount
     * @return
     */
    public BigDecimal getDiscountByReceivableAmount(BigDecimal standardAmount,BigDecimal receivableAmount){
        if(standardAmount.compareTo(new BigDecimal("0"))==0){
            return new BigDecimal("0");
        }
        return receivableAmount.divide(standardAmount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 根据加项 已有项目 分组信息 计算加项的支付方式和折扣
     * @param addItems
     * @param haveItems
     * @param amountCalGroupBo
     */
    @Override
    public void calCulPayType(List<AmountCalculationItemBo> addItems, List<AmountCalculationItemBo> haveItems, AmountCalGroupBo amountCalGroupBo) {
        //首先比较已有记录的应收金额 和 分组金额的大小关系
        //获取默认分组支付方式应收金额累加的总和
        BigDecimal reduce = new BigDecimal("0");
        BigDecimal leftAmount = new BigDecimal("0");
        if(CollUtil.isNotEmpty(haveItems)){
           reduce = haveItems.stream().filter(m -> Objects.equals(amountCalGroupBo.getGroupPayType(), m.getPayType())).map(m -> m.getReceivableAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        for (int i = 0; i < addItems.size() ; i++) {
            AmountCalculationItemBo bo = addItems.get(i);
            if(reduce.compareTo(amountCalGroupBo.getPrice())>=0){
                //初始化计算前就超过了分组金额  当前和之后的全部走加项
                addItems.get(i).setPayType(amountCalGroupBo.getAddPayType());
                addItems.get(i).setDiscount(amountCalGroupBo.getAddDiscount());
            }else{
                reduce = reduce.add(bo.getReceivableAmount());
                //存量的金额小于分组内金额 从当前和后续的支付方式 全部为分组内支付方式,分组内折扣
                if(reduce.compareTo(amountCalGroupBo.getPrice())<=0){
                    addItems.get(i).setPayType(amountCalGroupBo.getGroupPayType());
                    addItems.get(i).setDiscount(amountCalGroupBo.getItemDiscount());
                }else{
                    //组内 和 组外支付方式不一样 才会有混合支付赋值
                    if(i==0 && !Objects.equals(amountCalGroupBo.getAddPayType(),amountCalGroupBo.getGroupPayType())){
                        addItems.get(i).setPayType("2");
                        //混合支付时,需要把当前多余的金额计算出来当做加项支付。
                        leftAmount = reduce.subtract(amountCalGroupBo.getPrice());
                    }else{
                        addItems.get(i).setPayType(amountCalGroupBo.getAddPayType());
                    }
                    addItems.get(i).setDiscount(amountCalGroupBo.getAddDiscount());
                }
            }
            fillSingleAmount(addItems.get(i),leftAmount,amountCalGroupBo);

        }

    }


    @Override
    public TableDataInfo<PackageAndProjectVo> queryPackageAndProjectPages(String name, PageQuery pageQuery) {
        Page<PackageAndProjectVo> packageAndProjectVoPage = baseMapper.queryPackageAndProjectPages(pageQuery.build(), name);
        return TableDataInfo.build(packageAndProjectVoPage);
    }

    @Override
    public List<PackageAndProjectVo> queryProjectByPackageId(Long packageId) {
        List<PackageAndProjectVo> projectVos = baseMapper.queryProjectByPackageId(packageId);
        return projectVos;
    }

    @Override
    public List<TjPackageVo> queryListByIds(List<Long> packageList) {
        if(packageList.isEmpty()){
            return new ArrayList<>();
        }
        return this.baseMapper.queryListByIds(packageList);
    }


    /**
     * 处理个费 和 团费的金额
     * @param bo
     */
    public void fillSingle(AmountCalculationItemBo bo){
        if(Objects.equals("0",bo.getPayType())){
            bo.setPersonAmount(bo.getReceivableAmount());
            bo.setTeamAmount(new BigDecimal("0"));
        }else{
            bo.setTeamAmount(bo.getReceivableAmount());
            bo.setPersonAmount(new BigDecimal("0"));
        }
    }


    /**
     * 处理个费 和 团费的金额 根据支付类型来处理
     * @param bo
     */
    public void fillSingleAmount(AmountCalculationItemBo bo,BigDecimal leftAmount, AmountCalGroupBo amountCalGroupBo){
        if(Objects.equals("0",bo.getPayType())){
            bo.setPersonAmount(bo.getReceivableAmount());
            bo.setTeamAmount(new BigDecimal("0"));
        }else if(Objects.equals("1",bo.getPayType())){
            bo.setTeamAmount(bo.getReceivableAmount());
            bo.setPersonAmount(new BigDecimal("0"));
        }else{
            //混合支付 加项为个人时
            if(Objects.equals(amountCalGroupBo.getAddPayType(),"0")){
                bo.setPersonAmount(leftAmount);
                bo.setTeamAmount(bo.getReceivableAmount().subtract(leftAmount));
            }else if(Objects.equals(amountCalGroupBo.getAddPayType(),"1")){
                bo.setPersonAmount(bo.getReceivableAmount().subtract(leftAmount));
                bo.setTeamAmount(leftAmount);
            }

        }
    }

    public void insertPackageInfo(List<TjPackageInfoItemBo> infoItemBos, TjPackageAddBo bo){
        List<TjPackageInfo> convert = MapstructUtils.convert(infoItemBos, TjPackageInfo.class);
        convert.stream().forEach(m->{m.setPackageId(bo.getId());});
        tjPackageInfoMapper.insertBatch(convert);
    }
}
