package org.fxkc.peis.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjTeamTask;

import java.util.List;

@Data
@AutoMapper(target = TjTeamTask.class)
public class TjTeamTaskDetailVo extends TjTeamTask {


    /**
     * 单位分组信息
     */
    private List<TjTeamGroupVo> groupList;

}
