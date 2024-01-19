package org.fxkc.peis.domain.vo;

import lombok.Data;
import org.fxkc.peis.domain.TjTeamTask;

import java.util.List;

@Data
public class TjTeamTaskDetailVo extends TjTeamTask {


    /**
     * 单位分组信息
     */
    private List<TjTeamGroupVo> groupList;

}
