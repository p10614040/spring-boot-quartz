package com.iceblock.springboot.quartz.factory;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;

/**
 * Job Detail Factory
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
public class JobDetailFactory {

    private JobDetailFactory() {
    }

    public static JobDetail createJobDetail(Class jobClass, String name) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(name);
        jobDetail.setDurability(true);
        jobDetail.setJobClass(jobClass);
        jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
        return jobDetail;
    }
}
