package org.fxkc.demo.mapper;

import org.fxkc.common.mybatis.annotation.DataColumn;
import org.fxkc.common.mybatis.annotation.DataPermission;
import org.fxkc.common.mybatis.core.mapper.BaseMapperPlus;
import org.fxkc.demo.domain.TestTree;
import org.fxkc.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTree, TestTreeVo> {

}
