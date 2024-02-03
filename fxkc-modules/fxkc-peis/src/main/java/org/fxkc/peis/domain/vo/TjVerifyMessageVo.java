package org.fxkc.peis.domain.vo;

import lombok.Data;

@Data
public class TjVerifyMessageVo {

    /**
     * 记录信息
     */
    private String recordMessage;

    /**
     * 提示信息
     */
    private String promptMessage;

    /**
     * 是否提示
     */
    private Boolean isPrompt;

}
