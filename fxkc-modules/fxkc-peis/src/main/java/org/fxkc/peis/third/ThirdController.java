package org.fxkc.peis.third;

import org.fxkc.common.core.domain.R;
import org.fxkc.peis.third.core.IThridService;
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
public class ThirdController{

    @Autowired
    IThridService thridService;

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
}
