package org.fxkc.peis.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.SpringUtils;
import org.fxkc.common.core.utils.ValidatorUtils;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.excel.core.ExcelListener;
import org.fxkc.common.excel.core.ExcelResult;
import org.fxkc.peis.domain.vo.TjTaskOccupationalExportVo;
import org.fxkc.peis.service.ITjTeamGroupService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户自定义导入
 *
 * @author Lion Li
 */
@Slf4j
public class TjTaskImportListener extends AnalysisEventListener<TjTaskOccupationalExportVo> implements ExcelListener<TjTaskOccupationalExportVo> {

    private final Boolean isOccupational;

    private final ITjTeamGroupService iTjTeamGroupService;
    private final int successNum = 0;
    private final int failureNum = 0;
    private final StringBuffer successMsg = new StringBuffer();
    private final StringBuffer failureMsg = new StringBuffer();

    private final List<TjTaskOccupationalExportVo> successList = CollUtil.newArrayList();

    private final List<String> errorList = CollUtil.newArrayList();
    public TjTaskImportListener(Boolean isOccupational) {
        this.isOccupational = isOccupational;
        this.iTjTeamGroupService = SpringUtils.getBean(ITjTeamGroupService.class);
    }

    @Override
    public void invoke(TjTaskOccupationalExportVo tjTaskOccupationalExportVo, AnalysisContext context) {
        log.info("解析导入数据======={}", JSONUtil.toJsonStr(tjTaskOccupationalExportVo));
        ValidatorUtils.validate(tjTaskOccupationalExportVo, AddGroup.class);
        Field[] fields = tjTaskOccupationalExportVo.getClass().getDeclaredFields();
        List<Field> collect = Arrays.stream(fields).filter(f -> f.isAnnotationPresent(ExcelProperty.class)).toList();
        for (Field field : collect) {
            ExcelProperty declaredAnnotation = field.getDeclaredAnnotation(ExcelProperty.class);
            log.info("name======={}", field.getName());
            log.info("index======={}", declaredAnnotation.index());
        }
//        try {
//            // 验证是否存在这个用户
//            if (ObjectUtil.isNull(sysUser)) {
//
//                successNum++;
//                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
//            } else {
//                failureNum++;
//                failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(sysUser.getUserName()).append(" 已存在");
//            }
//        } catch (Exception e) {
//            failureNum++;
//            String msg = "<br/>" + failureNum + "、账号 " + userVo.getUserName() + " 导入失败：";
//            failureMsg.append(msg).append(e.getMessage());
//            log.error(msg, e);
//        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //todo 数据库操作
    }

    @Override
    public ExcelResult<TjTaskOccupationalExportVo> getExcelResult() {
        return new ExcelResult<TjTaskOccupationalExportVo>() {
            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<TjTaskOccupationalExportVo> getList() {
                return successList;
            }

            @Override
            public List<String> getErrorList() {
                return errorList;
            }
        };
    }
}
