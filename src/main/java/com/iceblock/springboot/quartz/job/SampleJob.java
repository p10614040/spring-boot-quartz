package com.iceblock.springboot.quartz.job;

import com.iceblock.springboot.quartz.service.SampleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Sample
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
public class SampleJob implements Job {

    @Autowired
    private SampleService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        service.hello();
    }
}