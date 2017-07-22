package com.iceblock.springboot.quartz.runner;

import com.iceblock.springboot.quartz.factory.JobDetailFactory;
import com.iceblock.springboot.quartz.job.SampleJob;
import com.iceblock.springboot.quartz.model.SimpleTriggerModel;
import com.iceblock.springboot.quartz.service.SchedulerService;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动定时任务
 *
 * @author yan.liang@ustcinfo.com
 * @date 2017/7/17
 */
@Component
public class SampleJobRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleJobRunner.class);
    private static final String JOB_NAME = "SampleJob";

    @Autowired
    private SchedulerService schedulerService;

    @Value("${samplejob.enabled}")
    private Boolean enabled;

    @Value("${samplejob.replace}")
    private Boolean replace;

    @Value("${samplejob.repeatInterval}")
    private Long repeatInterval;


    @Override
    public void run(String... strings) throws Exception {
        Trigger.TriggerState triggerState = schedulerService.getTriggerState(JOB_NAME);
        LOGGER.info("{} job state is {}", JOB_NAME, triggerState);
        if (enabled) {
            JobDetail jobDetail = JobDetailFactory.createJobDetail(SampleJob.class, JOB_NAME);
            SimpleTriggerModel simpleTriggerModel = createTriggerModel();
            if (replace) {
                LOGGER.info("{} need restart!", JOB_NAME);
                schedulerService.removeScheduler(JOB_NAME);
            }
            schedulerService.createScheduler(jobDetail, simpleTriggerModel);

            LOGGER.info("{} start now!", JOB_NAME);
        } else {
            schedulerService.removeScheduler(JOB_NAME);

            LOGGER.info("{} stop now!", JOB_NAME);
        }
    }

    private SimpleTriggerModel createTriggerModel() {
        SimpleTriggerModel simpleTriggerModel = new SimpleTriggerModel();
        simpleTriggerModel.setRepeatInterval(repeatInterval);
        simpleTriggerModel.setTriggerName(JOB_NAME);
        return simpleTriggerModel;
    }
}
