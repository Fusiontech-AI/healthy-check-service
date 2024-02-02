package org.fxkc.peis.third;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.domain.R;
import org.fxkc.peis.third.core.IThridService;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.fxkc.peis.third.his.HisProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author zj
 * @description: TODO
 * @date 2024-01-31 15:15
 */
@RestController
@RequestMapping("/third")
@Slf4j
public class ThirdController{

    @Autowired
    IThridService thridService;

    @Autowired
    HisProvider hisProvider;

    @GetMapping("/test")
    public R test() throws ExecutionException, InterruptedException {
        // lis测试
        // List<String> list = List.of("a", "b", "c");
        // Object itemResult = thridService.getItemResult("a", list);

        // pacs测试
        List<Integer> list = List.of(1, 2, 3);
        Object itemResult = thridService.getItemResult("1", list);
        return thridService.postProcessAfterGetResult("1",itemResult);
    }

    @GetMapping("/his")
    public R his(){
        String patientId = "1";
        log.info("his 建档结果:{}", JSON.toJSONString(hisProvider.examination(ServiceProviderEnum.FUSIONTECH_HIS_HTTP,patientId)));
        log.info("his 登记结果:{}", JSON.toJSONString(hisProvider.checkIn(ServiceProviderEnum.FUSIONTECH_HIS_HTTP,patientId)));
        log.info("his 费用推送结果:{}", JSON.toJSONString(hisProvider.pushFees(ServiceProviderEnum.FUSIONTECH_HIS_JCPT,patientId)));
        log.info("his 收费结果:{}", JSON.toJSONString(hisProvider.charge(ServiceProviderEnum.FUSIONTECH_HIS_JCPT,patientId)));
        return R.ok();
    }
}
