package org.fxkc.common.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：liangcc
 * @DateTime：2023/3/7 16:56
 * @Desc:
 */
@Getter
@AllArgsConstructor
public enum TjRecordLogEnum {

    OPER_TYPE_RYDJ("1", "人员登记"),
    OPER_TYPE_RYBD("2", "人员报道"),
    OPER_TYPE_RYXG("3", "人员修改"),
    OPER_TYPE_RYSC("4", "人员删除"),
    OPER_TYPE_XMBG("5", "项目变更"),
    OPER_TYPE_XMQJ("6", "项目弃检"),
    OPER_TYPE_XMHF("7", "项目弃检恢复"),
    OPER_TYPE_DYZJD("8", "打印指引单"),
    OPER_TYPE_TJSF("8", "团检收费"),
    OPER_TYPE_DYTXM("9", "打印条形码"),
    OPER_TYPE_DWGZ("10", "单位挂账"),
    OPER_TYPE_DYZJBG("11", "打印总检报告"),
    OPER_TYPE_DJQJ("12","登记弃检"),
    OPER_TYPE_DWFZBG("13","单位分组变更"),
    OPER_TYPE_XMZC("14","项目暂存"),
    OPER_TYPE_XMDJ("15","项目登记");

    private String code;

    private String desc;

    public static List<Map<String, String>> getDescList() {
        List<Map<String, String>> respList = new ArrayList<>();
        for(TjRecordLogEnum tjRecordLogEnum : TjRecordLogEnum.values()) {
                respList.add(new HashMap<String, String>(){{
                    put("code", tjRecordLogEnum.getCode());
                    put("desc", tjRecordLogEnum.getDesc());
                }});
        }
        return respList;
    }
}
