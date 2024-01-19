package org.fxkc.peis.service;

import org.fxkc.peis.domain.bo.TjOccupationalDictBo;
import org.fxkc.peis.domain.vo.TjOccupationalDictVo;

import java.util.List;

/**
 * 职业病字典Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
public interface ITjOccupationalDictCacheService {

    /**
     * 查询职业病字典列表
     */
    List<TjOccupationalDictVo> queryList(TjOccupationalDictBo bo);
}
