package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTemplateExtendBo;
import org.fxkc.peis.domain.TjTemplateExtend;
import org.fxkc.peis.mapper.TjTemplateExtendMapper;
import org.fxkc.peis.service.ITjTemplateExtendService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检报告扩展Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@RequiredArgsConstructor
@Service
public class TjTemplateExtendServiceImpl implements ITjTemplateExtendService {

    private final TjTemplateExtendMapper baseMapper;

    /**
     * 查询体检报告扩展
     */
    @Override
    public TjTemplateExtendVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检报告扩展列表
     */
    @Override
    public TableDataInfo<TjTemplateExtendVo> queryPageList(TjTemplateExtendBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTemplateExtend> lqw = buildQueryWrapper(bo);
        Page<TjTemplateExtendVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检报告扩展列表
     */
    @Override
    public List<TjTemplateExtendVo> queryList(TjTemplateExtendBo bo) {
        LambdaQueryWrapper<TjTemplateExtend> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTemplateExtend> buildQueryWrapper(TjTemplateExtendBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTemplateExtend> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExtendType()), TjTemplateExtend::getExtendType, bo.getExtendType());
        lqw.eq(StringUtils.isNotBlank(bo.getShowType()), TjTemplateExtend::getShowType, bo.getShowType());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), TjTemplateExtend::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getTemplateId()), TjTemplateExtend::getTemplateId, bo.getTemplateId());
        return lqw;
    }

    /**
     * 新增体检报告扩展
     */
    @Override
    public Boolean insertByBo(TjTemplateExtendBo bo) {
        TjTemplateExtend add = MapstructUtils.convert(bo, TjTemplateExtend.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检报告扩展
     */
    @Override
    public Boolean updateByBo(TjTemplateExtendBo bo) {
        TjTemplateExtend update = MapstructUtils.convert(bo, TjTemplateExtend.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 批量删除体检报告扩展
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<TjTemplateExtendVo> getListByInfoId(List<Long> templateIdList) {
        return this.baseMapper.getListByInfoId(templateIdList);
    }
}
