package org.fxkc.peis.reportPrint;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.constant.TemplateConstants;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.service.*;
import org.fxkc.peis.utils.BarCodeUtils;
import org.fxkc.peis.utils.WordToPdfUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 指引单打印
 */
@Slf4j
public abstract class AbstractReportPrint implements ReportPrintService {

    protected String reportType;

    @Autowired
    private ITjTemplateService templateService;

    @Autowired
    private ReportPrintHandler registerInsertHolder;
    @PostConstruct
    public void init() {
        registerInsertHolder.putHandler(reportType, this);
    }

    protected Map<Long,TjTemplateVo> getTemplateMap(List<TjRegisterVo> registerList){
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
    protected String getBarCodeBase64(String number) {
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
