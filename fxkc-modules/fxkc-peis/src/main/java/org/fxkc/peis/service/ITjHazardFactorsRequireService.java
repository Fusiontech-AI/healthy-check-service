package org.fxkc.peis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fxkc.peis.domain.TjHazardFactorsRequire;
import org.fxkc.peis.domain.bo.TjHazardFactorsCodeBo;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireSaveBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireBo;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.List;

/**
 * 危害因素必检项目主Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
public interface ITjHazardFactorsRequireService extends IService<TjHazardFactorsRequire> {

    TjHazardFactorsRequireVo hazardFactorsQuery(TjHazardFactorsRequireBo bo, PageQuery pageQuery);

    void saveOrUpdate(TjHazardFactorsRequireSaveBo bo);

    TjHazardFactorsRequireVo hazardFactorsDetail(String id);

    Boolean deleteById(Long id);

    Boolean batchDeleteByIds(List<Long> list);

    List<TjHazardFactorsRequireVo.ItemBody> queryItemByFactorsCodeAndDutyStatus(TjHazardFactorsCodeBo bo);
}
