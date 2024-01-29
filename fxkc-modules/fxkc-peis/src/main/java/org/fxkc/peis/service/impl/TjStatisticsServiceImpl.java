package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.bo.TjStatisticsCommonBo;
import org.fxkc.peis.domain.vo.TjDailyPhysicalExportVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjStatisticsMapper;
import org.fxkc.peis.service.ITjStatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TjStatisticsServiceImpl implements ITjStatisticsService {

    private final TjStatisticsMapper tjStatisticsMapper;

    @Override
    public TjDailyPhysicalExportVo dailyPhysicalOverview(TjStatisticsCommonBo bo) {
        List<TjDailyPhysicalExportVo> voList = tjStatisticsMapper.dailyPhysicalOverview(bo);
        TjDailyPhysicalExportVo vo = new TjDailyPhysicalExportVo();
        TjDailyPhysicalExportVo person = new TjDailyPhysicalExportVo();
        TjDailyPhysicalExportVo team = new TjDailyPhysicalExportVo();
        if(CollUtil.isNotEmpty(voList)) {
            List<TjDailyPhysicalExportVo> personList = StreamUtils.filter(voList, e -> Objects.equals("1", e.getRegType()));
            List<TjDailyPhysicalExportVo> teamList = StreamUtils.filter(voList, e -> Objects.equals("2", e.getRegType()));
            vo.setTeamName("全部体检者");
            vo.setTotalNum((long) voList.size());
            vo.setTotalPersonalAmount(voList.stream().map(TjDailyPhysicalExportVo::getPersonAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
            vo.setTotalTeamAmount(voList.stream().map(TjDailyPhysicalExportVo::getTeamAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
            vo.setTotalAmount(vo.getTotalPersonalAmount().add(vo.getTotalTeamAmount()));
            vo.setAvgAmount(vo.getTotalNum() != 0L ? vo.getTotalAmount().divide(new BigDecimal(vo.getTotalNum()), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO);
            vo.setManCount(voList.stream().filter(e ->  Objects.equals("0", e.getGender())).count());
            vo.setWomanCount(voList.stream().filter(e -> Objects.equals("1", e.getGender())).count());
            vo.setNoRecycledGuideSheet(voList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getGuideSheetReceived())).count());
            vo.setRecycledGuideSheet(voList.stream().filter(e ->  Objects.equals(CommonConstants.NORMAL, e.getGuideSheetReceived())).count());
            vo.setNoChecked(voList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getInsMark())).count());
            vo.setHasChecked(voList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getInsMark())).count());
            vo.setNoPrintReport(voList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getReportPrintFlag())).count());
            vo.setHasPrintReport(voList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getReportPrintFlag())).count());

            if(CollUtil.isNotEmpty(personList)) {
                person.setRegType("1");
                person.setTeamName("全部个人");
                person.setTotalNum((long) personList.size());
                person.setTotalPersonalAmount(personList.stream().map(TjDailyPhysicalExportVo::getPersonAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                person.setTotalTeamAmount(personList.stream().map(TjDailyPhysicalExportVo::getTeamAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                person.setTotalAmount(person.getTotalPersonalAmount().add(person.getTotalTeamAmount()));
                person.setAvgAmount(person.getTotalNum() != 0L ? person.getTotalAmount().divide(new BigDecimal(person.getTotalNum()), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO);
                person.setManCount(personList.stream().filter(e ->  Objects.equals("0", e.getGender())).count());
                person.setWomanCount(personList.stream().filter(e -> Objects.equals("1", e.getGender())).count());
                person.setNoRecycledGuideSheet(personList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getGuideSheetReceived())).count());
                person.setRecycledGuideSheet(personList.stream().filter(e ->  Objects.equals(CommonConstants.NORMAL, e.getGuideSheetReceived())).count());
                person.setNoChecked(personList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getInsMark())).count());
                person.setHasChecked(personList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getInsMark())).count());
                person.setNoPrintReport(personList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getReportPrintFlag())).count());
                person.setHasPrintReport(personList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getReportPrintFlag())).count());
            }

            if(CollUtil.isNotEmpty(teamList)) {
                team.setRegType("2");
                team.setTeamName("全部单位");
                team.setTotalNum((long) teamList.size());
                team.setTotalPersonalAmount(teamList.stream().map(TjDailyPhysicalExportVo::getPersonAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                team.setTotalTeamAmount(teamList.stream().map(TjDailyPhysicalExportVo::getTeamAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                team.setTotalAmount(team.getTotalPersonalAmount().add(team.getTotalTeamAmount()));
                team.setAvgAmount(team.getTotalNum() != 0L ? team.getTotalAmount().divide(new BigDecimal(team.getTotalNum()), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO);
                team.setManCount(teamList.stream().filter(e ->  Objects.equals("0", e.getGender())).count());
                team.setWomanCount(teamList.stream().filter(e -> Objects.equals("1", e.getGender())).count());
                team.setNoRecycledGuideSheet(teamList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getGuideSheetReceived())).count());
                team.setRecycledGuideSheet(teamList.stream().filter(e ->  Objects.equals(CommonConstants.NORMAL, e.getGuideSheetReceived())).count());
                team.setNoChecked(teamList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getInsMark())).count());
                team.setHasChecked(teamList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getInsMark())).count());
                team.setNoPrintReport(teamList.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getReportPrintFlag())).count());
                team.setHasPrintReport(teamList.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getReportPrintFlag())).count());

                Map<String, List<TjDailyPhysicalExportVo>> teamGroupMap =StreamUtils.groupByKey(teamList, TjDailyPhysicalExportVo::getTeamName);
                TjDailyPhysicalExportVo teamChild;
                List<TjDailyPhysicalExportVo> childList = CollUtil.newArrayList();
                for (Map.Entry<String, List<TjDailyPhysicalExportVo>> entry : teamGroupMap.entrySet()) {
                    List<TjDailyPhysicalExportVo> v = entry.getValue();
                    teamChild = new TjDailyPhysicalExportVo();
                    teamChild.setTeamName(entry.getKey());
                    teamChild.setTotalNum((long) v.size());
                    teamChild.setTotalPersonalAmount(v.stream().map(TjDailyPhysicalExportVo::getPersonAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                    teamChild.setTotalTeamAmount(v.stream().map(TjDailyPhysicalExportVo::getTeamAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
                    teamChild.setTotalAmount(teamChild.getTotalPersonalAmount().add(teamChild.getTotalTeamAmount()));
                    teamChild.setAvgAmount(teamChild.getTotalNum() != 0L ? teamChild.getTotalAmount().divide(new BigDecimal(teamChild.getTotalNum()), 2, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO);
                    teamChild.setManCount(v.stream().filter(e ->  Objects.equals("0", e.getGender())).count());
                    teamChild.setWomanCount(v.stream().filter(e -> Objects.equals("1", e.getGender())).count());
                    teamChild.setNoRecycledGuideSheet(v.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getGuideSheetReceived())).count());
                    teamChild.setRecycledGuideSheet(v.stream().filter(e ->  Objects.equals(CommonConstants.NORMAL, e.getGuideSheetReceived())).count());
                    teamChild.setNoChecked(v.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getInsMark())).count());
                    teamChild.setHasChecked(v.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getInsMark())).count());
                    teamChild.setNoPrintReport(v.stream().filter(e -> Objects.equals(CommonConstants.DISABLE, e.getReportPrintFlag())).count());
                    teamChild.setHasPrintReport(v.stream().filter(e -> Objects.equals(CommonConstants.NORMAL, e.getReportPrintFlag())).count());
                    childList.add(teamChild);
                }
                team.setChildren(childList);
            }
            vo.setChildren(List.of(person,team));
        }
        return vo;
    }

    @Override
    public void dailyPhysicalOverviewExport(TjStatisticsCommonBo bo, HttpServletResponse response) {
        TjDailyPhysicalExportVo vo = dailyPhysicalOverview(bo);
        if(Objects.isNull(vo)) {
            throw new PeisException(ErrorCodeConstants.PEIS_EXPORT_DATA_EMPTY);
        }
        List<TjDailyPhysicalExportVo> voList = CollUtil.newArrayList();
        List<TjDailyPhysicalExportVo> childList = vo.getChildren();
        List<TjDailyPhysicalExportVo> personList = StreamUtils.filter(childList, e -> Objects.equals("1", e.getRegType()));
        List<TjDailyPhysicalExportVo> teamList = StreamUtils.filter(childList, e -> Objects.equals("2", e.getRegType()));
        voList.add(vo);
        if(CollUtil.isNotEmpty(personList)) {
            voList.add(personList.get(0));
        }
        if(CollUtil.isNotEmpty(teamList)) {
            voList.add(teamList.get(0));
            voList.addAll(teamList.get(0).getChildren());
        }
        ExcelUtil.exportExcel(voList, "每日体检者概览", TjDailyPhysicalExportVo.class, response);
    }


}
