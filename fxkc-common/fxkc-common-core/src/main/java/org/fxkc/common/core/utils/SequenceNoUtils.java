package org.fxkc.common.core.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.*;
import java.util.Calendar;

/**
 * @Author: create by yuanxing
 * @Version: v1.0
 * @Description: 序号生成唯一id
 * @Date:2021/7/5 15:01
 */
@Slf4j
public class SequenceNoUtils {

    /** .log */
    /**
     * The FieldPosition.
     */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
    /**
     * This Format for format the data to special format.
     */
//    private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");
    private final static Format dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final static Format dateFormat2 = new SimpleDateFormat("yyMMdd");
    /**
     * This Format for format the number to special format.
     */
    private final static NumberFormat numberFormat = new DecimalFormat("0000");
    /**
     * 起始值
     */
    private static int seq = 1;
    private static final int MAX = 9999;
    /**
     * 起始日期
     */
    private static Calendar today = Calendar.getInstance();

    /**
     * 时间格式生成序列
     *
     * @return String
     */
    public static synchronized String generateSequenceNo() {
        Calendar rightNow = Calendar.getInstance();
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer format1 = dateFormat.format(today.getTime(), sb1, HELPER_POSITION);
        StringBuffer format2 = dateFormat.format(rightNow.getTime(), sb2, HELPER_POSITION);
        //判断不是当天序号回归1
        if (Integer.parseInt(format1.toString()) != Integer.parseInt(format2.toString())) {
            today = rightNow;
            seq = 1;
        }
        if (seq == MAX) {
            seq = 1;
        } else {
            seq++;
        }
        numberFormat.format(seq, sb2, HELPER_POSITION);
        //log.info("THE SQUENCE IS :" + sb.toString());
        return sb2.toString();
    }

    /**
     * 生成6位的体检编号
     * 100000
     */
    public static String generateTjNum(String sequenceNo) {
        Assert.notNull(sequenceNo, "入参序列号不能为空");
        int defaultLimit = 6;
        int length = sequenceNo.length();

        if (length > defaultLimit) {
            defaultLimit = length;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < defaultLimit - length; i++) {
            sb.append("0");
        }
        sb.append(sequenceNo);
        return sb.toString();
    }

    /**
     * 生成登记流水号
     * yyMMdd000001
     */
    public static String generateRegNum(String seqNo) {
        if (StringUtils.isEmpty(seqNo)) {
            return generateSequenceNo();
        }
        return dateFormat2.format(DateUtil.date()) + seqNo;
    }

    /**
     * 生成 4_4_9   体检基础项目编号，例如 fxkc_jcxm_000000002
     * 100000
     */
    public static String generateTjxmNum(String sequenceNo, String type) {
        Assert.notNull(sequenceNo, "入参序列号不能为空");
        int defaultLimit = 9;
        int length = sequenceNo.length();

        if (length > defaultLimit) {
            defaultLimit = length;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < defaultLimit - length; i++) {
            sb.append("0");
        }
        sb.append(sequenceNo);
        //return "fxkc_jcxm_" + sb.toString();
        return type+ "_" + sb.toString();
    }

    public static String generateNo(String sequenceNo, int defaultLimit) {
        int length = sequenceNo.length();
        if (length > defaultLimit) {
            defaultLimit = length;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < defaultLimit - length; i++) {
            sb.append("0");
        }
        sb.append(sequenceNo);
        return sb.toString();
    }

    public static String zeroPrefix(String source, int tagetLength){
        StringBuilder sb = new StringBuilder(source);
        while (sb.length() < tagetLength){
            sb.insert(0,"0");
        }
        return sb.toString();
    }

    /**
     * 生成报告编号流水号
     * 0001
     */
    public static String generateReportNum(Long seqNo) {
        return  String.format("%04d", seqNo);
    }


    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                sb.append(String.valueOf(c).repeat(size - len));
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            sb.append(String.valueOf(c).repeat(Math.max(0, size)));
        }
        return sb.toString();
    }

}

