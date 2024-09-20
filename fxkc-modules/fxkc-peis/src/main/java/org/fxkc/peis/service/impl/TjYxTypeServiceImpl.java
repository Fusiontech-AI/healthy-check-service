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
import org.fxkc.peis.domain.TjYxType;
import org.fxkc.peis.domain.bo.TjYxTypeBo;
import org.fxkc.peis.domain.bo.TjYxTypeListQueryBo;
import org.fxkc.peis.domain.vo.TjYxTypeVo;
import org.fxkc.peis.mapper.TjYxTypeMapper;
import org.fxkc.peis.service.ITjYxTypeService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检阳性分类Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
@RequiredArgsConstructor
@Service
public class TjYxTypeServiceImpl implements ITjYxTypeService {

    private final TjYxTypeMapper baseMapper;

    /**
     * 查询体检阳性分类
     */
    @Override
    public TjYxTypeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检阳性分类列表
     */
    @Override
    public TableDataInfo<TjYxTypeVo> queryPageList(TjYxTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjYxType> lqw = buildQueryWrapper(bo);
        List<TjYxTypeVo> oldTjYxTypeVos = baseMapper.selectVoList(lqw);
        //拿出一级分类
        List<TjYxTypeVo> oneLevelList = oldTjYxTypeVos.stream().filter(m -> Objects.equals(1L, m.getParentId())).collect(Collectors.toList());
        //拿出二级分类
        List<TjYxTypeVo> twoLevelList = oldTjYxTypeVos.stream().filter(m -> !Objects.equals(1L, m.getParentId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(twoLevelList)){
            List<Long> parentIds = twoLevelList.stream().map(m -> m.getParentId()).collect(Collectors.toList());
            List<TjYxTypeVo> oneLevelList2 = baseMapper.selectVoBatchIds(parentIds);
            oneLevelList.addAll(oneLevelList2.stream().filter(m -> Objects.equals(1L, m.getParentId())).collect(Collectors.toList()));
        }

        //开始渲染响应的树结构信息
        List<TjYxTypeVo> finalTwoLevelList = twoLevelList;
        List<TjYxTypeVo> collect = oneLevelList.stream().distinct().collect(Collectors.toList());
        List<TjYxTypeVo> respList = collect.stream().map(
            (m) -> {
                m.setChildren(getChildrenList(m, finalTwoLevelList));
                return m;
            }
        ).collect(Collectors.toList());
        //构造响应的分页信息
        Page<TjYxTypeVo> respPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize(), respList.size());
        List<TjYxTypeVo> lists = getPageInfo(pageQuery.getPageNum(), pageQuery.getPageSize(), respList);
        respPage.setRecords(lists);
        return TableDataInfo.build(respPage);
    }

    /**
     * 查询体检阳性分类列表
     */
    @Override
    public List<TjYxTypeVo> queryList(TjYxTypeBo bo) {
        LambdaQueryWrapper<TjYxType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjYxType> buildQueryWrapper(TjYxTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjYxType> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, TjYxType::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TjYxType::getName, bo.getName());
        lqw.eq(bo.getSort() != null, TjYxType::getSort, bo.getSort());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TjYxType::getStatus, bo.getStatus());
        lqw.orderByDesc(TjYxType::getSort);
        return lqw;
    }

    /**
     * 新增体检阳性分类
     */
    @Override
    public Boolean insertByBo(TjYxTypeBo bo) {
        TjYxType add = MapstructUtils.convert(bo, TjYxType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检阳性分类
     */
    @Override
    public Boolean updateByBo(TjYxTypeBo bo) {
        TjYxType update = MapstructUtils.convert(bo, TjYxType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjYxType entity){
        if(StringUtils.isNotEmpty(entity.getName()) && !checkNameUnique(entity)){
            throw new ServiceException("阳性分类名称'" + entity.getName() + "'已存在!");
        }
    }

    /**
     * 批量删除体检阳性分类
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        List<TjYxType> tjYxTypeList = baseMapper.selectBatchIds(ids);
        List<Long> deleteIds = new ArrayList<>();
        deleteIds.addAll(ids);
        tjYxTypeList.stream().forEach(oldTjYxTypeByName->{
            //这里需要判断当前修改的id是否为一级目录  如果是 且做删除动作时则对应下面的二级目录都需要删除
            if(Objects.equals(1L,oldTjYxTypeByName.getParentId())){
                List<TjYxType> tjYxTypes = baseMapper.selectAllType(oldTjYxTypeByName.getId());
                List<Long> typeIds = tjYxTypes.stream().map(m -> m.getId()).collect(Collectors.toList());
                deleteIds.addAll(typeIds);
            }
        });

        return baseMapper.deleteBatchIds(deleteIds) > 0;
    }

    @Override
    public List<TjYxType> getTjYxTypedList(TjYxTypeListQueryBo bo) {
        return baseMapper.selectList(new LambdaQueryWrapper<TjYxType>()
            .eq(bo.getParentId()!=null,TjYxType::getParentId,bo.getParentId())
            .eq(Objects.equals(bo.getLevel(),"1"),TjYxType::getParentId,1L)
            .ne(Objects.equals(bo.getLevel(),"2"),TjYxType::getParentId,1L)
        );
    }


    /**
     * 判断阳性分类名称是否唯一
     */
    private boolean checkNameUnique(TjYxType entity) {
        long id = ObjectUtil.isNull(entity.getId()) ? -1L : entity.getId();
        TjYxType tjYxType = baseMapper.selectOne(new LambdaQueryWrapper<TjYxType>()
            .eq(TjYxType::getName, entity.getName())
        );
        if (ObjectUtil.isNotNull(tjYxType) && tjYxType.getId() != id) {
            return false;
        }
        return true;
    }

    /**
     * 获取子节点列表
     * @param tree
     * @param list
     * @return
     */
    public static List<TjYxTypeVo> getChildrenList(TjYxTypeVo tree, List<TjYxTypeVo> list){
        List<TjYxTypeVo> children = list.stream().filter(item -> Objects.equals(item.getParentId(), tree.getId())).map(
            (item) -> {
                item.setChildren(getChildrenList(item, list));
                return item;
            }
        ).collect(Collectors.toList());
        return children;
    }



    /**
     * 处理List集合数据进行分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据个数
     * @param list        进行分页的数据
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        List<T> newList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
                newList.add(list.get(currIdx + i));
            }
        }
        return newList;
    }
}
