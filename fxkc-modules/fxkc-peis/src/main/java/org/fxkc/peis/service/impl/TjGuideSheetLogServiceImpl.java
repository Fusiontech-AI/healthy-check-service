package org.fxkc.peis.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.oss.constant.OssConstant;
import org.fxkc.common.oss.core.OssClient;
import org.fxkc.common.oss.entity.UploadResult;
import org.fxkc.common.oss.factory.OssFactory;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjGuideSheetLogBo;
import org.fxkc.peis.domain.vo.TjGuideSheetLogVo;
import org.fxkc.peis.domain.TjGuideSheetLog;
import org.fxkc.peis.mapper.TjGuideSheetLogMapper;
import org.fxkc.peis.service.ITjGuideSheetLogService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 导检单回收记录Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TjGuideSheetLogServiceImpl implements ITjGuideSheetLogService {

    private final TjGuideSheetLogMapper baseMapper;

    /**
     * 查询导检单回收记录
     */
    @Override
    public TjGuideSheetLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询导检单回收记录列表
     */
    @Override
    public TableDataInfo<TjGuideSheetLogVo> queryPageList(TjGuideSheetLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjGuideSheetLog> lqw = buildQueryWrapper(bo);
        Page<TjGuideSheetLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询导检单回收记录列表
     */
    @Override
    public List<TjGuideSheetLogVo> queryList(TjGuideSheetLogBo bo) {
        LambdaQueryWrapper<TjGuideSheetLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjGuideSheetLog> buildQueryWrapper(TjGuideSheetLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjGuideSheetLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRegisterId() != null, TjGuideSheetLog::getRegisterId, bo.getRegisterId());
        lqw.eq(StringUtils.isNotBlank(bo.getImagePath()), TjGuideSheetLog::getImagePath, bo.getImagePath());
        lqw.eq(bo.getUploadTime() != null, TjGuideSheetLog::getUploadTime, bo.getUploadTime());
        return lqw;
    }

    /**
     * 新增导检单回收记录
     */
    @Override
    public Boolean insertByBo(TjGuideSheetLogBo bo) {
        TjGuideSheetLog add = MapstructUtils.convert(bo, TjGuideSheetLog.class);
        validEntityBeforeSave(add);

        OssClient ossClient = OssFactory.instance();
        String originalFileName = bo.getFile().getOriginalFilename();
        String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
        String uuid = OssConstant.GUIDE_SHEET_BUKET+ StrUtil.SLASH +IdUtil.fastSimpleUUID()+suffix;
        UploadResult uploadResult;
        try {
            uploadResult = ossClient.upload(bo.getFile().getBytes(), uuid,bo.getFile().getContentType());
            add.setImagePath(URLUtil.getPath(uploadResult.getUrl()));
        } catch (IOException e) {
            log.error("上传导检单失败：{}",e);
            throw new ServiceException("上传导检单失败!");
        }

        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改导检单回收记录
     */
    @Override
    public Boolean updateByBo(TjGuideSheetLogBo bo) {
        TjGuideSheetLog update = MapstructUtils.convert(bo, TjGuideSheetLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjGuideSheetLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除导检单回收记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
