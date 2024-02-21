package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Select;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.peis.domain.TjArchives;
import org.fxkc.peis.domain.vo.TjArchivesVo;

/**
 * 体检档案Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
public interface TjArchivesMapper extends BaseMapperPlus<TjArchives, TjArchivesVo> {

    @Select("select tj_archives_seq.nextval from dual")
    String queryTjArchives();
}
