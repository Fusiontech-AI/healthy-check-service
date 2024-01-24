package org.fxkc.system.api;

import org.fxkc.system.api.domain.vo.RemoteDictDataVo;

import java.util.Collection;
import java.util.List;

/**
 * 字典服务
 *
 * @author Lion Li
 */
public interface RemoteDictService {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<RemoteDictDataVo> selectDictDataByType(String dictType);

    List<RemoteDictDataVo> selectDictDataByValueOrType(Collection<String> valueList, Collection<String> typeList);
}
