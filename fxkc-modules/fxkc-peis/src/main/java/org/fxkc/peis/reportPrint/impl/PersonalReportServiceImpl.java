package org.fxkc.peis.reportPrint.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.core.service.DictService;
import org.fxkc.common.core.utils.DateUtils;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.DictTypeConstants;
import org.fxkc.peis.constant.ExtendTypeEnum;
import org.fxkc.peis.constant.ReportTypeEnum;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;
import org.fxkc.peis.domain.vo.ftlModel.HealthReportModel;
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
import org.fxkc.system.api.domain.vo.RemoteUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人报告打印
 */
@Slf4j
@Service
public class PersonalReportServiceImpl  extends AbstractReportPrint {


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

    public PersonalReportServiceImpl(){
        this.reportType = ReportTypeEnum.PERSONAL.getCode();
    }

    @Override
    public void print(ReportPrintBO bo, HttpServletResponse response) throws Exception {
        String nickname = LoginHelper.getLoginUser().getNickname();
        //查询体检记录
        List<TjRegisterVo> registerList = registerService.getByIds(bo.getRegIdList());
        if(registerList.isEmpty()){
            throw new PeisException("数据查询失败，无法打印！");
        }
        Set<Long> teamIdSet = new HashSet<>();
        Set<Long> userIdSet = new HashSet<>();
        Map<Long, TjTemplateVo> templateMap = this.getTemplateMap(registerList);
        registerList.forEach(s->{
            if(null!=s.getTeamId()){teamIdSet.add(s.getTeamId());}
            if(null!=s.getGeneralReviewDoctor()){userIdSet.add(s.getGeneralReviewDoctor());}
        });
        //查询其它需要翻译的数据
        List<TjTeamInfoVo> teamInfoList = this.teamInfoService.queryListByIds(new ArrayList<>(teamIdSet));
        Map<Long, String> teamInfoMap = teamInfoList.stream().collect(Collectors.toMap(TjTeamInfoVo::getId, TjTeamInfoVo::getTeamName));
        //翻译用户昵称
        List<RemoteUserVo> userList = new ArrayList<>();
        if(!userIdSet.isEmpty()){
            userList = remoteUserService.selectByIdList(new ArrayList<>(userIdSet));
        }
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(RemoteUserVo::getUserId, RemoteUserVo::getNickName));
        //翻译字典值
        Map<String, String> sexMap = dictService.getAllDictByDictType(DictTypeConstants.SYS_USER_SEX);
        //设置请求头
        WordToPdfUtils.setResponse(response,System.currentTimeMillis()+".pdf");
        //只有一条数据时，直接返回
        if(registerList.size()==1){
            TjRegisterVo s = registerList.get(0);
            this.printReport(s,templateMap, teamInfoMap, sexMap,  userMap, nickname, response.getOutputStream());
            this.afterExecute(bo);
            return;
        }
        //多条数据时，需要下载导本地并合并
        List<File> fileList =new ArrayList<>();
        String templatePath = WordToPdfUtils.getTemplatePath();
        try {
            for(TjRegisterVo s:registerList){
                File file = new File(templatePath, s.getId() + ".pdf");
                this.printReport(s,templateMap, teamInfoMap, sexMap,  userMap, nickname, new FileOutputStream(file));
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
    private void printReport(TjRegisterVo s, Map<Long, TjTemplateVo> templateMap,  Map<Long, String> teamInfoMap, Map<String, String> sexMap, Map<Long, String> userMap, String userName, OutputStream outputStream){
        TjTemplateVo templateVo = templateMap.get(s.getId());
        if (templateVo.getTemplateCode().equals("GR001")) {
            this.printReport001(s, templateVo, teamInfoMap, sexMap, userMap, userName, outputStream);
        } else {
            throw new PeisException("模板功能未实现，无法打印！");
        }
    }

    /**
     * 打印具体报告[GR001]数据模型
     * @param s 体检登记信息
     * @param teamInfoMap 科室
     * @param sexMap 性别
     * @param userMap 用户
     * @param userName 当前登录人姓名
     * @param outputStream 输出流，文件渲染
     */
    private void printReport001(TjRegisterVo s, TjTemplateVo templateVo, Map<Long, String> teamInfoMap, Map<String, String> sexMap, Map<Long, String> userMap, String userName, OutputStream outputStream ){
        List<TjTemplateExtendVo> extendList = templateVo.getExtendList();
        Map<String, String> extendMap = extendList.stream().collect(Collectors.toMap(TjTemplateExtendVo::getExtendType, TjTemplateExtendVo::getContent));
        HealthReportModel model = new HealthReportModel();
        model.setAge(s.getAge()+"岁");
        model.setGender(sexMap.get(s.getGender()));
        model.setHealthyCheckTime(DateUtils.parseDateToStr("yyyy年MM月dd日",s.getHealthyCheckTime()));
        model.setHealthyCheckCode(s.getHealthyCheckCode());
        model.setName(s.getName());
        model.setTeamName(teamInfoMap.getOrDefault(s.getTeamId(),"个人体检"));
        model.setHospitalName(extendMap.get(ExtendTypeEnum.HOSPITAL_NAME.getCode()));
        model.setTjPhone(extendMap.get(ExtendTypeEnum.TJ_PHONE.getCode()));
        model.setHospitalIcon(WordToPdfUtils.getImageFromNetByUrl(extendMap.get(ExtendTypeEnum.HOSPITAL_LOGO.getCode())));
        model.setHospitalHomeIcon(WordToPdfUtils.getImageFromNetByUrl(extendMap.get(ExtendTypeEnum.PAGE_HEADER_ICON.getCode())));
        model.setGeneralReviewDoctor(userMap.get(s.getGeneralReviewDoctor()));
        model.setPeConclusion(s.getPeConclusion());
        model.setPeAdvice(s.getPeAdvice());
        //渲染条形码
        model.setHealthyBarCode(this.getBarCodeBase64(s.getHealthyCheckCode()));
        //获取科普说明--暂时没有
        //获取检查项目结果
        WordToPdfUtils.createPdf(model,templateVo.getTemplatePath(),templateVo.getTemplateName(),outputStream);
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
