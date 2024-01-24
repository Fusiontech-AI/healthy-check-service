package org.fxkc.system.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.system.api.RemoteDictService;
import org.fxkc.system.api.domain.vo.RemoteDictDataVo;
import org.fxkc.system.domain.vo.SysDictDataVo;
import org.fxkc.system.service.ISysDictTypeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 字典服务
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteDictServiceImpl implements RemoteDictService {

    private final ISysDictTypeService sysDictTypeService;


    @Override
    public List<RemoteDictDataVo> selectDictDataByType(String dictType) {
        List<SysDictDataVo> list = sysDictTypeService.selectDictDataByType(dictType);
        return MapstructUtils.convert(list, RemoteDictDataVo.class);
    }

    @Override
    public List<RemoteDictDataVo> selectDictDataByValueOrType(Collection<String> valueList, Collection<String> typeList) {
        List<SysDictDataVo> list = sysDictTypeService.selectDictDataByValueOrType(valueList, typeList);
        return MapstructUtils.convert(list, RemoteDictDataVo.class);
    }

}
