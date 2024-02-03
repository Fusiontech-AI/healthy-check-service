package org.fxkc.peis.service;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.bo.template.TjTemplateQueryBO;
import org.fxkc.peis.domain.vo.template.TjTemplateVo;

import java.io.IOException;
import java.util.List;


/**
 * 体检报告维护Service接口
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
public interface ITjTemplateService {

    List<TjTemplateVo> getList(TjTemplateQueryBO bo);

    void downloadTemplate(String id, HttpServletResponse response) throws IOException;

    List<TjTemplateVo> getValidTemplate(String reportType);
}
