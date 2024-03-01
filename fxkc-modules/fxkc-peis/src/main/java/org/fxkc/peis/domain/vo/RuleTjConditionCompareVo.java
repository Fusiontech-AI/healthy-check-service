package org.fxkc.peis.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:体检项目规则条件表
 * @author chenjunbai
 * @since 2023-08-25
 */

/**
 * 体检项目规则条件表精度比较响应vo
 */
@Data
public class RuleTjConditionCompareVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 主键id
         */
        private String id;

        /**
         * 规则id
         */
        private String ruleId;

        /**
         * 比较值
         */
        private String referenceValue;

        /**
         * 比较原始值
         */
        private String oldValue;

        /**
         * 比较结果精度
         */
        private Double similar;

        }
