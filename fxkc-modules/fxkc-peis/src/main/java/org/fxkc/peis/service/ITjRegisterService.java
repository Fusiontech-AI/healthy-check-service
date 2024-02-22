package org.fxkc.peis.service;

import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjRegisterPageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;

import java.util.Collection;
import java.util.List;

/**
 * 体检人员登记信息Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
public interface ITjRegisterService {

    /**
     * 查询体检人员登记信息
     */
    TjRegisterVo queryById(Long id);

    TjRegisterVo getSingleInfo(TjRegisterSingleBo bo);

    /**
     * 查询体检人员登记信息列表
     */
    TableDataInfo<TjRegisterPageVo> queryPageList(TjRegisterPageBo bo, PageQuery pageQuery);

    /**
     * 查询体检人员登记信息列表
     */
    List<TjRegisterVo> queryList(TjRegisterBo bo);

    /**
     * 新增体检人员登记信息
     */
    Long insertByBo(TjRegisterAddBo bo);

    /**
     * 修改体检人员登记信息
     */
    Boolean updateByBo(TjRegisterBo bo);

    /**
     * 校验并批量删除体检人员登记信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean cancelRegistration(Collection<Long> ids);

    Boolean reRegistration(Collection<Long> ids);

    /**
     * 体检人员冻结
     *
     * @param ids 主键串
     */
    Boolean freeze(Collection<Long> ids);

    /**
     * 体检人员解冻
     *
     * @param ids 主键串
     */
    Boolean unfreeze(Collection<Long> ids);

    Boolean changeRegCombin(TjRegCombinAddBo bo);

    List<TjRegisterVo> getByIds(List<Long> regIdList);

    Boolean changeRegReplaceInfo(TjRegReplaceInfoBo bo);

    Boolean changeReportReceiveWay(TjRegReceiveWayBo bo);

    void updateGuideSheetPrint(ReportPrintBO bo);

    Boolean saveDiagnosis(TjRegSaveDiagnosisBo bo);

    Boolean deleteTaskRegister(Long id);

    void mergeArchives(TjArchivesBo bo);
}
