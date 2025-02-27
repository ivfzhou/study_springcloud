package cn.ivfzhou.reserve_platform.searchserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

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
