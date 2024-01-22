package org.fxkc.peis.service;

import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.bo.TjRegisterBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

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

    /**
     * 查询体检人员登记信息列表
     */
    TableDataInfo<TjRegisterVo> queryPageList(TjRegisterBo bo, PageQuery pageQuery);

    /**
     * 查询体检人员登记信息列表
     */
    List<TjRegisterVo> queryList(TjRegisterBo bo);

    /**
     * 新增体检人员登记信息
     */
    Boolean insertByBo(TjRegisterBo bo);

    /**
     * 修改体检人员登记信息
     */
    Boolean updateByBo(TjRegisterBo bo);

    /**
     * 校验并批量删除体检人员登记信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
