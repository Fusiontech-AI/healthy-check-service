package org.fxkc.peis.service;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.common.excel.core.ExcelResult;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 团检任务管理Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
public interface ITjTeamTaskService {

    /**
     * 查询团检任务管理
     */
    TjTeamTaskDetailVo queryById(Long id);

    /**
     * 查询团检任务管理列表
     */
    TableDataInfo<TjTeamTaskVo> queryPageList(TjTeamTaskQueryBo bo, PageQuery pageQuery);

    /**
     * 查询团检任务管理列表
     */
    List<TjTeamTaskVo> queryList(TjTeamTaskQueryBo bo);

    /**
     * 新增团检任务管理
     */
    List<TjTeamGroupVo> insertByBo(TjTeamTaskBo bo);

    /**
     * 修改团检任务管理
     */
    List<TjTeamGroupVo> updateByBo(TjTeamTaskBo bo);

    /**
     * 校验并批量删除团检任务管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    TjVerifyMessageVo verifyGroupData(List<TjGroupVerifyBo> list);

    TjVerifyMessageVo verifyGroupPackageData(List<TjGroupVerifyPackageBo> list);

    void exportRegisterTemplate(String templateType, Long taskId, HttpServletResponse response);

    ExcelResult<TjTaskOccupationalExportVo> importRegisterData(MultipartFile file, TjTaskImportBo bo) throws IOException;

    void insertRegisterData(TjRegisterImportBo bo);

    TjTaskReviewDetailVo queryTaskReviewDetail(Long id);

    TableDataInfo<TjTaskReviewGroupVo> queryTaskReviewGroup(Long taskId, PageQuery pageQuery);

    TableDataInfo<TjTaskReviewRegisterVo> queryTaskReviewRegister(Long taskId, PageQuery pageQuery);

    void reviewTask(TjTaskReviewBo bo);


}
