package org.fxkc.peis.rule.aviator;

import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings({"unchecked"})
@Component
public class ExpressionExecuteService {

    @Resource
    private AviatorEvaluatorInstance evalEvaluatorInstance;

    public <T> T execute(String expression, Map<String, Object> paramMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("## execute expression start");
            log.info("## execute expression =>\n{}, ", expression);
            log.info("## execute param => {}", paramMap);
            Expression compiledExp = evalEvaluatorInstance.compile(expression, true);
            return (T) compiledExp.execute(paramMap);
        } catch (Exception e) {
            log.error("ExpressionExecuteService出错"+e.getMessage(),e);
            return null;
        } finally {
            stopWatch.stop();
            log.info("## execute expression end (elapsed {} milliseconds)", stopWatch.getTime(TimeUnit.MILLISECONDS));
        }
    }
}
