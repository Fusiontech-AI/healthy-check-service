package org.fxkc.peis.reportPrint.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.core.service.DictService;
import org.fxkc.common.core.utils.DateUtils;
import org.fxkc.peis.constant.DictTypeConstants;
import org.fxkc.peis.constant.ExtendTypeEnum;
import org.fxkc.peis.constant.ReportTypeEnum;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.vo.ftlModel.HealthReportModel;
import org.fxkc.peis.domain.vo.ftlModel.TxmModel;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.reportPrint.AbstractReportPrint;
import org.fxkc.peis.service.ITjRegCombinationProjectService;
import org.fxkc.peis.service.ITjRegisterService;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.fxkc.peis.utils.PdfMergeUtil;
import org.fxkc.peis.utils.WordToPdfUtils;
import org.fxkc.system.api.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class BarCodeServiceImpl  extends AbstractReportPrint {
    @Autowired
    private ITjRegisterService registerService;
    @Autowired
    private ITjRegCombinationProjectService regProjectService;
    @Autowired
    private ITjTeamInfoService teamInfoService;
    @Autowired
    private DictService dictService;
    @DubboReference
    private RemoteUserService remoteUserService;

    public BarCodeServiceImpl(){
        this.reportType = ReportTypeEnum.TXM.getCode();
    }

    @Override
    public void print(ReportPrintBO bo, HttpServletResponse response) throws Exception {
        //查询体检记录
        List<TjRegisterVo> registerList = registerService.getByIds(bo.getRegIdList());
        if(registerList.isEmpty()){
            throw new PeisException("数据查询失败，无法打印！");
        }
        Map<Long, TjTemplateVo> templateMap = this.getTemplateMap(registerList);
        //翻译字典值
        Map<String, String> sexMap = dictService.getAllDictByDictType(DictTypeConstants.SYS_USER_SEX);
        //设置请求头
        WordToPdfUtils.setResponse(response,System.currentTimeMillis()+".pdf");
        //只有一条数据时，直接返回
        if(registerList.size()==1){
            TjRegisterVo s = registerList.get(0);
            this.printReport(s,templateMap, sexMap,   response.getOutputStream());
            this.afterExecute(bo);
            return;
        }
        //多条数据时，需要下载导本地并合并
        List<File> fileList =new ArrayList<>();
        String templatePath = WordToPdfUtils.getTemplatePath();
        try {
            for(TjRegisterVo s:registerList){
                File file = new File(templatePath, s.getId() + ".pdf");
                this.printReport(s,templateMap, sexMap,  new FileOutputStream(file));
                fileList.add(file);
            }
            //合并pdf
            PdfMergeUtil.mergePdfFiles(fileList,response.getOutputStream());
        }finally {
            //删除临时文件
            WordToPdfUtils.deleteFolder(new File(templatePath));
        }
        this.afterExecute(bo);
    }

    /**
     * 打印指引单
     */
    private void printReport(TjRegisterVo s, Map<Long, TjTemplateVo> templateMap,  Map<String, String> sexMap,  OutputStream outputStream){
        TjTemplateVo templateVo = templateMap.get(s.getId());
        if (templateVo.getTemplateCode().equals("TXM001")) {
            this.printTxm001(s, templateVo,  sexMap,  outputStream);
        } else {
            throw new PeisException("模板功能未实现，无法打印！");
        }
    }

    /**
     * 打印具体报告[GR001]数据模型
     * @param register 体检登记信息
     * @param sexMap 性别
     * @param outputStream 输出流，文件渲染
     */
    private void printTxm001(TjRegisterVo register, TjTemplateVo templateVo, Map<String, String> sexMap, OutputStream outputStream ){
        //获取需要显示的项目
        List<TxmModel> modelList = this.regProjectService.queryByTxmModel(register.getId());
        modelList.forEach(model->{
            model.setAge(register.getAge()+"岁");
            model.setGender(sexMap.get(register.getGender()));
            model.setName(register.getName());
            //渲染条形码
            model.setBarCode(this.getBarCodeBase64(register.getHealthyCheckCode()));
        });
        Map<String,Object> map = new HashMap<>();
        map.put("dataList",modelList);
        WordToPdfUtils.createPdf(map,templateVo.getTemplatePath(),templateVo.getTemplateName(),outputStream);
    }

    /**
     * 数据的后置处理
     * @param bo 查询的数据
     */
    private void afterExecute(ReportPrintBO bo) {
        //修改导检单打印数据
        if(bo.getIsPrint()){
            this.registerService.updatePersonalReportPrint(bo);
        }

    }
}
