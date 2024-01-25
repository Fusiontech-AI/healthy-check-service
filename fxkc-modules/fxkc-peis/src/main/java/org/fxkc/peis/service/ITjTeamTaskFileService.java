package org.fxkc.peis.service;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.TjTeamTaskFile;
import org.fxkc.peis.domain.vo.TjTeamTaskFileVo;
import org.fxkc.peis.domain.bo.TjTeamTaskFileBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 体检单位任务文件Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
public interface ITjTeamTaskFileService {

    /**
     * 查询体检单位任务文件
     */
    TjTeamTaskFileVo queryById(Long id);

    /**
     * 查询体检单位任务文件列表
     */
    TableDataInfo<TjTeamTaskFileVo> queryPageList(TjTeamTaskFileBo bo, PageQuery pageQuery);

    /**
     * 查询体检单位任务文件列表
     */
    List<TjTeamTaskFileVo> queryList(TjTeamTaskFileBo bo);

    /**
     * 新增体检单位任务文件
     */
    Boolean insertByBo(TjTeamTaskFileBo bo);

    /**
     * 修改体检单位任务文件
     */
    Boolean updateByBo(TjTeamTaskFileBo bo);

    /**
     * 校验并批量删除体检单位任务文件信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void downLoadTaskFile(Long id, HttpServletResponse response);
}
