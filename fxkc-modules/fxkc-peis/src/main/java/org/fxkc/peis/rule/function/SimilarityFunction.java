package org.fxkc.peis.rule.function;

import org.fxkc.peis.enums.SimilarityCodeEnum;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import info.debatty.java.stringsimilarity.JaroWinkler;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SimilarityFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env,
                              AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        String result = FunctionUtils.getStringValue(arg1, env);
        String right = FunctionUtils.getStringValue(arg2, env);
        String splitSymbol = FunctionUtils.getStringValue(arg3, env);
        if(StringUtils.isNotEmpty(splitSymbol)){
            String[] split = result.split("["+splitSymbol+"]");
            for (int i = 0; i < split.length; i++) {
                JaroWinkler jaroWinkler = new JaroWinkler();
                String s = split[i].contains(":") ? split[i].substring(split[i].indexOf(":") + 1) : split[i];
                double similar = jaroWinkler.similarity(s.replace((char)12288, ' ').trim(), right);
                if(similar> SimilarityCodeEnum.精度85.getCode()){
                    return AviatorBoolean.TRUE;
                } ;
            }
            return AviatorBoolean.FALSE;
        }
        JaroWinkler jaroWinkler = new JaroWinkler();
        String s = result.contains(":") ? result.substring(result.indexOf(":") + 1) : result;
        double similar = jaroWinkler.similarity(s.replace((char)12288, ' ').trim(), right);

        return similar>SimilarityCodeEnum.精度85.getCode() ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
    }
    public String getName() {
        return "similarity.contains";
    }
}
