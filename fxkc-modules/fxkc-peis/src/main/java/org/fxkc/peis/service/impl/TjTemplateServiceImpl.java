package org.fxkc.peis.service.impl;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.tenant.helper.TenantHelper;
import org.fxkc.peis.domain.bo.template.TjTemplateQueryBO;
import org.fxkc.peis.domain.vo.TjTemplateInfoVo;
import org.fxkc.peis.domain.vo.template.TjTemplateExtendVo;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;
import org.fxkc.peis.service.ITjTemplateExtendService;
import org.fxkc.peis.service.ITjTemplateInfoService;
import org.fxkc.peis.utils.WordToPdfUtils;
import org.springframework.stereotype.Service;
import org.fxkc.peis.mapper.TjTemplateMapper;
import org.fxkc.peis.service.ITjTemplateService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 体检报告维护Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@RequiredArgsConstructor
@Service
public class TjTemplateServiceImpl implements ITjTemplateService {

    private final TjTemplateMapper baseMapper;

    private final ITjTemplateInfoService templateInfoService;

    private final ITjTemplateExtendService templateExtendService;

    @Override
    public List<TjTemplateVo> getList(TjTemplateQueryBO bo) {
        bo.setTenantId(TenantHelper.getTenantId());
        return this.baseMapper.getList(bo);
    }

    @Override
    public void downloadTemplate(String id, HttpServletResponse response) throws IOException {
        TjTemplateInfoVo templateInfo = this.templateInfoService.queryById(id);
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotBlank(templateInfo.getTemplateData())){
            jsonObject = JSONObject.parseObject(templateInfo.getTemplateData());
        }
        WordToPdfUtils.createPdf(jsonObject,templateInfo.getTemplatePath(),templateInfo.getTemplateName(),response);
    }

    @Override
    public List<TjTemplateVo> getValidTemplate(String reportType) {
        List<TjTemplateVo>  list = this.baseMapper.getValidTemplate(reportType);
        if(!list.isEmpty()){
            List<Long> infoIdList = list.stream().map(TjTemplateVo::getId).toList();
            List<TjTemplateExtendVo> extendList = templateExtendService.getListByInfoId(infoIdList);
            Map<Long, List<TjTemplateExtendVo>> extendMap = extendList.stream().collect(Collectors.groupingBy(TjTemplateExtendVo::getTemplateId));
            list.forEach(s-> s.setExtendList(extendMap.getOrDefault(s.getId(),new ArrayList<>())));
        }
        return list;
    }
}
