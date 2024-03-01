package org.fxkc.peis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjTjxmKeywords;
import org.fxkc.peis.domain.vo.TjTjxmKeywordsVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 体检项目关键字库Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface TjTjxmKeywordsMapper extends BaseMapperPlus<TjTjxmKeywords, TjTjxmKeywordsVo> {

    @Select("select t1.* from tj_tjxm_keywords t1  ${ew.customSqlSegment}")
    List<TjTjxmKeywords> selectNoBindKeywords(@Param(Constants.WRAPPER) QueryWrapper<TjTjxmKeywords> wrapper);
}
