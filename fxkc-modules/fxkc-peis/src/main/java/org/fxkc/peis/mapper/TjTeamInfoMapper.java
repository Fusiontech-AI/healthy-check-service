package org.fxkc.peis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 体检单位信息Mapper接口
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
public interface TjTeamInfoMapper extends BaseMapperPlus<TjTeamInfo, TjTeamInfoVo> {

    List<TjTeamInfoVo> queryListByIds(@Param("teamIdList") List<Long> teamIdList);
}
