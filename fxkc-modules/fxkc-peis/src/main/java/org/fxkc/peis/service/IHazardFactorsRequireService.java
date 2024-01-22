package org.fxkc.peis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.domain.bo.HazardFactorsRequireSaveBo;
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 危害因素必检项目主Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface IHazardFactorsRequireService extends IService<HazardFactorsRequire> {

    HazardFactorsRequireVo hazardFactorsQuery(HazardFactorsRequireBo bo, PageQuery pageQuery);

    void saveOrUpdate(HazardFactorsRequireSaveBo bo);

    HazardFactorsRequireVo hazardFactorsDetail(String id);

    Boolean deleteById(Long id);

    Boolean batchDeleteByIds(List<Long> list);
}
