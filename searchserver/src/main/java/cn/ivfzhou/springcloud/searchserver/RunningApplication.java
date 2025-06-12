package cn.ivfzhou.springcloud.searchserver;

import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunningApplication implements CommandLineRunner {

    @Autowired
    private ISearchService searchService;

    // SpringBoot项目运行时触发。
    @Override
    public void run(String... args) throws Exception {
        searchService.createIndex();
    }

}
