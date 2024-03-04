package org.fxkc.peis.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.peis.domain.TjRegProjectSummary;
import org.fxkc.peis.domain.bo.TjRegProjectHistoryListBo;
import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.bo.TjRegProjectSummaryBo;
import org.fxkc.peis.domain.vo.TjRegProjectSummaryVo;
import org.fxkc.peis.mapper.TjRegProjectSummaryMapper;
import org.fxkc.peis.service.ITjRegProjectSummaryService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 体检登记基础项目小结Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@RequiredArgsConstructor
@Service
public class TjRegProjectSummaryServiceImpl implements ITjRegProjectSummaryService {

    private final TjRegProjectSummaryMapper baseMapper;


    @Override
    public List<TjRegProjectSummaryVo> summaryList(TjRegProjectListBo bo) {
        LambdaQueryWrapper<TjRegProjectSummary> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRegItemId() != null, TjRegProjectSummary::getRegItemId, bo.getRegItemId());
        lqw.eq(bo.getRegId() != null, TjRegProjectSummary::getRegId, bo.getRegId());
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 新增体检登记基础项目小结
     */
    @Override
    public Boolean insertByBo(TjRegProjectSummaryBo bo) {
        TjRegProjectSummary add = MapstructUtils.convert(bo, TjRegProjectSummary.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检登记基础项目小结
     */
    @Override
    public Boolean updateByBo(TjRegProjectSummaryBo bo) {
        TjRegProjectSummary update = MapstructUtils.convert(bo, TjRegProjectSummary.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjRegProjectSummary entity){
        if(!checkCodeUnique(entity)){
            throw new ServiceException("登记基础项目id'" + entity.getBasicItemId() + "'已存在相应小结内容!");
        }
    }

    /**
     * 批量删除体检登记基础项目小结
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<TjRegProjectSummaryVo> summaryHistoryList(TjRegProjectHistoryListBo bo) {
        List<TjRegProjectSummaryVo> tjRegProjectSummaryVos = baseMapper.summaryHistoryList(new QueryWrapper<TjRegProjectSummary>()
            .eq("t.del_flag", CommonConstants.NORMAL)
            .eq("t.combination_project_id", bo.getCombinationProjectId())
            .exists("select 1 from tj_register m where  m.del_flag = '0' and m.credential_type = " + bo.getCredentialType() +
                " and m.credential_number = " + bo.getCredentialNumber() + "  and m.id != " + bo.getRegId())
        );

        return tjRegProjectSummaryVos;
    }


    /**
     * 判断登记基础项目id是否唯一
     */
    private boolean checkCodeUnique(TjRegProjectSummary entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjRegProjectSummary tjRegProjectSummary = baseMapper.selectOne(new LambdaQueryWrapper<TjRegProjectSummary>()
            .eq(TjRegProjectSummary::getBasicItemId, entity.getBasicItemId())
        );
        if (ObjectUtil.isNotNull(tjRegProjectSummary) && tjRegProjectSummary.getId() != id) {
            return false;
        }
        return true;
    }

}
