package org.fxkc.peis.third.core;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @author zj
 * @description: 体检系统获取第三方结果抽象实现类
 * @date 2024-01-28 17:55
 */

@Service
@Primary
@Slf4j
public class AbstractThridServiceImpl implements IThridService{

    @Autowired
    private List<IThridService> thridServices;

    private final Map<String,IThridService> thridServiceMap = new ConcurrentHashMap<>(32);

    protected IThridService findServie(String itemId){
        IThridService currentThirdService = this.thridServiceMap.get(itemId);
        if (Objects.isNull(currentThirdService)) {
            for (IThridService processService : this.thridServices) {
                if (processService.supportItem(itemId)) {
                    currentThirdService = processService;
                    this.thridServiceMap.put(itemId, currentThirdService);
                    break;
                }
            }
        }
        return currentThirdService;
    }

    @Override
    public R postProcessBeforeGetResult(Object... objects){
        String itemId = (String) objects[0];
        IThridService thirdService = findServie(itemId);
        if (Objects.isNull(thirdService)) {
            throw new IllegalArgumentException("can not find service for itemId:[ "+ itemId +"],please check the configuration!");
        }
        return thirdService.postProcessBeforeGetResult(objects);
    }

    @Override
    public boolean supportItem(String itemId) {
        return false;
    }

    @Override
    public Object getItemResult(Object... objects) throws ExecutionException, InterruptedException {
        R r = postProcessBeforeGetResult(objects);
        if (!R.isSuccess(r)) {
            return r;
        }
        IThridService servie = findServie((String) objects[0]);
        return servie.getItemResult(r.getData());
    }

    /**
     * 获取第三方结果后置处理
     * @param objects 参数
     * @return 返回结果
     */
    @Override
    public R postProcessAfterGetResult(Object... objects) throws ExecutionException, InterruptedException {
        return findServie((String) objects[0]).postProcessAfterGetResult(objects);
    }
}
