package org.fxkc.peis.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
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
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.exception.PeisException;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamTaskFileBo;
import org.fxkc.peis.domain.vo.TjTeamTaskFileVo;
import org.fxkc.peis.domain.TjTeamTaskFile;
import org.fxkc.peis.mapper.TjTeamTaskFileMapper;
import org.fxkc.peis.service.ITjTeamTaskFileService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 体检单位任务文件Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@RequiredArgsConstructor
@Service
public class TjTeamTaskFileServiceImpl extends ServiceImpl<TjTeamTaskFileMapper, TjTeamTaskFile> implements ITjTeamTaskFileService {

    private final static String[] fileSuffix = new String[]{"doc", "docx", "pdf","rar","zip"};

    /**
     * 查询体检单位任务文件
     */
    @Override
    public TjTeamTaskFileVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检单位任务文件列表
     */
    @Override
    public TableDataInfo<TjTeamTaskFileVo> queryPageList(TjTeamTaskFileBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamTaskFile> lqw = buildQueryWrapper(bo);
        Page<TjTeamTaskFileVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位任务文件列表
     */
    @Override
    public List<TjTeamTaskFileVo> queryList(TjTeamTaskFileBo bo) {
        LambdaQueryWrapper<TjTeamTaskFile> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamTaskFile> buildQueryWrapper(TjTeamTaskFileBo bo) {
        LambdaQueryWrapper<TjTeamTaskFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTaskId() != null, TjTeamTaskFile::getTaskId, bo.getTaskId());
        return lqw;
    }

    /**
     * 新增体检单位任务文件
     */
    @Override
    public Boolean insertByBo(TjTeamTaskFileBo bo) {
        TjTeamTaskFile add = commonBuild(bo);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改体检单位任务文件
     */
    @Override
    public Boolean updateByBo(TjTeamTaskFileBo bo) {
        TjTeamTaskFile update = commonBuild(bo);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamTaskFile entity){
        long fileSizeLimit = 500*1024;
        if(entity.getFileSize() > fileSizeLimit){
            throw new PeisException(ErrorCodeConstants.PEIS_TASK_FILE_SIZE);
        }
        if(Arrays.binarySearch(fileSuffix, entity.getSuffixType()) < 0) {
            throw new PeisException(ErrorCodeConstants.PEIS_TASK_FILE_FORMAT, entity.getSuffixType());
        }
    }

    private TjTeamTaskFile commonBuild(TjTeamTaskFileBo bo) {
        TjTeamTaskFile tjTeamTaskFile = MapstructUtils.convert(bo, TjTeamTaskFile.class);
        String fileName = IdUtil.simpleUUID() + StrUtil.DOT +
            FileUtil.extName(bo.getFile().getOriginalFilename());
        OssClient ossClient = OssFactory.instance();
        try {
            ossClient.upload(bo.getFile().getBytes(), fileName, bo.getFile().getContentType());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PeisException(ErrorCodeConstants.PEIS_TASK_FILE_UPLOAD);
        }
        tjTeamTaskFile.setFileName(fileName);
        tjTeamTaskFile.setOriginalName(FileNameUtil.cleanInvalid(bo.getFile().getOriginalFilename()));
        tjTeamTaskFile.setSuffixType(FileUtil.extName(tjTeamTaskFile.getOriginalName()));
        tjTeamTaskFile.setFileSize(bo.getFile().getSize());
        return tjTeamTaskFile;
    }

    /**
     * 批量删除体检单位任务文件
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void downLoadTaskFile(Long id, HttpServletResponse response) {
        TjTeamTaskFile tjTeamTaskFile = baseMapper.selectById(id);
        OssClient ossClient = OssFactory.instance();
        try(InputStream inputStream = ossClient.getObjectContent(tjTeamTaskFile.getFileName())) {
            int available = inputStream.available();
            IoUtil.copy(inputStream, response.getOutputStream(), available);
            response.setContentLength(available);
        } catch (Exception e) {
            throw new PeisException(e.getMessage());
        }
    }
}
