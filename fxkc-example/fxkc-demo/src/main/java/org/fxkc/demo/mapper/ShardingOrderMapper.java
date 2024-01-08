package org.fxkc.demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.fxkc.demo.domain.ShardingOrder;


@Mapper
@DS("sharding")
public interface ShardingOrderMapper extends BaseMapper<ShardingOrder> {


}
