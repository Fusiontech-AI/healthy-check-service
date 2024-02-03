package org.fxkc.peis.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.core.service.DictService;
import org.fxkc.common.core.utils.DateUtils;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.DictTypeConstants;
import org.fxkc.peis.constant.ExtendTypeEnum;
import org.fxkc.peis.constant.ReportTypeEnum;
import org.fxkc.peis.constant.TemplateConstants;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetItemVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetTypeVo;
import org.fxkc.peis.domain.vo.ftlModel.GuideSheetVo;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.service.*;
import org.fxkc.peis.utils.BarCodeUtils;
import org.fxkc.peis.utils.PdfMergeUtil;
import org.fxkc.peis.utils.WordToPdfUtils;
import org.fxkc.system.api.RemoteUserService;
import org.fxkc.system.api.domain.vo.RemoteUserVo;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TjReportPrintServiceImpl implements ITjReportPrintService {

    private final ITjTemplateService templateService;

    private final ITjRegisterService registerService;

    private final ITjRegCombinationProjectService regProjectService;

    private final ITjTeamInfoService teamInfoService;

    private final ITjPackageService packageService;

    private final DictService dictService;
    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public void printGuideSheet(ReportPrintBO bo, HttpServletResponse response) throws Exception {
        String nickname = LoginHelper.getLoginUser().getNickname();
        //查询体检记录
        List<TjRegisterVo> registerList = registerService.getByIds(bo.getRegIdList());
        if(registerList.isEmpty()){
            throw new PeisException("数据查询失败，无法打印！");
        }
        Set<Long> packageIdSet = new HashSet<>();
        Set<Long> teamIdSet = new HashSet<>();
        Set<Long> regIdSet = new HashSet<>();
        Set<Long> userIdSet = new HashSet<>();
        Map<Long, TjTemplateVo> templateMap = this.getTemplateMap(registerList, ReportTypeEnum.ZYD.getCode());
        registerList.forEach(s->{
            packageIdSet.add(s.getPackageId());
            if(null!=s.getTeamId()){teamIdSet.add(s.getTeamId());}
            if(null!=s.getId()){regIdSet.add(s.getId());}
            if(null!=s.getGeneralReviewDoctor()){userIdSet.add(s.getGeneralReviewDoctor());}
        });
        //查询全部的项目
        List<GuideSheetItemVo> itemAllList = this.regProjectService.queryGuideItemByIds(new ArrayList<>(regIdSet));
        Map<Long, List<GuideSheetItemVo>> itemMap = itemAllList.stream().collect(Collectors.groupingBy(GuideSheetItemVo::getRegId));
        //查询其它需要翻译的数据
        List<TjTeamInfoVo> teamInfoList = this.teamInfoService.queryListByIds(new ArrayList<>(teamIdSet));
        Map<Long, String> teamInfoMap = teamInfoList.stream().collect(Collectors.toMap(TjTeamInfoVo::getId, TjTeamInfoVo::getTeamName));
        List<TjPackageVo> packageList = this.packageService.queryListByIds(new ArrayList<>(packageIdSet));
        Map<Long, String> packageMap = packageList.stream().collect(Collectors.toMap(TjPackageVo::getId, TjPackageVo::getPackageName));
        //翻译用户昵称
        List<RemoteUserVo> userList = new ArrayList<>();
        if(!userIdSet.isEmpty()){
           userList = remoteUserService.selectByIdList(new ArrayList<>(userIdSet));
        }
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(RemoteUserVo::getUserId, RemoteUserVo::getNickName));
        //翻译字典值
        Map<String, String> sexMap = dictService.getAllDictByDictType(DictTypeConstants.SYS_USER_SEX);
        Map<String, String> guideItemTypeMap = dictService.getAllDictByDictType(DictTypeConstants.GUIDE_ITEM_TYPE);
        //设置请求头
        WordToPdfUtils.setResponse(response,System.currentTimeMillis()+".pdf");
        //只有一条数据时，直接返回
        if(registerList.size()==1){
            TjRegisterVo s = registerList.get(0);
            this.printGuideSheet(s,templateMap, itemMap.getOrDefault(s.getId(),new ArrayList<>()), teamInfoMap, packageMap, sexMap, guideItemTypeMap, userMap, nickname, response.getOutputStream());
            return;
        }
        //多条数据时，需要下载导本地并合并
        List<File> fileList =new ArrayList<>();
        String templatePath = WordToPdfUtils.getTemplatePath();
        try {
            for(TjRegisterVo s:registerList){
                File file = new File(templatePath, s.getId() + ".pdf");
                this.printGuideSheet(s,templateMap, itemMap.getOrDefault(s.getId(),new ArrayList<>()), teamInfoMap, packageMap, sexMap, guideItemTypeMap, userMap, nickname, new FileOutputStream(file));
                fileList.add(file);
            }
            //合并pdf
            PdfMergeUtil.mergePdfFiles(fileList,response.getOutputStream());
        }finally {
            //删除临时文件
            WordToPdfUtils.deleteFolder(new File(templatePath));
        }
    }

    /**
     * 打印指引单
     */
    private void printGuideSheet(TjRegisterVo s, Map<Long, TjTemplateVo> templateMap, List<GuideSheetItemVo> itemVoList, Map<Long, String> teamInfoMap, Map<Long, String> packageMap, Map<String, String> sexMap, Map<String, String> guideItemTypeMap, Map<Long, String> userMap, String userName, OutputStream outputStream){
        TjTemplateVo templateVo = templateMap.get(s.getId());
        if (templateVo.getTemplateCode().equals("ZYD001")) {
            this.printGuideSheet001(s, templateVo, itemVoList, teamInfoMap, packageMap, sexMap, guideItemTypeMap, userMap, userName, outputStream);
        } else {
            throw new PeisException("模板功能未实现，无法打印！");
        }
    }

    /**
     * 打印具体指引单[ZYD001]数据模型
     * @param s 体检登记信息
     * @param itemVoList 指引单项目信息
     * @param teamInfoMap 科室
     * @param packageMap 套餐
     * @param sexMap 性别
     * @param guideItemTypeMap 指引单项目分类
     * @param userMap 用户
     * @param userName 当前登录人姓名
     * @param outputStream 输出流，文件渲染
     */
    private void printGuideSheet001(TjRegisterVo s, TjTemplateVo templateVo,List<GuideSheetItemVo> itemVoList, Map<Long, String> teamInfoMap, Map<Long, String> packageMap, Map<String, String> sexMap, Map<String, String> guideItemTypeMap, Map<Long, String> userMap, String userName, OutputStream outputStream ){
        List<TjTemplateExtendVo> extendList = templateVo.getExtendList();
        Map<String, String> extendMap = extendList.stream().collect(Collectors.toMap(TjTemplateExtendVo::getExtendType, TjTemplateExtendVo::getContent));
        GuideSheetVo guideSheetVo = new GuideSheetVo();
        guideSheetVo.setAge(String.valueOf(s.getAge()));
        guideSheetVo.setGender(sexMap.get(s.getGender()));
        guideSheetVo.setRegisterTime(DateUtils.dateTime(s.getRegisterTime()));
        guideSheetVo.setHealthyCheckTime(DateUtils.dateTime(s.getHealthyCheckTime()));
        guideSheetVo.setHealthyCheckCode(s.getHealthyCheckCode());
        guideSheetVo.setName(s.getName());
        guideSheetVo.setPhone(s.getPhone());
        guideSheetVo.setContactAddress(s.getContactAddress());
        guideSheetVo.setCredentialNumber(s.getCredentialNumber());
        guideSheetVo.setPackageName(packageMap.get(s.getPackageId()));
        guideSheetVo.setTeamName(teamInfoMap.getOrDefault(s.getTeamId(),"个人体检"));
        guideSheetVo.setPeTimes(String.valueOf(s.getPeTimes()));
        guideSheetVo.setPrintTime(DateUtils.getDate());
        guideSheetVo.setTjAddress(extendMap.get(ExtendTypeEnum.TJ_ADDRESS.getCode()));
        guideSheetVo.setTjPhone(extendMap.get(ExtendTypeEnum.TJ_PHONE.getCode()));
        guideSheetVo.setWarmTips(extendMap.get(ExtendTypeEnum.WARM_TIPS.getCode()));
        if(StringUtils.isNotBlank(s.getRemark()) && s.getRemark().length() >= 23){
            s.setRemark(s.getRemark().substring(0,22)+"...");
        }
        guideSheetVo.setRemark(s.getRemark());
        guideSheetVo.setPrintUser(userName);
        guideSheetVo.setGeneralReviewDoctor(userMap.get(s.getGeneralReviewDoctor()));
        guideSheetVo.setIsReprint((null!=s.getGuidePrintTimes() && s.getGuidePrintTimes()>0)?"1":"0");
        //渲染用户图像
        if(StringUtils.isNotBlank(s.getUserImage())){
            guideSheetVo.setUserImage(WordToPdfUtils.getImageFromNetByUrl(s.getUserImage()));
        }
        //渲染条形码
        guideSheetVo.setHealthyBarCode(this.getBarCodeBase64(s.getHealthyCheckCode()));
        //guideSheetVo.setPayQrCode(`s.getUserImage());
        //获取检查项目
        Map<String, List<GuideSheetItemVo>> guideItemMap = itemVoList.stream().collect(Collectors.groupingBy(GuideSheetItemVo::getCheckType));
        List<GuideSheetTypeVo> checkItemList = new ArrayList<>();
        guideItemMap.forEach((k,v)->{
            GuideSheetTypeVo typeVo = new GuideSheetTypeVo();
            typeVo.setCheckTypeName(guideItemTypeMap.get(k));
            typeVo.setItemList(v);
            checkItemList.add(typeVo);
        });
        guideSheetVo.setCheckItemList(checkItemList);
        WordToPdfUtils.createPdf(guideSheetVo,templateVo.getTemplatePath(),templateVo.getTemplateName(),outputStream);
    }

    private Map<Long,TjTemplateVo> getTemplateMap(List<TjRegisterVo> registerList,String reportType){
        List<TjTemplateVo> list = templateService.getValidTemplate(reportType);
        if(list.isEmpty()){
            throw new PeisException("未配置相关模板，无法打印！");
        }
        Map<String, TjTemplateVo> templateMap = list.stream().collect(Collectors.toMap(TjTemplateVo::getPhysicalType,s->s));
        Map<Long,TjTemplateVo> map = new HashMap<>();
        for(TjRegisterVo s:registerList){
            TjTemplateVo tjTemplateVo;
            if(templateMap.containsKey(s.getPhysicalType())){
                tjTemplateVo = templateMap.get(s.getPhysicalType());
            }else {
                tjTemplateVo = templateMap.get(TemplateConstants.COMMON_PHYSICAL_TYPE);
            }
            if(null==tjTemplateVo){
                throw new PeisException(String.format("未寻找到 %s 关联的模板，请配置通用模板或者相关体检类型模板！",s.getHealthyCheckCode()));
            }
            map.put(s.getId(),tjTemplateVo);
        }
        return map;
    }

    /**
     * 生成条形码图片
     * @param number 内容
     */
    private  String getBarCodeBase64(String number) {
        try {
            //生成条形码图片，然后转换成base64
            BufferedImage img = BarCodeUtils.insertWords(BarCodeUtils.getBarCode(number), number);
            return WordToPdfUtils.getBufferedImageToBase64(img);
        }catch (Exception e){
            log.error("生成条形码失败：",e);
        }
        return null;
    }
}
